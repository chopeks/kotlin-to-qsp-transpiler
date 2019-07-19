package com.chopeks

import com.chopeks.transpiler.KotlinToQspTranspiler
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TestQspVariables : Spek({
  describe("Compiller") {
    val transpiler by memoized { KotlinToQspTranspiler() }

    it("""can define local variable""") {
      val code = """
          if(v == 1) {
            val n = 1
          }
        """.trimIndent()
      transpiler.transpile(code).trim() `should be equal to` """
          if v = 1: 
              n=1
          end
        """.trimIndent()
    }
  }
})