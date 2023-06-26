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

    private val jwtParser = JwtParser()
    private val jsonObject = jwtParser.parseToJsonObject(TEST_TOKEN)!!

    private fun getItem(key: String, default: Any): String {
        val item = jsonObject.safeGet(key, default).jsonPrimitive.content
        println(">>>>> key : $key >> getItem : $item")
        return item
    }

    @Test
    fun testForTestKey() {
        val item = getItem("test","123")
        assertEquals(item,"123")
        assertNotEquals(item,"1233")
    }

    @Test
    fun testForNameKey() {
        val item = getItem("name","")
        assertEquals(item,"Wonddak")
        assertNotEquals(item,"Wonddak123")
    }

    @Test
    fun testForNotExistKey() {
        val item = getItem("noneKey","notExist")
        assertEquals(item,"notExist")
        assertNotEquals(item,"")
    }
}