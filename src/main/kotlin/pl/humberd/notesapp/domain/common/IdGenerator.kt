package pl.humberd.notesapp.domain.common

import kotlin.reflect.KClass
import kotlin.streams.asSequence

object IdGenerator {
    private const val source = "abcdefghijklmnopqrstuvwxyz0123456789"
    private const val idLength = 32
    private val random = java.util.Random()

    fun random(type: KClass<*>): String {
        return getRandomId(type.simpleName?.toLowerCase() ?: "")
    }

    private fun getRandomId(
        prefix: String
    ): String {
        return random.ints(idLength.toLong(), 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("", "$prefix-")
            .padEnd(idLength)
    }
}
