package pl.humberd.notesapp.application.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.reflect.KClass

@ResponseStatus(value = HttpStatus.FORBIDDEN)
class ForbiddenException(
    type: KClass<*>,
    id: String
) : RuntimeException("${type.simpleName}($id) access forbidden")
