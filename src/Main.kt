import generator.MajorityVoteGenerator
import generator.PseudoRandomGenerator
import test.nonoverlapping.testNonOverlapping
import test.spectral.testSpectral

fun main(args: Array<String>) {

    with(MajorityVoteGenerator(
            PseudoRandomGenerator(k = 6, p = 2, a = arrayOf(0, 1, 0, 1, 0, 1),
                    x0 = arrayOf(0, 1, 0, 1, 1, 0)),
            PseudoRandomGenerator(k = 6, p = 2, a = arrayOf(0, 0, 0, 1, 1, 1),
                    x0 = arrayOf(1, 0, 0, 1, 1, 0)),
            PseudoRandomGenerator(k = 6, p = 2, a = arrayOf(0, 0, 0, 0, 1, 1),
                    x0 = arrayOf(0, 1, 1, 1, 1, 0))
    )
    ) {
        val v = mutableListOf<Long>()
        for (i in 1..1048576) {
            v.add(next())
        }
        v.writeSequence()
        println("Period = ${v.countPeriod()}")
        testSpectral(v)
        testNonOverlapping(v, listOf(0, 0, 1))
    }

}