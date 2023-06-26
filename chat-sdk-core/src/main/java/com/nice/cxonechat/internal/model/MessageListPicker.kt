package com.nice.cxonechat.internal.model

import com.nice.cxonechat.internal.model.MessageModel.Companion.author
import com.nice.cxonechat.internal.model.network.MessagePolyContent
import com.nice.cxonechat.message.Action
import com.nice.cxonechat.message.Attachment
import com.nice.cxonechat.message.Message.ListPicker
import com.nice.cxonechat.message.MessageAuthor
import com.nice.cxonechat.message.MessageDirection
import com.nice.cxonechat.message.MessageMetadata
import java.util.Date
import java.util.UUID

internal data class MessageListPicker(
    private val model: MessageModel,
) : ListPicker() {

    private val content
        get() = model.messageContent as MessagePolyContent.ListPicker

    override val id: UUID
        get() = model.idOnExternalPlatform
    override val threadId: UUID
        get() = model.threadIdOnExternalPlatform
    override val createdAt: Date
        get() = model.createdAt
    override val direction: MessageDirection
        get() = model.direction.toMessageDirection()
    override val metadata: MessageMetadata
        get() = model.userStatistics.toMessageMetadata()
    override val author: MessageAuthor
        get() = model.author
    override val attachments: Iterable<Attachment>
        get() = model.attachments.map(AttachmentModel::toAttachment)

    override val title: String
        get() = content.payload.title.content
    override val text: String
        get() = content.payload.text.content
    override val fallbackText: String?
        get() = content.fallbackText
    override val actions: List<Action>
        get() = content.payload.actions.map(ActionInternal::create)

    override fun toString() = buildString {
        append("Message.ListPicker(id=")
        append(id)
        append(", threadId=")
        append(threadId)
        append(", createdAt=")
        append(createdAt)
        append(", direction=")
        append(direction)
        append(", metadata=")
        append(metadata)
        append(", author=")
        append(author)
        append(", attachments=")
        append(attachments)
        append(", title=")
        append(title)
        append(", text=")
        append(text)
        append(", fallbackText=")
        append(fallbackText)
        append(", actions=")
        append(actions)
        append(")")
    }
}