package pl.humberd.notesapp.application.firebase.model

data class PushNotificationRequest(
    val title: String,
    val message: String,
    val topic: String,
    val token: String
)
