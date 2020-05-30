package pl.humberd.notesapp.application.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.IOException
import javax.annotation.PostConstruct

private val logger = KotlinLogging.logger {}

@Configuration
class FCMInitializer(
    @Value("\${firebase.admin-sdk}")
    private val firebaseConfigPath: String
) {

    @PostConstruct
    fun initialize() {
        try {
            val inputStream = ClassPathResource(firebaseConfigPath).getInputStream()
            val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build()
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
                logger.info("Firebase application has been initialized")
            }
        } catch (e: IOException) {
            throw e
        }
    }
}
