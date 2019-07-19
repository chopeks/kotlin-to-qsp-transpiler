package com.chopeks

import com.chopeks.transpiler.KotlinToQspTranspiler
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TestQspFunctions : Spek({
  describe("Compiller") {
    val transpiler by memoized { KotlinToQspTranspiler() }

    describe("functions") {
      it("""functions with parameters""") {
        val code = """
          gs("hello")
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` "gs 'hello'"
      }
      it("""funtion without parameters""") {
        val code = """
          clr()
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` "*clr"
      }
      it("""chaining functions""") {
        val code = """
          clr(); cla()
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` "*clr & cla"
      }
    }

    describe("special cases") {
      it("killVar with string") {
        val code = """
          killVar("variable")  
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` "killvar('variable')"
      }
      it("killVar with variable") {
        val code = """
          killVar(variable)  
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` "killvar('variable')"
      }
      it("killVar with `variable`") {
        val code = "killVar(`variable`)"
        transpiler.transpile(code).trim() `should be equal to` "killvar('variable')"
      }
      it("killVar with `\$variable`") {
        val code = "killVar(`\$variable`)"
        transpiler.transpile(code).trim() `should be equal to` "killvar('\$variable')"
      }
      it("dynamic with string") {
        val code = """
          dynamic("variable")  
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` "dynamic('variable')"
      }
      it("dynamic with variable") {
        val code = """
          dynamic(variable)  
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` "dynamic('variable')"
      }
      it("dynamic with `variable`") {
        val code = "dynamic(`variable`)"
        transpiler.transpile(code).trim() `should be equal to` "dynamic('variable')"
      }
      it("dynamic with `\$variable`") {
        val code = "dynamic(`\$variable`)"
        transpiler.transpile(code).trim() `should be equal to` "dynamic('\$variable')"
      }
    }
  }
})