package org.liamjd.cantilever.corbel.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    @SerialName("date")
    val date: String,
    @SerialName("lastUpdated")
    val lastUpdated: String,
    @SerialName("srcKey")
    val srcKey: String,
    @SerialName("templateKey")
    val templateKey: String,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String
)