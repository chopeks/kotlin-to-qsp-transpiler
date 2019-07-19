package com.chopeks

import com.chopeks.transpiler.KotlinToQspTranspiler
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TestQspStrings : Spek({
  describe("Compiller") {
    val transpiler by memoized { KotlinToQspTranspiler() }

  }
})