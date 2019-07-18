package com.chopeks.transpiler

object KotlinToQspPostProcessor {
  private const val APOSTROPHE = "%^^%APOSTROPHE%^^%"
  private const val QUOTATION = "%^^%QUOTATION%^^%"

  fun process(srcCode: String): String {
    return reformatCode(extractStrings(srcCode))
  }

  private fun reformatCode(content: String): String {
    var current = 0
    return content.replace("""(?m)^\s+""".toRegex(), "").lines()
      .joinToString("\n") {
        if (it.trim().startsWith("if") && it.trim().endsWith(":")) current++
        if (it.trim().startsWith("elseif") && it.trim().endsWith(":")) current++
        if (it.trim().startsWith("act") && it.trim().endsWith(":")) current++

        if (it.trim().endsWith("else")) current++
        if (it.trim().startsWith("end")) current--
        if (it.trim().startsWith("else")) current--

        if (it.trim().endsWith("{")) current++
        if (it.trim().startsWith("}")) current--


        current = maxOf(current, 0)

        val intent = if (it.trim().endsWith("{") || it.startsWith("if") || it.startsWith("else") || (it.startsWith("act") && it.trim().endsWith(":"))) {
          (0 until current).joinToString("    ") { "" }
        } else {
          (0..current).joinToString("    ") { "" }
        }
        intent + it
      }
  }

  private fun extractStrings(code: String): String {
    val lines = code.lines().run {
      val lines = mutableListOf<String>()
      forEach {
        lines += it
          .replace("\\\"", QUOTATION)
          .replace("'", APOSTROPHE)
          .replace("\"", "'")
          .replace("\\\$", "\$")
          .replace(APOSTROPHE, "''")
          .replace(QUOTATION, "\"")
      }
      lines
    }
    return lines.joinToString("\n")
  }
}