import generator.LFSRGenerator
import generator.MajorityVoteGenerator
import test.nonoverlapping.testNonOverlapping
import test.spectral.testSpectral

fun main(args: Array<String>) {

    with(MajorityVoteGenerator(
            LFSRGenerator(k = 6, a = arrayOf(0, 0, 1, 0, 0, 1), s0 = arrayOf(0, 1, 0, 1, 1, 0)),
            LFSRGenerator(k = 6, a = arrayOf(0, 0, 0, 1, 1, 1), s0 = arrayOf(1, 0, 0, 1, 1, 0)),
            LFSRGenerator(k = 6, a = arrayOf(0, 0, 0, 0, 1, 1), s0 = arrayOf(0, 1, 1, 1, 1, 0)))
    ) {
        val v = mutableListOf<Long>()
        for (i in 1..33554432) {
            v.add(next())
        }
        v.writeSequence()
        println("Period = ${v.countPeriod()}\n")
        testSpectral(v)
        testNonOverlapping(v, listOf(0, 0, 1))
    }

}