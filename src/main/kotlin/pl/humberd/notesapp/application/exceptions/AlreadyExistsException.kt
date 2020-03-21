package pl.humberd.notesapp.application.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.reflect.KClass

@ResponseStatus(value = HttpStatus.CONFLICT)
class AlreadyExistsException(
    type: KClass<*>,
    key: String
) : RuntimeException("${type} with $key already exists.")
