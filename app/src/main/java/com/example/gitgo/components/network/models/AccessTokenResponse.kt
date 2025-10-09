package com.example.gitgo.components.network.models

import com.google.gson.annotations.SerializedName

data class AccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String
)