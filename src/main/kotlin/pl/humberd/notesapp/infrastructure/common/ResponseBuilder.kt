package pl.humberd.notesapp.infrastructure.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ResponseBuilder {
    fun <T> ok(body: T): ResponseEntity<T> {
        return ResponseEntity.ok(body)
    }

    fun <T> created(body: T): ResponseEntity<T> {
        return ResponseEntity(body, HttpStatus.CREATED)
    }

    fun <T> updated(body: T): ResponseEntity<T> {
        return ok(body)
    }

    fun noContent(): ResponseEntity<Unit> {
        return ResponseEntity.noContent().build()
    }
}
