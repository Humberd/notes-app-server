package pl.humberd.notesapp.application.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.reflect.KClass

@ResponseStatus(value = HttpStatus.CONFLICT)
class AlreadyExistsException : RuntimeException {

    constructor(message: String) : super(message) {
    }

    constructor(type: KClass<*>, key: String)
            : super("${type.simpleName} with [$key] already exists.")
}

