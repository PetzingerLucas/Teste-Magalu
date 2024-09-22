package com.petzinger.magalu.model

import com.google.gson.annotations.SerializedName

data class RepositoryOwner(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String?
)
