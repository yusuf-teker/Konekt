package org.yusufteker.konekt.ui.utils

import konekt.composeapp.generated.resources.Res
import konekt.composeapp.generated.resources.error_disk_full
import konekt.composeapp.generated.resources.error_forbidden
import konekt.composeapp.generated.resources.error_local_unknown
import konekt.composeapp.generated.resources.error_no_internet
import konekt.composeapp.generated.resources.error_remote_unknown
import konekt.composeapp.generated.resources.error_request_timeout
import konekt.composeapp.generated.resources.error_serialization
import konekt.composeapp.generated.resources.error_server
import konekt.composeapp.generated.resources.error_too_many_requests
import konekt.composeapp.generated.resources.error_unauthorized
import org.jetbrains.compose.resources.StringResource
import org.yusufteker.konekt.ui.base.UiText
import org.yusufteker.konekt.util.DataError


fun DataError.Remote.toStringResource(): StringResource {
    return when (this) {
        DataError.Remote.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.UNAUTHORIZED -> Res.string.error_unauthorized
        DataError.Remote.FORBIDDEN -> Res.string.error_forbidden
        DataError.Remote.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.Remote.SERVER -> Res.string.error_server
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_remote_unknown
        DataError.Remote.MISSING_TOKEN -> Res.string.error_unauthorized
    }
}

fun DataError.Local.toStringResource(): StringResource {
    return when (this) {
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.UNKNOWN -> Res.string.error_local_unknown
    }
}

fun DataError.Remote.toUiText(): UiText {
    return UiText.StringResourceId(this.toStringResource())
}

fun DataError.Local.toUiText(): UiText {
    return UiText.StringResourceId(this.toStringResource())
}