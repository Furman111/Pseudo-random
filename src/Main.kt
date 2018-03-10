import generator.MajorityVoteGenerator
import generator.PseudoRandomGenerator

fun main(args: Array<String>) {

    with(MajorityVoteGenerator(
            PseudoRandomGenerator(k = 6, p = 2, a = arrayOf(0, 0, 0, 1, 1, 1),
                    x0 = arrayOf(0, 1, 0, 1, 1, 0))
    )
    ) {
        val v = mutableListOf<Long>()
        for (i in 1..124) {
            v.add(next())
            print("${v.get(i - 1)} ")
            if (i % 15 == 0) println()
        }
        v.writeSequence()
        println()
        println("Period = ${v.countPeriod()}")
    }

}