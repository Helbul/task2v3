package task2v3

import kotlin.math.ceil
import kotlin.math.min

/**
 * Paragraph extends the TextElement class
 * @param text type String
 */

class Paragraph (private val text: String): TextElement() {
    private var arrayWords = text.split(" ").toMutableList()
    override var countWord: Int = arrayWords.size

    //Print options
    override var widhtPrint: Int = 0
        set(value) {
            field = value
            countLine = ceil((text.length.toDouble() / value)).toInt()
        }
    override var countLine: Int = 0

    /**
     * Print the entire
     * @param Print widht
     */
    override fun print(widht: Int) {
        widhtPrint = widht
        for (i in 0 until countLine) {
            linePrint(i)
            println()
        }
    }

    /**
     * Print a specified item line. To print a table item. The print width is calculated automatically.
     * @param Line number
     */
    override fun linePrint(numberLine: Int) {
        if (numberLine >= countLine){
                print("".padEnd(widhtPrint, ' '))
        } else if (numberLine == (countLine - 1)) {
            print(text.substring(numberLine * widhtPrint, min((numberLine * widhtPrint + widhtPrint), text.length)).padEnd(widhtPrint, ' '))
        } else {
            print(text.substring(numberLine * widhtPrint, numberLine * widhtPrint + widhtPrint))
        }
    }

}