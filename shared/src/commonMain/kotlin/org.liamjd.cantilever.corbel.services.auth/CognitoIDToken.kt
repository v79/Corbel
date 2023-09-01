package org.liamjd.cantilever.corbel.services.auth


import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing the Cognito token (most likely Bearer token) in response to an oauth2 code
 */
@Serializable
class CognitoIDToken(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("id_token")
    val idToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("token_type")
    val tokenType: String
) {

    val createdTime: Instant = Clock.System.now()

    constructor() : this("", 0, "", "", "")

    fun isEmpty() = accessToken.isEmpty()

    /**
     * Returns true if the difference between now and the createdTime is greater than the expiresIn value (seconds)
     */
    fun expired() = Clock.System.now().minus(createdTime).inWholeSeconds > expiresIn

}

/**
 * Build a new [CognitoIDToken] from the supplied [CognitoRefreshToken]
 */
fun CognitoIDToken.refresh(refreshToken: CognitoRefreshToken) = CognitoIDToken(
    accessToken = refreshToken.accessToken,
    expiresIn = refreshToken.expiresIn,
    idToken = refreshToken.idToken,
    refreshToken = this.refreshToken,
    tokenType = this.tokenType
)