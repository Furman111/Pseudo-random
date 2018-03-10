const val LONG_BITS_NUMBER = 63

class MajorityVoteGenerator(private vararg val generators: Generator) : Generator {

    override fun next(): Long {
        val values = generators.map { it.next() }
        var res: Long = 0
        for (i in 0 until LONG_BITS_NUMBER) {
            val pow = Math.pow(2.toDouble(), i.toDouble()).toLong()
            with(values) {
                if (filter { it.and(pow) > 0 }.size > size / 2) {
                    res += pow
                }
            }
        }
        return res
    }

    override fun reset() = generators.forEach { it.reset() }

}