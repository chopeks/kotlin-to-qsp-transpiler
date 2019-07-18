package com.chopeks.transpiler

import antlr.KotlinLexer
import org.antlr.v4.runtime.*
import java.io.File

object KotlinToQspPreProcessor {
  fun process(srcFile: File, preProcessors: Array<ExternalPreProcessor>): String {
    val lexer = KotlinLexer(CharStreams.fromStream(srcFile.inputStream()))
    val tokens = CommonTokenStream(lexer)
    var code = srcFile.readText()
    var hadFun = true
    for (i in (tokens.numberOfOnChannelTokens - 1) downTo 0) {
      val token = tokens[i]
      if (token.type.let { it == KotlinLexer.LineComment || it == KotlinLexer.DelimitedComment }) {
        code = code.removeRange(token.startIndex..token.stopIndex)
      }
      if (token.type == KotlinLexer.FUN) {
        hadFun = false
      }
      if (hadFun) {
        if (token.type == KotlinLexer.Identifier) {
          when (token.text) {
            "`==`" -> code = code.replaceRange(token.startIndex..token.stopIndex, "==")
            "`!=`" -> code = code.replaceRange(token.startIndex..token.stopIndex, "!=")
          }
        }
      }
    }
    preProcessors.forEach { code = it.process(code) }
    return code
  }
}