package org.yusufteker.konekt.base
interface BaseState {
    val isLoading: Boolean
    fun copyWithLoading(isLoading: Boolean): BaseState
}
