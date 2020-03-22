package pl.humberd.notesapp.application.common

import pl.humberd.notesapp.application.exceptions.NotFoundException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@ExperimentalContracts
inline fun <reified T> EXISTS(
    exists: Boolean,
    id: String
) {
    contract {
        returns() implies (exists)
    }

    if (!exists) {
        throw NotFoundException(T::class, id)
    }
}
