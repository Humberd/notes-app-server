package pl.humberd.notesapp.application.common.asserts

import pl.humberd.notesapp.application.exceptions.AlreadyExistsException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@ExperimentalContracts
inline fun <reified T> ASSERT_NOT_EXIST_GENERIC(
    exists: Boolean,
    id: String
) {
    contract {
        returns() implies (!exists)
    }

    if (exists) {
        throw AlreadyExistsException(T::class, id)
    }
}

@ExperimentalContracts
fun ASSERT_NOT_EXIST(
    exists: Boolean,
    message: String
) {
    contract {
        returns() implies (!exists)
    }

    if (exists) {
        throw AlreadyExistsException(message)
    }
}
