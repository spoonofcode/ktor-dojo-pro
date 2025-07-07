package com.spoonofcode.core.domain

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import com.spoonofcode.core.model.MessageFCM

class SendMessageFCMUseCase() {
    operator fun invoke(messageFCM: MessageFCM) {
//        FirebaseMessaging.getInstance().send(messageFCM.toMessage())
//        FirebaseMessaging.getInstance().sendEachForMulticast(messageFCM.toMessageMulti())

        val dataPayload = mapOf(
            "screen" to "detail",
            "item_id" to "123"
        )

        if (messageFCM.topics.isNullOrEmpty().not()) {
            messageFCM.topics.forEach { topic ->
                FirebaseMessaging.getInstance().send(
                    Message.builder()
                        .setNotification(
                            Notification.builder()
                                .setTitle(messageFCM.notification.title)
                                .setBody(messageFCM.notification.body)
                                .setImage("https://i.seadn.io/s/raw/files/ec3d7f1edc5e5b3699b8d5c1d1e0623a.png")
                                .build()
                        ).setTopic(topic)
                        .putAllData(dataPayload)
                        .build()
                )
            }
//                FirebaseMessaging.getInstance().send(
//                    Message.builder()
//                        .setNotification(
//                            Notification.builder()
//                                .setTitle(messageFCM.notification.title)
//                                .setBody(messageFCM.notification.body)
//                                .build()
//                        ).setTopic("chat").build()
//                )

        } else {
            val token1 =
                "efl4kqCoR46sxpOSbGpBAe:APA91bFbmhqCWVy7yclK10IqdwFpIpgtjMC64aku29ZgihHqm9EEZMHloSCaAakMs456Y9RHPsc8AaLv7QgwmGadiKZk8V5xAPVF2RQhmOB1QiwEFpGBMS4"
            val token2 =
                "cGb6Rd9mTnG5iE0Zm9jW8r:APA91bFX62ylHN8XfvnRBeCAXSVCoHoBAIEyN4TuLHSjb4-kQsbqvBAn2yD5zfxRRufs5-3q8iR77MdIMaIrCLRhvimswVqGG0Sbw_QGnLOKoMH9rZSA1wQ"
            FirebaseMessaging.getInstance().sendEachForMulticast(
                MulticastMessage.builder()
                    .putData("type", "info")
                    .setNotification(
                        Notification.builder()
                            .setTitle(messageFCM.notification.title)
                            .setBody(messageFCM.notification.body)
                            .build()
                    )
                    .addAllTokens(listOf(token1, token2))
                    .build()
            )
        }

    }
}