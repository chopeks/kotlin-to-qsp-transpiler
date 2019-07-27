package com.chopeks.transpiler

import antlr.*
import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.tree.ParseTreeWalker
import java.io.File

class KotlinToQspTranspiler(
  private val preProcessors: Array<ExternalPreProcessor> = arrayOf()
) {
  private val tokenList = mutableListOf<String>()

  fun transpile(srcFile: File, targetFile: File) {
    targetFile.writeText("# ${targetFile.nameWithoutExtension}\n\n")
    targetFile.appendText("!! File generated from '${srcFile.name}'\n\n")
    targetFile.appendText(transpile(srcFile.readText()))
    targetFile.appendText("\n--- ${targetFile.nameWithoutExtension} ---------------------------------\n")
  }

  fun transpile(source: String): String {
    val preprocessedCode = KotlinToQspPreProcessor.process(source, preProcessors)

    val lexer = KotlinLexer(CharStreams.fromString(preprocessedCode))
    val tokens = CommonTokenStream(lexer)
    val parser = KotlinParser(tokens)

    for (i in 0 until tokens.numberOfOnChannelTokens) {
      tokenList.add(tokens[i].text)
    }

    for (i in 0 until tokens.numberOfOnChannelTokens) {
      val token = tokens[i]
      when (token.type) {
        KotlinLexer.NL               -> tokenList[i] = "\n"
        KotlinLexer.SEMICOLON        -> tokenList[i] = "& "
        KotlinLexer.LineComment      -> tokenList[i] = ""
        KotlinLexer.DelimitedComment -> tokenList[i] = ""
        KotlinLexer.Inside_Comment   -> tokenList[i] = ""
        KotlinLexer.StrExpr_Comment  -> tokenList[i] = ""
        KotlinLexer.VAL              -> tokenList[i] = ""
        KotlinLexer.VAR              -> tokenList[i] = ""
      }
    }

    ParseTreeWalker().apply {
      walk(ComparisionOperatorTracker(tokenList), parser.script()); parser.reset()
      walk(IfTracker(tokenList), parser.script());parser.reset()
      walk(ElseTracker(tokenList), parser.script());parser.reset()
      walk(LambdasTracker(tokenList), parser.script());parser.reset()
      walk(CallTracker(tokenList), parser.script());parser.reset()
      walk(LineStringExprTracker(tokenList), parser.script());parser.reset()
      walk(StringWithDollarTracker(tokenList), parser.script());parser.reset()
    }

    for (i in 1 until tokenList.size) {
      if (tokenList[i - 1] == "end" && tokens[i].type == KotlinLexer.ELSE) {
        tokenList[i - 1] = ""
      }
      if (tokens[i - 1].type == KotlinLexer.ELSE && tokens[i].type == KotlinLexer.IF) {
        tokenList[i - 1] = "else"
      }
    }

    val code = tokenList.apply { remove(last()) }.joinToString("")

    tokenList.clear()

    return KotlinToQspPostProcessor.process(code)
  }


  class ComparisionOperatorTracker(val list: MutableList<String>) : KotlinParserBaseListener() {
    override fun enterEqualityOperation(ctx: KotlinParser.EqualityOperationContext) {
      list[ctx.start.tokenIndex] = " ${ctx.text} ".replace("==", "=").replace("!=", "!")
    }

    override fun enterComparisonOperator(ctx: KotlinParser.ComparisonOperatorContext) {
      list[ctx.start.tokenIndex] = " ${ctx.text} "
    }

    override fun enterDisjunction(ctx: KotlinParser.DisjunctionContext) {
      ctx.DISJ()?.forEach {
        list[it.symbol.tokenIndex] = " or "
      }
    }

    override fun enterConjunction(ctx: KotlinParser.ConjunctionContext) {
      ctx.CONJ()?.forEach {
        list[it.symbol.tokenIndex] = " and "
      }
    }

    override fun enterAssignmentOperator(ctx: KotlinParser.AssignmentOperatorContext) {
      list[ctx.start.tokenIndex] = " ${ctx.text} "
    }

    override fun enterMultiplicativeOperation(ctx: KotlinParser.MultiplicativeOperationContext) {
      list[ctx.start.tokenIndex] = " ${ctx.text} ".replace("%", "mod")
    }
  }

  class IfTracker(val list: MutableList<String>) : KotlinParserBaseListener() {
    var insideIf = false
    var removeBrackets = false
    var addEnd = false
    override fun enterIfExpression(ctx: KotlinParser.IfExpressionContext) {
      list[ctx.LPAREN().symbol.tokenIndex] = " "
      list[ctx.RPAREN().symbol.tokenIndex] = ": "
      insideIf = true
      if (ctx.controlStructureBody(0)?.expression() != null) {
        // it's ok, nothing more to do
      } else {
        if (ctx.text.contains("\n")) {
          removeBrackets = true
          addEnd = true
          // normal case with brackets
        } else {
          removeBrackets = true
          // thats inlined with brackets, but unlikely to be here
        }
      }
      ctx.ELSE()?.apply {
        list[symbol.tokenIndex] = "else "
        addEnd = false
      }
    }

    override fun enterControlStructureBody(ctx: KotlinParser.ControlStructureBodyContext) {
      if (insideIf) {
        if (removeBrackets) {
          list[ctx.start.tokenIndex] = ""
          if (addEnd) {
            list[ctx.stop.tokenIndex] = "end"
          } else {
            list[ctx.stop.tokenIndex] = ""
          }
        }
      }
      insideIf = false
      removeBrackets = false
      addEnd = false
    }
  }

  class ElseTracker(val list: MutableList<String>) : KotlinParserBaseListener() {
    var blockId = 0
    var removeBrackets = false
    var hasNL = false
    override fun enterIfExpression(ctx: KotlinParser.IfExpressionContext) {
      ctx.ELSE()?.apply {
        blockId = ctx.controlStructureBody(1)?.ruleIndex!!
        removeBrackets = ctx.controlStructureBody(1)?.text?.contains("{") == true
        hasNL = ctx.controlStructureBody(1)?.text?.contains("\n") == true
      }
    }

    override fun enterControlStructureBody(ctx: KotlinParser.ControlStructureBodyContext) {
      if (ctx.ruleIndex == blockId) {
        if (ctx.start.text == "{") {
          list[ctx.start.tokenIndex] = ""
          list[ctx.stop.tokenIndex] = if (hasNL) "end" else ""
        }
      }
    }
  }

  class LambdasTracker(val list: MutableList<String>) : KotlinParserBaseListener() {
    private var token: Token? = null
    private var token2: Token? = null
    override fun enterStatement(ctx: KotlinParser.StatementContext) {
      token = ctx.start
    }

    override fun enterInfixFunctionCall(ctx: KotlinParser.InfixFunctionCallContext) {
      token2 = ctx.start
    }

    override fun enterAnnotatedLambda(ctx: KotlinParser.AnnotatedLambdaContext) {
      token?.also {
        when (it.text) {
          "act" -> {
            val inline = ctx.functionLiteral().getTokens(KotlinLexer.NL).isEmpty()
            list[ctx.start.tokenIndex] = ": "
            list[ctx.stop.tokenIndex] = if (inline) "" else "end"
          }
          else  -> when (token2?.text) {
            "obj" -> list[token2!!.tokenIndex] = " "
          }
        }
      }
    }
  }

  class CallTracker(val list: MutableList<String>) : KotlinParserBaseListener() {
    private var token: Token? = null
    private var specialCase1 = false
    override fun enterSimpleIdentifier(ctx: KotlinParser.SimpleIdentifierContext) {
      token = ctx.start
    }

    override fun enterCallSuffix(ctx: KotlinParser.CallSuffixContext) {
      val rename: (KotlinParser.CallSuffixContext, Token?, String) -> Unit =
        { ctx, token, name -> list[ctx.start.tokenIndex] = ""; list[ctx.stop.tokenIndex] = " "; list[token!!.tokenIndex] = name }
      if (ctx.annotatedLambda().isEmpty() && token != null) {
        // TODO add rest of methods...
        when (token!!.text.toLowerCase()) {
          // these are literally not changed, just passed as is, but () will be removed
          "gs", "gt", "delact", "func",
          "wait", "msg", "jump", "dynamic" -> {
            list[ctx.start.tokenIndex] = " "; list[ctx.stop.tokenIndex] = " "
          }
          "killvar"                        -> {
            list[token!!.tokenIndex] = token!!.text.toLowerCase()
            specialCase1 = true
          }
          "label"                          -> rename(ctx, token, ":")
          "cla"                            -> rename(ctx, token, "cla")
          "cls"                            -> rename(ctx, token, "cls")
          "clr"                            -> rename(ctx, token, "*clr")
          "aclr"                           -> rename(ctx, token, "clr")
          "ap"                             -> rename(ctx, token, "p")
          "apl"                            -> rename(ctx, token, "pl")
          "anl"                 -> rename(ctx, token, "nl")
          "p"                   -> rename(ctx, token, "*p")
          "pl"                  -> rename(ctx, token, "*pl")
          "nl"                  -> rename(ctx, token, "*nl")
          "closeall"            -> rename(ctx, token, "CLOSE ALL")
          "play"                -> rename(ctx, token, "PLAY")
          "rand", "rgb", "min", "max",
          "iif", "ucase", "mid", "len",
          "arrsize", "strpos"   -> Unit // ignore! leave as is with ()
          else                  -> println("call: ${token!!.text}${ctx.text}")
        }
      }
    }

    override fun enterValueArgument(ctx: KotlinParser.ValueArgumentContext) {
      if (specialCase1) {
        val name = ctx.text
        if (name.startsWith("\"")) {
          // this is ok, do nothing
        } else if (name.startsWith("`")) {
          // this has value enclosed in ``
          if (name.trim('`').startsWith("$")) {
            // string variable, escape $
            list[ctx.start.tokenIndex] = "\"${name.trim('`').replaceFirst("$", "\\\$")}\""
          } else {
            list[ctx.start.tokenIndex] = "\"${name.trim('`')}\""
          }
        } else {
          // this is most likely int variable, so enclose it in ""
          list[ctx.start.tokenIndex] = "\"$name\""
        }
      }
      specialCase1 = false
    }
  }

  class LineStringExprTracker(val list: MutableList<String>) : KotlinParserBaseListener() {
    override fun enterLineStringExpression(ctx: KotlinParser.LineStringExpressionContext) {
      list[ctx.start.tokenIndex] = "<<"; list[ctx.stop.tokenIndex] = ">>"
    }
  }

  class StringWithDollarTracker(val list: MutableList<String>) : KotlinParserBaseListener() {
    override fun enterSimpleIdentifier(ctx: KotlinParser.SimpleIdentifierContext) {
      if (ctx.start.text.let { it.startsWith("`") && it.endsWith("`") }) {
        if (list[ctx.start.tokenIndex].let { it.startsWith("`") && it.endsWith("`") }) {
          list[ctx.start.tokenIndex] = ctx.text.substring(1 until ctx.text.length - 1)
        }
      }
    }
  }
}