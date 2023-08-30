package org.liamjd.cantilever.corbel.services.auth

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.uri
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.liamjd.cantilever.corbel.models.SubmitUser
import java.awt.Desktop
import java.io.IOException
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.Charset

/**
 * Implement authorization using the AWS Cognito service
 */
class CognitoAuthService : AuthenticationService {

    private var authCode: String? = null

    val server = embeddedServer(Netty, port = 44817, configure = {
        connectionGroupSize = 2
    }) {
        install(Routing)

        routing {
            get("/callback") {
                authCode = null
                println("Callback received by server for ${call.request.uri}")
                val code = call.request.queryParameters["code"]
                println("Ktor: Received an auth code : '$code'")
                call.respondText("Authentication code received. Please close this browser window and return to the Corbel application")
                if (code != null) {
                    authCode = code
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

    @Suppress("NewApi")
    private val localCallbackUrl =
        URLEncoder.encode("http://localhost:44817/callback", Charset.defaultCharset())


    private val client = HttpClient(CIO) {
        install(Logging) {
            logger = object : io.ktor.client.plugins.logging.Logger {
                override fun log(message: String) {
                    println("Ktor Client: $message")
                }
            }
        }
    }

    /**
     * Log in to AWS Cognito by:
     * - Opening a web browser to the Cognito Hosted UI
     * - Waiting for an authorization code. This will be set by the embedded server listening to the AWS Cognito callback
     * This waits indefinitely; I should change that!
     */
    override suspend fun login(user: SubmitUser): String? {
        val cognitoUrl =
            "https://cantilever.auth.eu-west-2.amazoncognito.com/oauth2/authorize?response_type=code&client_id=$clientAppId&redirect_uri=$localCallbackUrl&scope=aws.cognito.signin.user.admin+email+openid"
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

        while (authCode == null) {
            print('.')
        }
        return authCode
    }

    /**
     * All the AWS Cognito logout URL which should clear our auth token
     */
    override suspend fun logout() {
        val cognitoUrl =
            "https://cantilever.auth.eu-west-2.amazoncognito.com/logout?client_id=$clientAppId&logout_uri=$localCallbackUrl&scope=aws.cognito.signin.user.admin+email+openid"
        val logoutResponse = client.request(cognitoUrl) {
            method = HttpMethod.Get
        }
        println("Ktor Client: Logout response: $logoutResponse")
        authCode = null
    }
}