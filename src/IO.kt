import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter
import java.util.*
import java.lang.Long as JavaLong

const val FILE_PATH = "/users/stepanfurman/documents/4k2s/Криптография/LAB2/sequence.txt"
const val FILE_EXPONENT = "/users/stepanfurman/documents/4k2s/Криптография/LAB2/data.e"
const val FILE_PI = "/users/stepanfurman/documents/4k2s/Криптография/LAB2/data.pi"

fun readSequence(readFrom: String = FILE_PATH): List<Long> =
        (Scanner(BufferedReader(FileReader(readFrom)))).use {
            val res = mutableListOf<Long>()
            while (it.hasNextLong()) {
                res.add(it.nextLong())
            }
            res
        }

fun List<Long>.writeSequence(writeTo: String = FILE_PATH) = BufferedWriter(FileWriter(writeTo)).use {
    for (number in this) {
        it.append("$number ")
    }
}

fun readTestingSequence(readFrom: String): List<Long> =
        (Scanner(BufferedReader(FileReader(readFrom)))).use {
            val res = mutableListOf<Long>()
            while (it.hasNextLine()) {
                val line = it.nextLine()
                for (char in line) {
                    try {
                        res.add(JavaLong.valueOf(char.toString()))
                    } catch (e: Exception) {
                    }
                }
            }
            res
        }
