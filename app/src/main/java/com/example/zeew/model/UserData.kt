package com.example.zeew.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserData2(
    @SerializedName("result")
    val result: Result,
    @SerializedName("status")
    val status: String
):Serializable

data class Result(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Int
):Serializable