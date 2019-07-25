package com.chopeks

import com.chopeks.transpiler.KotlinToQspTranspiler
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TestQspCodeTemplate : Spek({
  describe("Transpiler") {
    val transpiler by memoized { KotlinToQspTranspiler() }

    describe("code templates") {
      it("code template") {
        val code = """
          `${"\$object"}` = {
            n = 2
          }
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          ${"\$object"} = {
              n = 2
          }
        """.trimIndent()
      }

      it("weird import case") {
        val code = """
          @file:Suppress("UNUSED_EXPRESSION", "CascadeIf", "LiftReturnOrAssignment", "ConvertTwoComparisonsToRangeCheck", "RemoveCurlyBracesFromTemplate", "RemoveSingleExpressionStringTemplate", "PackageDirectoryMismatch")

          import com.chopeks.qsp.*
          import mod.*
          
          if (args[0] == "loc") {
            gs("stat")
          }

        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if args[0] = 'loc': 
              gs 'stat' 
          end
        """.trimIndent()
      }
    }
  }
})