package org.liamjd.cantilever.corbel.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.encodeURLPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.liamjd.cantilever.corbel.services.auth.AuthenticationService

class CantileverService(private val authService: AuthenticationService) {

    // TODO: Move these to a config file
    private val poolId = "eu-west-2_aSdFDvU0j"
    private val clientAppId = "128mn8agtcn7d7giqn0gk9rhni"
    private val fedPoolId = ""
    private val customDomain = "https://cantilever.auth.eu-west-2.amazoncognito.com"
    private val region = "eu-west-2"
    private val callbackUrl = "http://localhost:44817/callback"

    @Suppress("NewApi")
    private val localCallbackUrl = callbackUrl.encodeURLPath()

    private val client = HttpClient(CIO) {
        engine {
            requestTimeout =
                30_000L // better to shorten this but start the ping as soon as the app starts? Ping doesn't require auth does it?
        }
        install(Logging) {
            logger =
                object : Logger {
                    override fun log(message: String) {
                        println("Ktor Client: $message")
                    }
                }
        }
        install(ContentNegotiation) {
            json()
        }
    }

    /**
     * Make a call to pre-warm the API Lambda router function, returns nothing
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun warm() {
        GlobalScope.launch {
            val url = "https://api.cantilevers.org/warm"
            client.get(url) {
                accept(ContentType.Text.Plain)
            }
        }
    }

    /**
     * Get posts.json. Eventually, replace this with deserialized list of Post objects
     */
    suspend fun getPostListJson(): String {
        println("Getting post list json file")
        val url = "https://api.cantilevers.org/project/posts"
        val token = authService.getToken(authService.authCode)
        println("Token: ${token.tokenType} ${token.createdTime} expiresIn: ${token.expiresIn}")

        // default timeout might be too strict for these lambdas

        val response = client.get(url) {
            headers {
                accept(ContentType.Application.Json)
                bearerAuth(token.idToken)
            }
        }

        println("Post list json:")
        println(response.body<String>())
        return response.body<String>()
    }


}