package pl.humberd.notesapp

import org.springframework.http.ResponseEntity

fun JSON_OK(): ResponseEntity<String> {
    return ResponseEntity.ok("\"OK\"");
}
