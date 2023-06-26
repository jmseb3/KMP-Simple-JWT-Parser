package com.wonddak.jwt

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

fun JsonObject.safeGet(key:String) : JsonElement? {
    return if (this.containsKey(key)) {
        this[key]
    } else {
        null
    }
}

fun JsonObject.safeGet(key:String, defaultValue :Any?) : JsonElement {
    return if (this.containsKey(key)) {
        this[key]!!
    } else {
        defaultValue.toJsonElement()
    }
}

fun Any?.toJsonElement(): JsonElement {
    return when (this) {
        is Number -> JsonPrimitive(this)
        is Boolean -> JsonPrimitive(this)
        is String -> JsonPrimitive(this)
        is Array<*> -> JsonArray(this.map { it.toJsonElement() })
        is List<*> -> JsonArray(this.map { it.toJsonElement() })
        is Map<*, *> -> this.toJsonObject()
        is JsonElement -> this
        else -> JsonNull
    }
}

fun Map<*, *>.toJsonObject(): JsonObject {
    val map = mutableMapOf<String, JsonElement>()
    this.forEach {
        if (it.key is String) {
            map[it.key as String] = it.value.toJsonElement()
        }
    }
    return JsonObject(map)
}