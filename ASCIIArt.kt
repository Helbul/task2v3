package task2v3

import kotlin.math.min

/**
 * ASCIIArt extends the TextElement class
 * @param art type String
 * @param widhtArt - painting width ype Int
 */

class ASCIIArt(private val widhtArt: Int, private val art: String): TextElement() {
    override var countWord: Int = 0

    //Print options
    override var widhtPrint: Int = 0
    override var countLine: Int = art.length / widhtArt

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
        if (numberLine >= countLine) {
            print("".padEnd(widhtPrint, ' '))
        } else {
            print(art.substring(numberLine * widhtArt, numberLine * widhtArt + min(widhtArt, widhtPrint)).padEnd(widhtPrint, ' '))
        }
    }
}