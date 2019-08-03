package pl.humberd.notesapp

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ResponseStatus

fun JSON_OK(): ResponseEntity<String> {
    return ResponseEntity.ok("\"OK\"");
}

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException : Error()
