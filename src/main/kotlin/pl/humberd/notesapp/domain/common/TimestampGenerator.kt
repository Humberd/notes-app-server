package pl.humberd.notesapp.domain.common

import java.sql.Timestamp
import java.util.*

fun now(): Timestamp = Timestamp.from(Calendar.getInstance().toInstant())
