package com.manalili.hpQuizKotlin.fb.send

import com.fasterxml.jackson.annotation.JsonProperty
import com.manalili.hpQuizKotlin.fb.common.Recipient
import javax.validation.constraints.Max

class MessageToSend(

//TODO
//Replace with enum if possible *senderAction*
        @JsonProperty("messaging_type")
        val messagingType: MessagingType = MessagingType.RESPONSE,

        recipient: String,

        @JsonProperty("message")
        val message: MessageContent? = null,
        @JsonProperty("sender_action")
        var senderAction: String? = null, //SenderAction to replace
        @JsonProperty("notification_type")
        val notificationType: NotificationType = NotificationType.REGULAR,
        val tag: String? = null

) {
    @JsonProperty("recipient")
    val recipient = Recipient(recipient)
}

class MessageContent(
        var text: String? = null,
        val attachment: Attachment? = null,
        @JsonProperty("quick_replies")
        val quickReplies: List<QuickReply>? = null,
        val metadata: String? = null
)
class Attachment(
        val type: String,
        val payload: String
)

class QuickReply(
        @JsonProperty("content_type")
        val contentType: String, //ContentType to replace
        @Max(value = 20)
        val title: String? = null,
        @Max(value = 1000)
        val payload: String? = null,
        val image_url: String? = null

)

enum class MessagingType {
    RESPONSE,
    UPDATE,
    MESSAGE_TAG
}

enum class ContentType{
    TEXT,
    LOCATION,
    USER_PHONE_NUMBER,
    USER_EMAIL
}

enum class SenderAction{
    TYPING_ON,
    TYPING_OFF,
    MARK_SEEN
}

enum class NotificationType{
    REGULAR,
    SILENT_PUSH,
    NO_PUSH
}
