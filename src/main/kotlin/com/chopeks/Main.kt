package com.chopeks


import com.chopeks.extensions.GLExtensions
import com.chopeks.extensions.GLExtensionsPreProcessor
import com.chopeks.glife.GlifeLocationPreprocessor
import com.chopeks.transpiler.KotlinToQspTranspiler
import com.chopeks.transpiler.QspToKotlinTranspiler
import mod.extensions.UserExtensions
import java.io.File

const val modName = "chastity"

fun main(args: Array<String>) {
  val targetDir = File("output/$modName/").apply { mkdirs() }
  val srcDir = File("src/main/kotlin/mod/locations")

  // NOTE: You can add more as long as they're in packages:
  // com.chopeks.extensions
  // mod.extensions
  // and they follow format of already defined ones
  val extensions = mapOf(
    "ext" to GLExtensions::class,
    "usr" to UserExtensions::class
  )

  val qspSrcDir = File("glife/")
  val qspTargetDir = File("output/qsp/").apply { mkdirs() }
  qspSrcDir.listFiles()?.let { list ->
    list.forEachIndexed { index, it ->
      if (index !in 80..90) {
        return@forEachIndexed
      }
      QspToKotlinTranspiler(it, File(qspTargetDir, "${it.nameWithoutExtension}.kt")).transpile()
      if (index % 10 == 0) {
        println("Done (${index + 1}/${list.size})")
      }

//    QspToKotlinTranspiler(it, File(qspTargetDir, "${it.nameWithoutExtension.split("_", limit = 3).last()}.kt")).transpile()
    }
    println("Done (${list.size}/${list.size})")
  }


  KotlinToQspTranspiler(
    arrayOf(
      GlifeLocationPreprocessor(),
      GLExtensionsPreProcessor()
    )
  ).apply {
    srcDir.listFiles()?.forEach {
      val name = "mod_${modName}" + (if (it.nameWithoutExtension == "root") "" else "_${it.nameWithoutExtension}")
      compile(it, File(targetDir, "${it.nameWithoutExtension}.qsrc"))
    }
  }


//      VariableScrapper(File("glife/"), File("output/")).scrap()
}