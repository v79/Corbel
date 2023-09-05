package org.liamjd.cantilever.corbel.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CantileverPost(
    @SerialName("data")
    val `data`: Data
)