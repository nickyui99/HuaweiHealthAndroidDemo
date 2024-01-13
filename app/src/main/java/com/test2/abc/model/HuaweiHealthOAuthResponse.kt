package com.test2.abc.model

import com.google.gson.annotations.SerializedName
data class HuaweiHealthOAuthResponse(
    @SerializedName("scope")
    val scope: String,

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("token_type")
    val tokenType: String,

    @SerializedName("expires_in")
    val expiresIn: Int,

    @SerializedName("id_token")
    val idToken: String
)