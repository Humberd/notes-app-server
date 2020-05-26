package pl.humberd.notesapp.application.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.reflect.KClass

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class NotFoundException : RuntimeException {
    constructor(message: String) : super(message)

    constructor(
        type: KClass<*>,
        id: String
    ) : super("${type.simpleName}($id) does not exist.")
}
