package android.util

import java.util.Base64

public object Base64 {
    @JvmStatic
    public fun decode(byteArray: ByteArray, flags: Int): ByteArray {
        return Base64.getDecoder().decode(byteArray)
    }
}