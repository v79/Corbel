package org.liamjd.cantilever.corbel.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("count")
    val count: Int,
    @SerialName("lastUpdated")
    val lastUpdated: String,
    @SerialName("posts")
    val posts: List<Post>
)