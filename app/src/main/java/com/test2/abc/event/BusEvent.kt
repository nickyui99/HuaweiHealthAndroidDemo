package com.test2.abc.event

sealed class BusEvent {
    data class UnauthorizeHealthKitEvent(
        var success: Boolean,
        var errorMessage: String? = ""
    ): BusEvent()

    data class RefreshTokenEvent(
        var success: Boolean,
        var errorMessage: String? = ""
    ): BusEvent()
}