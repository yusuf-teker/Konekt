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
import org.yusufteker.konekt.util.DataError.RemoteType

fun DataError.Remote.toStringResource(): StringResource {
    return when (this.type) {

        RemoteType.REQUEST_TIMEOUT -> Res.string.error_request_timeout

        RemoteType.NO_INTERNET -> Res.string.error_no_internet

        RemoteType.UNAUTHORIZED -> Res.string.error_unauthorized

        RemoteType.FORBIDDEN -> Res.string.error_forbidden

        RemoteType.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests

        RemoteType.SERVER -> Res.string.error_server

        RemoteType.SERIALIZATION -> Res.string.error_serialization

        RemoteType.MISSING_TOKEN -> Res.string.error_unauthorized

        //RemoteType.BAD_REQUEST -> Res.string.error_bad_request

        RemoteType.UNKNOWN -> Res.string.error_remote_unknown
        else -> {
            Res.string.error_remote_unknown
        }
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