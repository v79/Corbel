package org.liamjd.cantilever.corbel.services

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import org.liamjd.cantilever.corbel.services.auth.CognitoIDToken
import java.net.URLEncoder
import java.nio.charset.Charset

class CantileverService {

    // TODO: Move these to a config file
    private val poolId = "eu-west-2_aSdFDvU0j"
    private val clientAppId = "128mn8agtcn7d7giqn0gk9rhni"
    private val fedPoolId = ""
    private val customDomain = "https://cantilever.auth.eu-west-2.amazoncognito.com"
    private val region = "eu-west-2"

    private val callbackUrl = "http://localhost:44817/callback"

    @Suppress("NewApi")
    private val localCallbackUrl =
        URLEncoder.encode(callbackUrl, Charset.defaultCharset())

    private val client = HttpClient(CIO) {
        install(Logging) {
            logger = object : Logger {
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
     * Get posts.json. Eventually, replace this with deserialized list of Post objects
     */
    suspend fun getPostListJson(authCode: String): String {
        val url = "https://api.cantilevers.org/project/posts"
        val token = getToken(authCode)

        val response = client.get(url) {
            headers {
                accept(ContentType.Application.Json)
                bearerAuth(token)
            }
        }

        println("Post list json:")
        println(response.body<String>())
        return "{}"
    }

    /**
     * Get a Bearer token for the supplied Cognito authorization code
     */
    private suspend fun getToken(authCode: String): String {
        println("Calling getToken() for code: $authCode")
        val grantType = "authorization_code"
        val getTokenURL =
            "https://cantilever.auth.eu-west-2.amazoncognito.com/oauth2/token"

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
        val idToken: CognitoIDToken = tokenResponse.body()

        return idToken.accessToken
    }
}