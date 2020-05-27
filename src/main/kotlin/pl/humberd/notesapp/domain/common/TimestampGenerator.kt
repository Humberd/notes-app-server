package pl.humberd.notesapp.domain.common

import java.sql.Timestamp
import java.util.*

fun now() = Timestamp.from(Calendar.getInstance().toInstant())
