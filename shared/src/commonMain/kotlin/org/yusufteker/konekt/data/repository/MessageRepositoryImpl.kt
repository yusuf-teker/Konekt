package org.yusufteker.konekt.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.yusufteker.konekt.domain.repository.MessageRepository

class MessageRepositoryImpl : MessageRepository{
    private val messages = listOf(
        "Bugün üretken ol!",
        "Yeni görevleri tamamla!",
        "Küçük adımlar büyük fark yaratır."
    )

    override fun getDailyMessage(): StateFlow<String> = MutableStateFlow(messages.random())
}
