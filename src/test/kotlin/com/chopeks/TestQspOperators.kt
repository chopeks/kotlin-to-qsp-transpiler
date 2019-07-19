package com.chopeks

import com.chopeks.transpiler.KotlinToQspTranspiler
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TestQspOperators : Spek({
  describe("Compiller") {
    val transpiler by memoized { KotlinToQspTranspiler() }

    describe("operators") {
      it("assignment") {
        val code = """
          m = 2
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          m = 2
        """.trimIndent()
      }
      it("equality comparision") {
        val code = """
          if(n == 2) n = 2
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if n = 2: n = 2
        """.trimIndent()
      }
      it("inequality comparision") {
        val code = """
          if(n != 2) n = 2
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if n ! 2: n = 2
        """.trimIndent()
      }
    }
  }
})