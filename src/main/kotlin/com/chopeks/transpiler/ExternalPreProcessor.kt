package com.chopeks.transpiler

/**
 * In case you want to add/remove/modify something on the fly, here's a thing.
 */
interface ExternalPreProcessor {
  /**
   * @param srcCode Valid Kotlin file
   * @return Valid kotlin file
   */
  fun process(srcCode: String): String
}