package org.liamjd.cantilever.corbel.services.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.uri
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.awt.Desktop
import java.io.IOException
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.Charset

/**
 * Implement authorization using the AWS Cognito service
 */
class CognitoAuthService : AuthenticationService {

    private var _authCode: String? = null
    override val authCode: String
        get() = _authCode ?: ""

    private var token: CognitoIDToken = CognitoIDToken()

    val server = embeddedServer(Netty, port = 44817, configure = {
        connectionGroupSize = 2
    }) {
        install(Routing)

        routing {
            get("/callback") {
                _authCode = null
                println("Callback received by server for ${call.request.uri}")
                val code = call.request.queryParameters["code"]
                println("Ktor: Received an auth code : '$code'")
                call.respondText("Authentication code received. Please close this browser window and return to the Corbel application")
                if (code != null) {
                    _authCode = code
                }
            }
        }
    }.start(wait = false)

    // TODO: Move these to a config file
    private val poolId = "eu-west-2_aSdFDvU0j"
    private val clientAppId = "128mn8agtcn7d7giqn0gk9rhni"
    private val fedPoolId = ""
    private val customDomain = "https://cantilever.auth.eu-west-2.amazoncognito.com"
    private val region = "eu-west-2"
    private val callbackUrl = "http://localhost:44817/callback"

    @Suppress("NewApi")
    private val encodedCallbackUrl =
        URLEncoder.encode(callbackUrl, Charset.defaultCharset())


    private val client = HttpClient(CIO) {
        install(Logging) {
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
//                    println("Ktor Client: $message")
                }
            }
        }
        install(ContentNegotiation) {
            json()
        }
    }

    /**
     * Log in to AWS Cognito by:
     * - Opening a web browser to the Cognito Hosted UI
     * - Waiting for an authorization code. This will be set by the embedded server listening to the AWS Cognito callback
     * This waits indefinitely; I should change that!
     */
    override suspend fun login(): String? {
        var code: String?
        val cognitoUrl =
            "https://cantilever.auth.eu-west-2.amazoncognito.com/oauth2/authorize?response_type=code&client_id=$clientAppId&redirect_uri=$encodedCallbackUrl&scope=aws.cognito.signin.user.admin+email+openid"
        try {
            if (Desktop.isDesktopSupported()) {
                val desktop = Desktop.getDesktop()
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    println("Browsing to $cognitoUrl")
                    withContext(Dispatchers.IO) {
                        desktop.browse(URI.create(cognitoUrl))
                    }
                }
            }
        } catch (ioe: IOException) {
            println("Unable to open browser for authentication")
            return null
        }

        val job = GlobalScope.launch {
            code = waitForAuthCodeOrNull()
            _authCode = code
        }
        job.join()
        if (_authCode == null) {
            println("Timeout while waiting for authentication code.")
        }
        return _authCode
    }

    /**
     * Wait for 30 seconds or return as soon as authCode has a value
     */
    private suspend fun waitForAuthCodeOrNull(): String? {
        return withTimeoutOrNull(30_000) {
            while (true) {
                if (_authCode != null) {
                    return@withTimeoutOrNull _authCode
                }
                delay(100)
            }
            null
        }
    }


    /**
     * All the AWS Cognito logout URL which should clear our auth token
     */
    override suspend fun logout() {
        val cognitoUrl =
            "https://cantilever.auth.eu-west-2.amazoncognito.com/logout?client_id=$clientAppId&logout_uri=$encodedCallbackUrl&scope=aws.cognito.signin.user.admin+email+openid"
        val logoutResponse = client.request(cognitoUrl) {
            method = HttpMethod.Get
        }
        println("Ktor Client: Logout response: $logoutResponse")
        _authCode = null
    }

    override suspend fun getToken(authCode: String): CognitoIDToken {
        println("Calling getToken() for code: $authCode")
        val grantType = "authorization_code"
        val refreshType = "refresh_token"
        val getTokenURL =
            "https://cantilever.auth.eu-west-2.amazoncognito.com/oauth2/token"

        println("Current token: $token; isExpired=${token.expired()}")

        if (token.isEmpty()) {
            val tokenResponse = client.post(getTokenURL) {
                headers {
                    contentType(ContentType.Application.FormUrlEncoded)
                    accept(ContentType.Application.Json)
                }
                setBody(
                    FormDataContent(
                        Parameters.build {
                            append("grant_type", grantType)
                            append("client_id", clientAppId)
                            append("code", authCode)
                            append("redirect_uri", callbackUrl)
                        }
                    )
                )
            }
            token = tokenResponse.body<CognitoIDToken>()
        } else if (token.expired()) {
            println("Token has expired; refresh it? {created ${token.createdTime}}")
            val refreshResponse = client.post(getTokenURL) {
                headers {
                    contentType(ContentType.Application.FormUrlEncoded)
                    accept(ContentType.Application.Json)
                }
                setBody(
                    FormDataContent(
                        Parameters.build {
                            append("grant_type", refreshType)
                            append("client_id", clientAppId)
                            append("refresh_token", token.refreshToken)
                            append("redirect_uri", callbackUrl)
                        }
                    )
                )
            }
            val refreshedToken = refreshResponse.body<CognitoRefreshToken>()
            println("RefreshToken response: $refreshedToken")
            token = token.refresh(refreshedToken)
        }
        println("Got or updated token. Is now $token")
        return token
    }
}