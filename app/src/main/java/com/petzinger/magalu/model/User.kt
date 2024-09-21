package com.petzinger.magalu.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_uri")
    val avatarUri: String?
)
