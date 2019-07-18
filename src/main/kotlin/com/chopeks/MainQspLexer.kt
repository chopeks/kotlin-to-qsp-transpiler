package com.chopeks

import antlr.QspLexer
import org.antlr.v4.runtime.*
import java.io.File

fun main(args: Array<String>) {
  val lexer = QspLexer(CharStreams.fromFileName("glife/artemEv.qsrc"))
  val tokens = CommonTokenStream(lexer)

  val log = File("log.txt")
  log.writeText("")
  for (i in 0 until tokens.numberOfOnChannelTokens) {
    log.appendText(tokens[i].toString() + '\n')
  }
}