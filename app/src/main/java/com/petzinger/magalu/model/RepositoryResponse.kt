package com.petzinger.magalu.model

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @SerializedName("total_count")
    val totalCount: String,
    @SerializedName("items")
    val items: List<RepositoryItem>
)