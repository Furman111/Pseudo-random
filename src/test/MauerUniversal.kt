package test

/**
 * Универсальный статистический тест Мауэра
 *
 * @param L - длина блока
 * @param Q - количество блоков в инициализационной подпоследовательности
 */
fun testMaurerUniversal(L: Int, Q: Int, sequence: List<Long>) {

    //Разбиение на сегменты
    val initialSegment = sequence.subList(0, L * Q)
    val testSegment = sequence.subList(L * Q, sequence.size).toMutableList()

    //Отброс хвоста
    while (testSegment.size % L != 0) {
        testSegment.removeAt(testSegment.lastIndex)
    }

    //Количество блоков в тестовом сегменте
    val K = testSegment.size / L

    //Инициализационный сегмент в виде блоков
    val initializationBlocks = List(Q) { block ->
        List(L) {
            initialSegment[block * L + it]
        }
    }

    //Тестовый сегмент в виде блоков
    val testBlocks = List(K) { block ->
        List(L) {
            testSegment[block * L + it]
        }
    }

    //Количество возможных битовых коомбинаций длины L
    val blockCombinationsCount = Math.pow(2.toDouble(), L.toDouble()).toInt()

    //Все возможные битовые коомбинации длины L
    val T = List(blockCombinationsCount) {
        var number = it
        List<Long>(L) {
            val position = L - it - 1
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
    val table = MutableList(T.size) {
        0
    }

    //Инициализация таблицы
    for (block in initializationBlocks.withIndex()) {
        table[T.indexOf(block.value)] = (block.index + 1)
    }



}

private fun log(log: String) {
    System.out.println(log)
}