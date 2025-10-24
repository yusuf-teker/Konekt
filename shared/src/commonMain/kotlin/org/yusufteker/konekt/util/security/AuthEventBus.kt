package org.yusufteker.konekt.util.security

// shared module
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AuthEventBus {
    private val _events = MutableSharedFlow<AuthEvent>(replay = 0)
    val events = _events.asSharedFlow()

    suspend fun emit(event: AuthEvent) {
        _events.emit(event)
    }
}

sealed class AuthEvent {
    object LoggedOut : AuthEvent()
}
