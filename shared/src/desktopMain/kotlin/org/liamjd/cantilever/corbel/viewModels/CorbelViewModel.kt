package org.liamjd.cantilever.corbel.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.liamjd.cantilever.corbel.models.SubmitUser
import org.liamjd.cantilever.corbel.services.auth.AuthenticationService
import org.liamjd.cantilever.corbel.services.auth.CognitoAuthService

/**
 * Parent class storing the mode, the current user, etc etc
 */
class CorbelViewModel {
    private val authService: AuthenticationService = CognitoAuthService()

    private val _mode = mutableStateOf(Mode.UNAUTHENTICATED)
    val mode: State<Mode>
        get() = _mode

    private val _user = mutableStateOf(SubmitUser(username = null, password = null))
    val user: State<SubmitUser>
        get() = _user

    private val _windowTitle = mutableStateOf("Corbel Editor")
    val windowTitle: State<String>
        get() {
            return _windowTitle
        }

    // these would actually call a Service to authenticate (via AWS Cognito)
    fun login(newUser: SubmitUser) {
        _mode.value = Mode.BUSY

        println("Calling auth service")
        val authCode =
            authService.login(newUser) // this doesn't wait for a response, and given that login starts an out-of-process web browser connection, I don't think I can make it wait

        if (!authCode.isNullOrEmpty()) {
            _user.value = newUser
            _mode.value = Mode.VIEWING
            _windowTitle.value =
                "Corbel Editor (${_mode.value.name}) [${_user.value.username ?: ""}]"
        } else {
            println("No authentication code was received.")
        }

    }

    fun logout() {
        _user.value = SubmitUser(null, null)
        _mode.value = Mode.UNAUTHENTICATED
        _windowTitle.value = "Corbel Editor (${_mode.value.name})"
    }
}