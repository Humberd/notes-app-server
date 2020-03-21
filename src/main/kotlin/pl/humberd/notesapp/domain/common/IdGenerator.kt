package pl.humberd.notesapp.domain.common

import kotlin.reflect.KClass
import kotlin.streams.asSequence

object IdGenerator {
    private const val source = "abcdefghijklmnopqrstuvwxyz0123456789"

    fun random(type: KClass<*>): String {
        return getRandomId(type.simpleName?.toLowerCase() ?: "", 15)
    }

    private fun getRandomId(
        prefix: String,
        length: Long
    ): String {
        return java.util.Random().ints(length, 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("", "$prefix-")
    }
}
