package com.wonddak.jwt

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class JwtParserTest {
    //TODO Android Base64 Mock

    companion object {
        const val TEST_TOKEN =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IldvbmRkYWsiLCJpYXQiOjE1MTYyMzkwMjIsInRlc3QiOjEyM30.ZbUhYv6Jf4AJW4os-ukU28sojXvJGzKwuxMWkfc8Poo"
        /*
        {
          "sub": "1234567890",
          "name": "Wonddak",
          "iat": 1516239022,
          "test": 123
        }
         */
    }

    private lateinit var jwtParser: JwtParser
    private lateinit var jsonObject: JsonObject

    @BeforeTest
    fun setup() {
        jwtParser = JwtParser()
        jsonObject = jwtParser.parseToJsonObject(TEST_TOKEN)!!
    }

    @Test
    fun test1() {
        assertTrue(
            jsonObject.safeGet("test", "123").jsonPrimitive.content == "123",
            "test is not 123"
        )
    }

    @Test
    fun test2() {
        assertTrue(
            jsonObject.safeGet("name", "").jsonPrimitive.content == "Wonddak",
            "name is not Wonddak"
        )
    }

    @Test
    fun test3() {
        assertTrue(
            jsonObject.safeGet("test333", "").jsonPrimitive.content == "",
            "test33 is not empty"
        )
    }

    @Test
    fun test4() {
        assertTrue(
            jsonObject.safeGet("test333", "123").jsonPrimitive.content != "",
            "test333 is 123"
        )
    }
}