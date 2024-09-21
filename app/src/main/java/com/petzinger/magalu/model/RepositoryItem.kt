package com.petzinger.magalu.model

import com.google.gson.annotations.SerializedName

data class RepositoryItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("owner")
    val repositoryOwner: RepositoryOwner,
    @SerializedName("description")
    val description: String?,
    @SerializedName("stargazers_count")
    val starCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int
)