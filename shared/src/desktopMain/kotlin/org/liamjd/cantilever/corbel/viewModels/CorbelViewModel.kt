package org.liamjd.cantilever.corbel.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.liamjd.cantilever.corbel.models.SubmitUser
import org.liamjd.cantilever.corbel.services.CantileverService
import org.liamjd.cantilever.corbel.services.auth.AuthenticationService
import org.liamjd.cantilever.corbel.services.auth.CognitoAuthService

/**
 * Parent class storing the mode, the current user, etc etc
 */
class CorbelViewModel {
    private val authService: AuthenticationService = CognitoAuthService()
    private val cantileverService: CantileverService = CantileverService(authService)

    private val _mode = mutableStateOf(Mode.UNAUTHENTICATED)
    val mode: State<Mode>
        get() = _mode

    private var _authCode: String? = null

    private val _user = mutableStateOf(SubmitUser(username = null, password = null))
    val user: State<SubmitUser>
        get() = _user

    private val _windowTitle = mutableStateOf("Corbel Editor")
    val windowTitle: State<String>
        get() {
            return _windowTitle
        }

    /**
     * Initiate the login process by calling the authService.login() method.
     * Await for the Cognito auth code then refresh the UI model and store the auth code here
     */
    suspend fun login() {
        _mode.value = Mode.BUSY_AWAITING_AUTH
        _authCode = null
        val code = authService.login()
        delay(500)
        if (code != null) {
            println("Received code $code")
            _authCode = code
            _mode.value = Mode.VIEWING
            _windowTitle.value =
                "Corbel Editor (${_mode.value.name}) [${_user.value.username ?: ""}]"
            // prove it works by getting some real data
            cantileverService.getPostListJson(_authCode!!)
        }
    }

    /**
     * Call the logout function in the auth service and clear this model
     */
    fun logout() {
        runBlocking {
            authService.logout()
            _user.value = SubmitUser(null, null)
            _mode.value = Mode.UNAUTHENTICATED
            _windowTitle.value = "Corbel Editor (${_mode.value.name})"
        }
    }
}