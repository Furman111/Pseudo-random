package test.spectral

import org.apache.commons.math3.special.Erf.erfc
import org.apache.commons.math3.transform.DftNormalization
import org.apache.commons.math3.transform.FastFourierTransformer
import org.apache.commons.math3.transform.TransformType
import kotlin.math.abs
import kotlin.math.log2
import kotlin.math.sqrt

/**
 * Спектральный тест
 *
 * @param sequence тестируемая битовая последовательность
 */

fun testSpectral(sequence: List<Long>) {

    val n = sequence.size

    val seq = sequence.map {
        when (it) {
            0.toLong() -> -1.toDouble()
            1.toLong() -> 1.toDouble()
            else -> throw IllegalArgumentException("Not bit's sequence")
        }
    }

    val transformated = FastFourierTransformer(DftNormalization.STANDARD)
            .transform(seq.toDoubleArray(), TransformType.FORWARD)

    val m = MutableList(n / 2) {
        sqrt(transformated[it].real * transformated[it].real + transformated[it].imaginary * transformated[it].imaginary)
    }

    val t = sqrt(log2(1 / 0.05) * n)

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