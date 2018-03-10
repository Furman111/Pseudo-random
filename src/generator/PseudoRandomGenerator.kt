package generator

/**
 * Класс генератора псевдослучаной последовательности.
 *
 * @param k степень порождающего полинома
 * @param p модуль (мощность алфавита)
 * @param a коэффиценты порождающего полинома для элементов со степенями {k-1, ... , 0}, длина k, элементы должны быть из {0,1, ... , p-1}
 * @param x0 начальные значения Xi, массив длиной k, элементы должны быть из {0,1, ... , p-1} и не обращаться в 0 одновременно
 */
class PseudoRandomGenerator(
        val k: Long = 6,
        val p: Long = 2,
        val a: Array<Long>,
        val x0: Array<Long>
) : Generator {

    private var x = x0

    override fun reset() {
        x = x0
    }

    override fun next(): Long {
        var res: Long = 0
        for (i in 0 until a.size) {
            res += a[i] * x[i]
        }
        res %= p
        for (i in a.size - 1 downTo 1) {
            x[i] = x[i - 1]
        }
        x[0] = res
        return res
    }

}