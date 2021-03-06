package test

import org.apache.commons.math3.special.Erf.erfc
import org.apache.commons.math3.transform.DftNormalization
import org.apache.commons.math3.transform.FastFourierTransformer
import org.apache.commons.math3.transform.TransformType
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Спектральный тест
 *
 * @param sequence тестируемая битовая последовательность
 */

fun testSpectral(sequence: List<Long>) {

    val seq = sequence.map { (2 * it - 1).toDouble() }.toMutableList()

    processSize(seq)

    val n = seq.size

    val transformed = FastFourierTransformer(DftNormalization.STANDARD)
            .transform(seq.toDoubleArray(), TransformType.FORWARD)

    val m = MutableList(n / 2) {
        sqrt(transformed[it].real * transformed[it].real + transformed[it].imaginary * transformed[it].imaginary)
    }

    val t = sqrt(2.995732274 * n)

    val n0 = 0.95 * n / 2

    val n1 = m.filter { it < t }.size

    val d = (n1 - n0) / sqrt(n * 0.95 * 0.05 / 4)

    val pValue = erfc(abs(d) / sqrt(2.toDouble()))

    with(StringBuilder()) {
        appendln("Spectral test: pValue = $pValue")
        append("Conclusion: ")
        appendln(if (pValue < 0.01) "non-random" else "random")
        println(toString())
    }

}

private fun processSize(sequence: MutableList<Double>) {
    val size = sequence.size
    var temp = 0
    var pow = 0.toDouble()
    while (temp < size) {
        temp = Math.pow(2.toDouble(), pow++).toInt()
    }
    temp /= 2
    while (temp != sequence.size) {
        sequence.removeAt(sequence.lastIndex)
    }
}