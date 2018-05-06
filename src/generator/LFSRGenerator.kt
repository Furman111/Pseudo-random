package generator

/**
 * Класс генератора с линейным регистром сдвига с обратной связью.
 *
 * @param k степень порождающего полинома
 * @param a коэффиценты порождающего полинома для элементов со степенями {k-1, ... , 0}, длина k, элементы должны быть из {0,1}
 * @param s0 начальные значения Si, массив длиной k, элементы должны быть из {0,1} и не обращаться в 0 одновременно
 */
class LFSRGenerator(
        val k: Long = 6,
        val a: Array<Long>,
        val s0: Array<Long>
) : Generator {

    private var s = s0


    override fun next(): Long {
        val res = s[0]
        var newLast = 0L
        for (i in 0 until s.size) {
            newLast = newLast xor (s[i] and a[i])
        }
        var pred = newLast
        for (i in s.size-1 downTo 0){
            val temp = s[i]
            s[i] = pred
            pred = temp
        }
        return res
    }

    override fun reset() {
        s = s0
    }

}