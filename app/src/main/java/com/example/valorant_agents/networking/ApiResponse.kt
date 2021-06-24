package com.example.valorant_agents.networking

import com.google.gson.annotations.SerializedName

/**
 * Custom representation for the returned json from backend.
 *
 * @param result 1 if success, -1 if error.
 * @param error custom backend error.
 */
class ApiResponse<T>(
    @SerializedName("status")
    val status: Int?,
    @SerializedName("error")
    val error: BrandError? = null,
    @SerializedName("data")
    val data: T?
)

/**
 * Custom backend error that usually returned with [ApiResponse].
 */
data class BrandError(
    @SerializedName("messageId")
    val messageId: Long,
    @SerializedName("message")
    val message: String
)