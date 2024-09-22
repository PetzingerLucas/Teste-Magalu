package com.petzinger.magalu.model

import com.google.gson.annotations.SerializedName

data class PullRequest(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("user")
    val user: User,
    @SerializedName("html_url")
    val url: String
)
