package task2v3

import java.io.File

fun main() {
    val fileName = File("test3.txt").absoluteFile
    fileParser(fileName, 150)
}