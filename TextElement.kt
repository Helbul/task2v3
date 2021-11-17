package task2v3

abstract class TextElement (){

    abstract var countWord: Int

    abstract var countLine: Int

    abstract var widhtPrint: Int

    abstract fun print(widhtPrint: Int)

    abstract fun linePrint(numberLine: Int)

}