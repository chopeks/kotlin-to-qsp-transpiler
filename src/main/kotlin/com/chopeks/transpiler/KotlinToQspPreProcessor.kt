package com.chopeks.transpiler

import antlr.KotlinLexer
import org.antlr.v4.runtime.*
import java.io.File

/**
 * Preprocessor, starts before transpiling process, also runs other preprocessors added to transpiler.
 */
object KotlinToQspPreProcessor {
  fun process(srcFile: File, preProcessors: Array<ExternalPreProcessor>): String {
    val lexer = KotlinLexer(CharStreams.fromStream(srcFile.inputStream()))
    val tokens = CommonTokenStream(lexer)
    var code = srcFile.readText()
    // aye, remove comments
    for (i in (tokens.numberOfOnChannelTokens - 1) downTo 0) {
      val token = tokens[i]
      if (token.type.let { it == KotlinLexer.LineComment || it == KotlinLexer.DelimitedComment }) {
        code = code.removeRange(token.startIndex..token.stopIndex)
      }
    }
    preProcessors.forEach { code = it.process(code) }
    return code
  }
}