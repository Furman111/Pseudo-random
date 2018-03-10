import java.io.*
import java.util.*

const val FILE_PATH = "/users/stepanfurman/documents/4k2s/Криптография/LAB2/sequence.txt"

fun readSequence(readFrom: String = FILE_PATH): List<Long> =
        (Scanner(BufferedReader(FileReader(readFrom)))).use {
            val res = mutableListOf<Long>()
            while (it.hasNextLong()) {
                res.add(it.nextLong())
            }
            res
        }

fun List<Long>.writeSequence(writeTo: String = FILE_PATH) = BufferedWriter(FileWriter(writeTo)).use {
    for(number in this){
        it.append("$number ")
    }
}

