package task2v3

import java.util.regex.Matcher
import java.util.regex.Pattern

val regexParagraphStart = Pattern.compile("paragraph\\[")
val regexAsciiArtStart = Pattern.compile("asciiart\\(\\d+\\)\\[")
val regexTableStart = Pattern.compile("table(\\(\\d+\\))+\\[")
val regexEndBlock = Pattern.compile("\\]")


/**
 * Parser text to type Paragraph
 * @throws IllegalStateException if the string doesn't match the pattern
 */
fun parserParagraph (str: String): Paragraph {
    val matcherParagraphEnd: Matcher = regexParagraphStart.matcher(str)
    matcherParagraphEnd.find()
    val content = str.substring(matcherParagraphEnd.end(), str.length - 1)
    return Paragraph(content)
}

/**
 * Parser text to type ASCIIArt
 * @throws IllegalStateException if the string doesn't match the pattern
 */
fun parserAsciiArt (str: String): ASCIIArt {
    val matcherAsciiArtStart: Matcher = regexAsciiArtStart.matcher(str)
    matcherAsciiArtStart.find()
    val content = str.substring(matcherAsciiArtStart.end(), str.length - 1)
    val regexNumber = Pattern.compile("\\d+")
    val matcherNumber: Matcher = regexNumber.matcher(str.substring(0, matcherAsciiArtStart.end()))
    matcherNumber.find()
    val widhtArt = matcherNumber.group().toInt()
    return ASCIIArt(widhtArt, content)
}

/**
 * Parser text to type Table
 * @throws IllegalStateException if the string doesn't match the pattern
 */
fun parserTable (str: String): Table {
    val matcherTableStart: Matcher = regexTableStart.matcher(str)
    matcherTableStart.find()
    val content = str.substring(matcherTableStart.end(), str.length - 1)
    val regexNumber = Pattern.compile("\\d+")
    val matcherNumber: Matcher = regexNumber.matcher(str.substring(0, matcherTableStart.end()))
    val arrayList: ArrayList<Int> = ArrayList()
    while (matcherNumber.find()){
        arrayList.add(matcherNumber.group().toInt())
    }
    val countColumns: Int = arrayList[0]
    val countLines: Int = arrayList[1]
    val columnWidthInPercent: ArrayList<Int> = ArrayList()
    for (i in 2 until arrayList.size) {
        columnWidthInPercent.add(arrayList[i])
    }
    return Table(countColumns, countLines, *columnWidthInPercent.toIntArray(), tableText = content)

}

/**
 * Parser text to ArrayList<TextElement>
 */
fun textParser(text: String): ArrayList<TextElement> {


    // indexesStartEndBlocks - start and end indices of TextElement blocks in the given text
    val indexesStartEndBlocks: ArrayList<Int> = arrayListOf()

    val matcherEndBlock: Matcher = regexEndBlock.matcher(text)
    while (matcherEndBlock.find()) {
        indexesStartEndBlocks.add(matcherEndBlock.start())
    }
    val matcherParagraphStart: Matcher = regexParagraphStart.matcher(text)
    while (matcherParagraphStart.find()){
        indexesStartEndBlocks.add(matcherParagraphStart.start())
    }
    val matcherAsciiArtStart: Matcher = regexAsciiArtStart.matcher(text)
    while (matcherAsciiArtStart.find()){
        indexesStartEndBlocks.add(matcherAsciiArtStart.start())
    }
    val matcherTableStart: Matcher = regexTableStart.matcher(text)
    while (matcherTableStart.find()){
        indexesStartEndBlocks.add(matcherTableStart.start())
    }

    indexesStartEndBlocks.sort()

    // Dividing text into blocks of TextElement type String
    val arrayElement: ArrayList<String> = arrayListOf()
    var countSymbolOpen: Int = 0
    var countSymbolClose: Int = 0
    var indexSymblolOpen: Int = 0
    for (i in 0 until indexesStartEndBlocks.size) {
        if (text[indexesStartEndBlocks[i]] != ']') {
            if (countSymbolOpen == 0) {
                indexSymblolOpen = indexesStartEndBlocks[i]
            }
            countSymbolOpen++
        } else {
            countSymbolClose++
        }
        if (countSymbolOpen == countSymbolClose) {
            arrayElement.add(text.substring(indexSymblolOpen, indexesStartEndBlocks[i]+1))
            countSymbolOpen = 0
            countSymbolClose = 0
        }
    }


    //Convert a list of strings to a list TextElement
    val textElements: ArrayList<TextElement> = arrayListOf()

    for (element in arrayElement){
        if (element.substring(0, 9) == "paragraph") {
            textElements.add(parserParagraph(element))
        } else if (element.substring(0, 8) == "asciiart") {
            textElements.add(parserAsciiArt(element))
        } else if (element.substring(0, 5) == "table") {
            textElements.add(parserTable(element))
        }
    }

    return textElements

}