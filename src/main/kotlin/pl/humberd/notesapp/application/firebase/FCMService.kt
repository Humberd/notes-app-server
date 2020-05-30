package pl.humberd.notesapp.application.firebase

import com.google.firebase.messaging.*
import mu.KotlinLogging
import org.springframework.stereotype.Service
import pl.humberd.notesapp.application.firebase.model.PushNotificationParameter
import pl.humberd.notesapp.application.firebase.model.PushNotificationRequest
import java.time.Duration
import java.util.concurrent.ExecutionException


private val logger = KotlinLogging.logger {}

@Service
class FCMService {

    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendMessage(
        data: Map<String, String>,
        request: PushNotificationRequest
    ) {
        val message = getPreconfiguredMessageWithData(data, request)
        val response = sendAndGetResponse(message)
        logger.info("Sent message with data. Topic: " + request.topic + ", " + response)
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendMessageWithoutData(request: PushNotificationRequest) {
        val message = getPreconfiguredMessageWithoutData(request)
        val response = sendAndGetResponse(message)
        logger.info("Sent message without data. Topic: " + request.topic + ", " + response)
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    fun sendMessageToToken(request: PushNotificationRequest) {
        val message = getPreconfiguredMessageToToken(request)
        val response = sendAndGetResponse(message)
        logger.info("Sent message to token. Device token: " + request.token + ", " + response)
    }

    @Throws(InterruptedException::class, ExecutionException::class)
    private fun sendAndGetResponse(message: Message): String {
        return FirebaseMessaging.getInstance().sendAsync(message).get()
    }

    private fun getAndroidConfig(topic: String): AndroidConfig {
        return AndroidConfig.builder()
            .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
            .setPriority(AndroidConfig.Priority.HIGH)
            .setNotification(
                AndroidNotification.builder()
                    .setSound(PushNotificationParameter.SOUND.value)
                    .setColor(PushNotificationParameter.COLOR.value)
                    .setTag(topic)
                    .build()
            ).build()
    }

    private fun getApnsConfig(topic: String): ApnsConfig {
        return ApnsConfig.builder()
            .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build()
    }

    private fun getPreconfiguredMessageToToken(request: PushNotificationRequest): Message {
        return getPreconfiguredMessageBuilder(request).setToken(request.token)
            .build()
    }

    private fun getPreconfiguredMessageWithoutData(request: PushNotificationRequest): Message {
        return getPreconfiguredMessageBuilder(request).setTopic(request.topic)
            .build()
    }

    private fun getPreconfiguredMessageWithData(
        data: Map<String, String>,
        request: PushNotificationRequest
    ): Message {
        return getPreconfiguredMessageBuilder(request)
            .putAllData(data)
            .setTopic(request.topic)
            .build()
    }

    private fun getPreconfiguredMessageBuilder(request: PushNotificationRequest): Message.Builder {
        val androidConfig = getAndroidConfig(request.topic)
        val apnsConfig = getApnsConfig(request.topic)
        return Message.builder()
            .setApnsConfig(apnsConfig)
            .setAndroidConfig(androidConfig)
            .setNotification(
                Notification.builder()
                    .setTitle(request.title)
                    .setBody(request.message)
                    .build()
            )
    }

}
