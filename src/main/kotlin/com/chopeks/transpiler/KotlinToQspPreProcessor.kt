package com.chopeks.transpiler

import antlr.KotlinLexer
import org.antlr.v4.runtime.*

/**
 * Preprocessor, starts before transpiling process, also runs other preprocessors added to transpiler.
 */
object KotlinToQspPreProcessor {
  fun process(source: String, preProcessors: Array<ExternalPreProcessor>): String {
    val lexer = KotlinLexer(CharStreams.fromString(source))
    val tokens = CommonTokenStream(lexer)
    var code = source
    // aye, remove comments
    for (i in (tokens.numberOfOnChannelTokens - 1) downTo 0) {
      val token = tokens[i]
      if (token.type.let { it == KotlinLexer.LineComment || it == KotlinLexer.DelimitedComment }) {
        code = code.removeRange(token.startIndex..token.stopIndex)
      }
    }
    preProcessors.forEach { code = it.process(code) }
    // after that remove imports

    return removeImports(code)
  }

  private fun removeImports(code: String): String {
    val lexer = KotlinLexer(CharStreams.fromString(code))
    val tokens = CommonTokenStream(lexer)
    // in case of empty code, pass it
    if (tokens.numberOfOnChannelTokens == 0) {
      return code
    }
    var lastImport: Token = tokens[0]
    // find last import
    for (i in 0 until tokens.numberOfOnChannelTokens) {
      if (tokens[i].type == KotlinLexer.IMPORT) {
        lastImport = tokens[i]
      }
    }
    // skip if no imports
    if (lastImport.tokenIndex == 0) {
      return code
    }
    // find 1st new line after import
    for (i in lastImport.tokenIndex until tokens.numberOfOnChannelTokens) {
      if (tokens[i].type == KotlinLexer.NL) {
        lastImport = tokens[i]
        break
      }
    }
    return code.removeRange(0..lastImport.stopIndex)
  }
}