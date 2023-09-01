package org.liamjd.cantilever.corbel.services.auth


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CognitoRefreshToken(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("id_token")
    val idToken: String,
    @SerialName("token_type")
    val tokenType: String
)