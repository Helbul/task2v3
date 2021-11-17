package task2v3

import java.io.File

/**
 * Print from file TextElement
 * @param File name type File
 * @param widhtPrint - Console print width
 */
fun fileParser(fileName: File, widhtPrint: Int) {
    if (!fileName.exists()) return
    val fileText = fileName.readText()

    val listTextElement: ArrayList<TextElement> = textParser(fileText)
    for (textElement in listTextElement) {
        textElement.print(widhtPrint)
    }
}