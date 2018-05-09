package test

import org.apache.commons.math3.special.Gamma.regularizedGammaQ
import kotlin.math.pow

/**
 * @param template шаблона
 */

fun testNonOverlapping(sequence: List<Long>, template: List<Int>) {

    val N = 8

    val M = sequence.size / N

    val W = MutableList(N) {
        0
    }

    for (j in 0 until N) {

        var times = 0

        var k = j * M

        while (k < M * (j + 1) - (template.size - 1)) {
            if (isHit(sequence.subList(k, k + template.size), template)) {
                ++times
                k += template.size
            } else {
                ++k
            }
        }

        W[j] = times

    }

    val theoreticalMean = (M - template.size + 1).toDouble() / (Math.pow(2.toDouble(), template.size.toDouble()))

    val theoreticalVarience = M * (1 / (Math.pow(2.toDouble(), template.size.toDouble())) - ((2 * template.size - 1) / (Math.pow(2.toDouble(), 2 * template.size.toDouble()))))

    var sigma = 0.toDouble()

    for (time in W) {
        sigma += ((time - theoreticalMean).pow(2)) / (theoreticalVarience)
    }

    val pValue = regularizedGammaQ(N.toDouble() / 2, sigma / 2)

    with(StringBuilder()) {
        appendln("NonOverlapping test: pValue = $pValue")
        append("Conclusion: ")
        appendln(if (pValue < 0.01) "non-random" else "random")
        println(toString())
    }

}

fun isHit(substring: List<Long>, template: List<Int>): Boolean {
    var isHit = true
    for (i in 0 until substring.size) {
        if (substring[i] != template[i].toLong()) {
            isHit = false
        }
    }
    return isHit
}