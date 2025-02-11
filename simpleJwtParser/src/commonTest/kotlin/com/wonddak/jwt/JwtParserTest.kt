package com.wonddak.jwt

import kotlinx.serialization.json.jsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


class JwtParserTest {
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

    private val payload = JwtParser.parseToJsonObject(TEST_TOKEN)!!

    @Test
    fun testForIat() {
        val item = payload.iat
        assertEquals(item,1516239022)
        assertNotEquals(item,0)
    }

    @Test
    fun testForSub() {
        val item = payload.sub
        assertEquals(item,"1234567890")
        assertNotEquals(item,"")
    }

    @Test
    fun testForTestKey() {
        val item = payload.get("test","123").jsonPrimitive.content
        assertEquals(item,"123")
        assertNotEquals(item,"1233")
    }

    @Test
    fun testForNameKey() {
        val item = payload.get("name","").jsonPrimitive.content
        assertEquals(item,"Wonddak")
        assertNotEquals(item,"Wonddak123")
    }

    @Test
    fun testForNotExistKey() {
        val item = payload.get("noneKey","notExist").jsonPrimitive.content
        assertEquals(item,"notExist")
        assertNotEquals(item,"")
    }
}