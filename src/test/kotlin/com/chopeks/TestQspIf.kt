package com.chopeks

import com.chopeks.transpiler.KotlinToQspTranspiler
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TestQspIf : Spek({
  describe("Compiller") {
    val transpiler by memoized { KotlinToQspTranspiler() }

    describe("ifs") {
      it("""simple one line if""") {
        val code = """
          if(n == 1) clr()
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if n = 1: *clr
        """.trimIndent()
      }
      it("""simple one line if with chaining""") {
        val code = """
          if(n == 1) clr(); cla()
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if n = 1: *clr & cla
        """.trimIndent()
      }
      it("""if else inline""") {
        val code = """
          if(n == 1) clr() else cla()
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if n = 1: *clr else cla
        """.trimIndent()
      }
      it("""if else multiline""") {
        val code = """
          if(n == 1) clr() 
          else cla()
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if n = 1: *clr 
          else cla
        """.trimIndent()
      }
      it("""if else inline but with brackets""") {
        val code = """
          if(n == 1) { clr() } else { cla() }
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if n = 1: *clr else cla
        """.trimIndent()
      }
      it("""if else multiline with brackets""") {
        val code = """
          if(n == 1) { 
              clr() 
          } else { 
              cla()
          }
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if n = 1: 
              *clr 
          else 
              cla 
          end
        """.trimIndent().trim()
      }
      it("""if elseif multiline with brackets""") {
        val code = """
          if(n == 1) { 
              clr() 
          } else if(n != 2) { 
              cla()
          }
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if n = 1: 
              *clr 
          elseif n ! 2: 
              cla 
          end
        """.trimIndent().trim()
      }
      it("""if elseif else multiline with brackets""") {
        val code = """
          if(n == 1) { 
              clr() 
          } else if(n != 2) { 
              cla()
          } else { 
              cla()
          }
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if n = 1: 
              *clr 
          elseif n ! 2: 
              cla 
          else 
              cla 
          end
        """.trimIndent().trim()
      }
      it("""if elseif else multiline with brackets, more else ifs""") {
        val code = """
          if(n == 1) { 
              clr() 
          } else if(n != 2) { 
              cla()
          } else if(n != 3) { 
              cla()
          } else if(n != 4) { 
              cla()
          } else { 
              cla()
          }
        """.trimIndent()
        transpiler.transpile(code).trim() `should be equal to` """
          if n = 1: 
              *clr 
          elseif n ! 2: 
              cla 
          elseif n ! 3: 
              cla 
          elseif n ! 4: 
              cla 
          else 
              cla 
          end
        """.trimIndent().trim()
      }
    }
  }

})