package generator

interface Generator {

    fun next(): Long

    fun reset()

}