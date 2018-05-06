/**
 * @return период последовательности, -1 если периода нет (возможно подана слишком короткая последовательность)
 */

fun List<Long>.countPeriod(prestep: Int): Int {
    var period: Int = size / 2
    var res = -1
    while (period > 0) {
        if (isEqual(prestep, period, this))
            res = period
        period--
    }
    return res
}

private fun isEqual(
        prestep: Int,
        period: Int,
        sequence: List<Long>
): Boolean {
    var res = true
    for (i in prestep until period + prestep) {
        var step = period
        while (step + i < sequence.size) {
            if (sequence[i] != sequence[step + i])
                res = false
            step += period
        }
        if (!res) break
    }
    return res
}