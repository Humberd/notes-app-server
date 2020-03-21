package pl.humberd.notesapp.application.common

import pl.humberd.notesapp.application.exceptions.NotFoundException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@ExperimentalContracts
inline fun <reified T> NOT_NULL(
    entity: T?,
    id: String
) {
    contract {
        returns() implies (entity !== null)
    }

    if (entity === null) {
        throw NotFoundException(T::class, id)
    }
}
