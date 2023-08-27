package org.liamjd.cantilever.corbel.services.auth

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.Logging
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.request.uri
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.liamjd.cantilever.corbel.models.SubmitUser
import java.awt.Desktop
import java.io.IOException
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.Charset

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
                println("Received an auth code : '$code'")
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

    override fun login(user: SubmitUser): String? {

//        https://cantilever.auth.eu-west-2.amazoncognito.com/login?client_id=128mn8agtcn7d7giqn0gk9rhni&response_type=code&scope=aws.cognito.signin.user.admin+email+openid&redirect_uri=corbelApp%3A%2F%2Fauth
        // don't need user here, really

        val cognitoUrl =
            "https://cantilever.auth.eu-west-2.amazoncognito.com/oauth2/authorize?response_type=code&client_id=$clientAppId&redirect_uri=$localCallbackUrl&scope=aws.cognito.signin.user.admin+email+openid"
        try {
            if (Desktop.isDesktopSupported()) {
                val desktop = Desktop.getDesktop()
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    println("Browsing to $cognitoUrl")
                    desktop.browse(URI.create(cognitoUrl))
                }
            }
        } catch (ioe: IOException) {
            println("Unable to open browser for authentication")
            return null
        }

        /* val testRes: HttpResponse = client.request("https://api.cantilevers.org/project/pages") {
             method = HttpMethod.Get
             headers {
                 append(HttpHeaders.Accept, "application/json")
                 append(
                     HttpHeaders.Authorization,
                     "Bearer eyJraWQiOiJlWERpXC81aVZiMTFmdWc1cmlYYTNHRndXSzBjZWlBYjNOMTN6Y0NxVFlkaz0iLCJhbGciOiJSUzI1NiJ9.eyJhdF9oYXNoIjoidHFHTk45UDB6SVVRUTJpM1EwN2pXUSIsInN1YiI6ImEwYWQ4NzMxLTQ5OTgtNDAwMS1hZGYwLTFhYTlhZTY2NzQ3NyIsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC5ldS13ZXN0LTIuYW1hem9uYXdzLmNvbVwvZXUtd2VzdC0yX2FTZEZEdlUwaiIsImNvZ25pdG86dXNlcm5hbWUiOiJhMGFkODczMS00OTk4LTQwMDEtYWRmMC0xYWE5YWU2Njc0NzciLCJhdWQiOiI2aWpiNmJnM2hrMjJzZWxxNnJqMmJiNXJtcSIsImV2ZW50X2lkIjoiMjFjN2M0MjktMzlmZi00YzljLWJmNGUtNzU1NGU2OGZkYWZmIiwidG9rZW5fdXNlIjoiaWQiLCJhdXRoX3RpbWUiOjE2OTMxMjU4OTksIm5hbWUiOiJsaWFtIiwiZXhwIjoxNjkzMTI5NDk5LCJpYXQiOjE2OTMxMjU4OTksImp0aSI6IjIyYjMyNmJhLTZkMzktNDY0ZC1iZGRiLTc0NTg1OWY3YzEzMyIsImVtYWlsIjoibGlhbWpkYXZpc29uQGdtYWlsLmNvbSJ9.alV_fE0uRWoicDt8q25KDXirSwnL5Dwxeb_upguXi9NgHul6qniXlqbJ5QiyER0h1Q03E5blaZ4mb_zwevPRz-XqE2FrYVeyW1ogyzW7BSG8RQ5dEunHgXn9qvcnhqHPtXLBWxI7ZuN1IFUDfmA23eCspBeKzd8zFVYX1Jol1OrEU7UbPqnvxCMmx6owg687tBe4gKFfSMSg5ncI_jqQWbbnxi_yZ2TgFBDhOnUxWOx0UMB-hv1zBJv3W15F9cItIPDzrSvElouuBl7y2N2b3iF9EGwcsIwbolEfOa8G78FX1BeX5rloKhzHdJ-M7VM41Tq8exKJqBV8Y-xL6_QGwQ"
                 )
             }
         }

         println(testRes)


 */

        return authCode
    }
}