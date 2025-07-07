package com.spoonofcode.core.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageFCM(
    val token: String? = null,
    val topics: List<String>? = null,
    val notification: NotificationFCM,
)

@Serializable
data class NotificationFCM(
    val title: String,
    val body: String,
)

//fun MessageFCM.toMessage(): Message {
//    return Message.builder()
//        .setNotification(
//            Notification.builder()
//                .setTitle(notification.title)
//                .setBody(notification.body)
//                .build()
//        )
//        .apply {
//            if (to == null) {
//                setTopic("chat")
//            } else {
//                setToken(to)
//            }
//        }
//        .build()
//}