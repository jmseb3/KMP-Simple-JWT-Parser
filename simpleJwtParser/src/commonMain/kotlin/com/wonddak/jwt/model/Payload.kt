package com.wonddak.jwt.model

import com.wonddak.jwt.safeGet
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long

data class Payload(
    private val jsonObject: JsonObject
) {
    /** Issuer (발행자) */
    val iss: String
        get() = jsonObject.safeGet("iss", "").jsonPrimitive.content

    /** Subject (주제 또는 사용자) */
    val sub: String
        get() = jsonObject.safeGet("sub", "").jsonPrimitive.content

    /** Audience (대상자) */
    val aud: String
        get() = jsonObject.safeGet("aud", "").jsonPrimitive.content


    /** Expiration time (만료 시간, timestamp) */
    val exp: Long
        get() = jsonObject.safeGet("exp", 0).jsonPrimitive.long


    /** Not Before (이 시간 이전에는 유효하지 않음, timestamp) */
    val nbf: Long
        get() = jsonObject.safeGet("nbf", 0).jsonPrimitive.long


    /** Issued At (발행 시간, timestamp) */
    val iat: Long
        get() = jsonObject.safeGet("iat", 0).jsonPrimitive.long


    /** JWT ID (고유 식별자) */
    val jti: String
        get() = jsonObject.safeGet("jti", "").jsonPrimitive.content

    /**
     * get custom payload value
     */
    fun get(key: String, defaultValue: Any): JsonElement {
        return jsonObject.safeGet(key, defaultValue)
    }
}
