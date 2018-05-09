package test

import org.apache.commons.math3.special.Erf.erfc
import kotlin.math.abs
import kotlin.math.log2
import kotlin.math.sqrt

data class TheoreticalValues(
        val blockLength: Int,
        val expectedValue: Double,
        val variance: Double
)

val theoreticalData = listOf(
        TheoreticalValues(6, 5.2177052, 2.954),
        TheoreticalValues(7, 6.1962507, 3.125),
        TheoreticalValues(8, 7.1836656, 3.238),
        TheoreticalValues(9, 8.1764248, 3.311),
        TheoreticalValues(10, 9.1723243, 3.356),
        TheoreticalValues(11, 10.170032, 3.384),
        TheoreticalValues(12, 11.168765, 3.401),
        TheoreticalValues(13, 12.168070, 3.410),
        TheoreticalValues(14, 13.167693, 3.416),
        TheoreticalValues(15, 14.167488, 3.419),
        TheoreticalValues(16, 15.167379, 3.421)
)

/**
 * Универсальный статистический тест Мауэра
 *
 * @param blockLength - длина блока
 * @param initializationBlocksCount - количество блоков в инициализационной подпоследовательности
 */
fun testMaurerUniversal(blockLength: Int, initializationBlocksCount: Int, sequence: List<Long>) {

    //Разбиение на сегменты
    val initialSegment = sequence.subList(0, blockLength * initializationBlocksCount)
    val testSegment = sequence.subList(blockLength * initializationBlocksCount, sequence.size).toMutableList()

    //Отброс хвоста
    while (testSegment.size % blockLength != 0) {
        testSegment.removeAt(testSegment.lastIndex)
    }

    //Количество блоков в тестовом сегменте
    val testingBlocksCount = testSegment.size / blockLength

    //Инициализационный сегмент в виде блоков
    val initializationBlocks = List(initializationBlocksCount) { block ->
        List(blockLength) {
            initialSegment[block * blockLength + it]
        }
    }

    //Тестовый сегмент в виде блоков
    val testBlocks = List(testingBlocksCount) { block ->
        List(blockLength) {
            testSegment[block * blockLength + it]
        }
    }

    //Количество возможных битовых коомбинаций длины blockLength
    val blockCombinationsCount = Math.pow(2.toDouble(), blockLength.toDouble()).toInt()

    //Все возможные битовые коомбинации длины blockLength
    val blockCombinations = List(blockCombinationsCount) {
        var number = it
        List<Long>(blockLength) {
            val position = blockLength - it - 1
            if (position == 0) {
                if (number > 0) 1 else 0
            } else {
                val value = Math.pow(2.toDouble(), position.toDouble()).toInt()
                if ((number / value) > 0) {
                    number -= value
                    1
                } else 0
            }
        }
    }

    //Табличка позиций последних появлений блоков
    val table = MutableList(blockCombinations.size) {
        0
    }

    //Инициализация таблицы
    for (block in initializationBlocks.withIndex()) {
        table[blockCombinations.indexOf(block.value)] = (block.index + 1)
    }

    var sum = 0.0

    //Вычисление сумму разностей растояний между блоками
    for (block in testBlocks.withIndex()) {
        sum += log2((block.index + initializationBlocksCount + 1 - table[blockCombinations.indexOf(block.value)]).toDouble())
        table[blockCombinations.indexOf(block.value)] = block.index + initializationBlocksCount + 1
    }

    val fn = 1.toDouble() / testingBlocksCount.toDouble() * sum

    val c = 0.7 - 0.8 / blockLength + (4 + 32 / blockLength) * Math.pow(testingBlocksCount.toDouble(), (-3 / blockLength).toDouble()) / 15

    val sigma = theoreticalData.find { it.blockLength == blockLength }?.let {
        c * sqrt(it.expectedValue / testingBlocksCount)
    } ?: throw IllegalArgumentException("blockLength must be between 6 and 16, sorry!!!")

    //Вычисление pValue
    val pValue = theoreticalData.find { it.blockLength == blockLength }?.let {
        erfc(abs((fn - it.expectedValue) / (sqrt(2.toDouble()) * sigma)))
    } ?: throw IllegalArgumentException("blockLength must be between 6 and 16, sorry!!!")

    with(StringBuilder()) {
        appendln("Universal Maurer test: pValue = $pValue")
        append("Conclusion: ")
        appendln(if (pValue < 0.01) "non-random" else "random")
        println(toString())
    }

}

data class RecommendationData(
        val minimalSequenceSize: Int,
        val blockLength: Int,
        val initializationBlocksCount: Int
)

val recommendedParams = listOf(
        RecommendationData(387840, 6, 640),
        RecommendationData(904960, 7, 1280),
        RecommendationData(2068480, 8, 2560)
)

fun testMaurerUniversalWithRecommendedParams(sequence: List<Long>) {
    var params: RecommendationData = recommendedParams.first()
    recommendedParams.sortedBy { it.minimalSequenceSize }.forEach {
        if (sequence.size > it.minimalSequenceSize) params = it
    }
    testMaurerUniversal(params.blockLength, params.initializationBlocksCount, sequence)
}