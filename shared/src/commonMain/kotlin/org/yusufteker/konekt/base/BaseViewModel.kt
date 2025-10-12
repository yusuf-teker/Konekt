package org.yusufteker.konekt.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface BaseViewModel<S : BaseState> {
    val state: StateFlow<S>
    fun setState(reducer: S.() -> S)
    fun launchWithLoading(block: suspend () -> Unit)
}

open class BaseViewModelImpl<S : BaseState>(
    initialState: S
) : BaseViewModel<S> {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<S> = _state

    private var activeLoadingJobs = 0

    override fun setState(reducer: S.() -> S) {
        _state.update(reducer)
    }

    override fun launchWithLoading(block: suspend () -> Unit) {
        viewModelScope.launch {
            incrementLoading()
            try {
                block()
            } finally {
                decrementLoading()
            }
        }
    }

    private fun incrementLoading() {
        activeLoadingJobs++
        setLoading(true)
    }

    private fun decrementLoading() {
        activeLoadingJobs--
        if (activeLoadingJobs <= 0) {
            activeLoadingJobs = 0
            setLoading(false)
        }
    }

    private fun setLoading(loading: Boolean) {
        _state.update { it.copyWithLoading(loading) as S }
    }
}
