package com.chopeks

import com.chopeks.transpiler.KotlinToQspTranspiler
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TestQspStrings : Spek({
  describe("Transpiler") {
    val transpiler by memoized { KotlinToQspTranspiler() }
    val `$npc` = mapOf<String, String>()

    it("string template escaping") {
      val code = "\"is \${`\$npc`[\"A29\"]} photo\""
      transpiler.transpile(code).trim() `should be equal to`
        "'is <<\$npc[''A29'']>> photo'"
    }
  }
})