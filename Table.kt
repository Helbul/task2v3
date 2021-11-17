package task2v3

import kotlin.math.max

/**
 * Table class, cells can contain TextElement
 * @param countColumns - number of table columns type Int
 * @param countLinesTable - number of table rows type Int
 * @param columnWidthInPercent -
 * @param tableText - the contents of the table of type String to be converted to an ArrayList<TextElement>
 */

class Table(private val countColumns: Int, private val countLinesTable: Int, vararg columnWidthInPercent: Int, val tableText: String): TextElement(){
    private var columnWidthInPercent: IntArray = columnWidthInPercent
    var tableContents: ArrayList<TextElement> = textParser(tableText)

    override var countWord: Int = 0

    //Print options
    var heightLines: ArrayList<Int> = ArrayList()
    var sumHeightLines: ArrayList<Int> = ArrayList()
    override var countLine: Int = 0
    override var widhtPrint: Int = 0
        set(value) {
            field = value
            countWord = 0
            var sum = 0
            for (i in 0 until countLinesTable) {
                for (j in 0 until countColumns) {
                    tableContents[i * countColumns + j].widhtPrint = widhtPrint * columnWidthInPercent[j] / 100 - 1
                    countWord += tableContents[i * countColumns + j].countWord
                }
                heightLines.add(getHeightLineTable(i))
                sum += heightLines[i]
                sumHeightLines.add(sum)
            }
            countLine = sum - 1
        }

    /**
     * Get the height of a table row by row number
     * @param Line number
     */
    fun getHeightLineTable (numberLine: Int): Int {
        var maxHeigth = 0
        for (i in (countColumns * numberLine) until (countColumns * numberLine) + countColumns) {
            maxHeigth = max(maxHeigth, tableContents[i].countLine)
        }
        return maxHeigth + 1
    }

    /**
     * Print the entire
     * @param Print widht
     */
    override fun print(widht: Int) {
        widhtPrint = widht

        print("".padEnd(widhtPrint, '_'))
        println()

        for (i in 0 until countLinesTable) {
            for (j in 0 until heightLines[i]) {
                val currentNumberLine = j + (sumHeightLines[i] - heightLines[i])
                if(currentNumberLine == countLine) break
                print("|")
                linePrint(currentNumberLine)
                println()
            }
        }
        println("".padEnd(widhtPrint, '_'))
        println("Count word: $countWord")
    }

    /**
     * Print a specified item line. The print width is calculated automatically.
     * @param Line number
     */
    override fun linePrint(numberLine: Int) {
        var numberLineTable = 0

        for (i in 0 until countLinesTable) {
            if (numberLine < sumHeightLines[i]) {
                numberLineTable = i
                break
            }
        }
        val currentNumberLineTextElement = numberLine - (sumHeightLines[numberLineTable] - heightLines[numberLineTable])
        for (j in 0 until countColumns) {
            val currentTextElement = numberLineTable * countColumns + j
            if (numberLine >= countLine){
                print("".padEnd(tableContents[currentTextElement].widhtPrint, ' '))
            }else if (numberLine == sumHeightLines[numberLineTable] - 1) {
                print("".padEnd(tableContents[currentTextElement].widhtPrint, '_'))
            }else tableContents[currentTextElement].linePrint(currentNumberLineTextElement)
            if (tableContents[currentTextElement] !is Table)print("|")
        }
    }

}
