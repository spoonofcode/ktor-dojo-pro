package com.spoonofcode.core.domain

import com.google.firebase.messaging.FirebaseMessaging
import com.spoonofcode.core.model.MessageFCM
import com.spoonofcode.core.model.toMessage

class SendMessageFCMUseCase() {
    operator fun invoke(messageFCM: MessageFCM) {
        FirebaseMessaging.getInstance().send(messageFCM.toMessage())
    }
}