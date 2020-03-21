package pl.humberd.notesapp.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.reflect.KClass

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class NotFoundError(
    type: KClass<*>,
    id: String
) : Error("${type.simpleName}($id) does not exist.")
