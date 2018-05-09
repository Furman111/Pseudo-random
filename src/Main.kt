import generator.LFSRGenerator
import generator.MajorityVoteGenerator
import test.nonoverlapping.testNonOverlapping
import test.spectral.testSpectral

fun main(args: Array<String>) {

    with(MajorityVoteGenerator(
            LFSRGenerator(k = 6, a = arrayOf(0, 0, 1, 0, 0, 1), s0 = arrayOf(0, 1, 0, 1, 0, 0)),
            LFSRGenerator(k = 6, a = arrayOf(0, 0, 0, 1, 1, 1), s0 = arrayOf(1, 0, 1, 1, 1, 0)),
            LFSRGenerator(k = 6, a = arrayOf(0, 0, 0, 0, 1, 1), s0 = arrayOf(0, 1, 1, 0, 1, 0)))
    ) {
        val v = mutableListOf<Long>()
        for (i in 1..1000000) {
            v.add(next())
        }
        v.writeSequence()

        println("Period = ${v.countPeriod(6)}\n")
        println("Тест последовательности:")
        testSpectral(v)
        testNonOverlapping(v, listOf(0, 0, 1))

        println("Тест Пи:")
        val pi = readTestingSequence(FILE_PI)
        testSpectral(pi)
        testNonOverlapping(pi, listOf(0, 0, 1))

        println("Тест экспоненты:")
        val exp = readTestingSequence(FILE_EXPONENT)
        testSpectral(exp)
        testNonOverlapping(exp, listOf(0, 0, 1))

    }

}