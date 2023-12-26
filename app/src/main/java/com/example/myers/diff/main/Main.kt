package com.example.myers.diff.main

import com.example.myers.diff.MyersDiff
import java.util.Arrays

/**
 * @author gk
 * @Description:
 * @date 2023/12/25
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val oldText = "A\nB\nC\nA\nB\nB\nA"
        val newText = "C\nB\nA\nB\nA\nC"
        val oldList = listOf(*oldText.split("\\n".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
        val newList = listOf(*newText.split("\\n".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
        val myersDiff = MyersDiff<String>()
        try {
            val pathNode = myersDiff.buildPath(oldList, newList)
            println(pathNode)
            myersDiff.buildDiff(pathNode, oldList, newList)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}