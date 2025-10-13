package org.yusufteker.konekt.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface MessageRepository {
    fun getDailyMessage(): StateFlow<String>
}
