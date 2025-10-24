package org.yusufteker.konekt.util.security

import io.github.aakira.napier.Napier
import kotlin.js.ExperimentalWasmJsInterop

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(input) => btoa(input)")
private external fun jsBtoa(input: String): String

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("(input) => atob(input)")
private external fun jsAtob(input: String): String

actual class EncryptionHelperImpl actual constructor() {

    private val secretKey = "KonektSecretKey1" // 16-byte

    actual fun encrypt(value: String): String {
        try {
            val data = encodeStringToBytes(value)
            val keyBytes = encodeStringToBytes(secretKey)
            val encrypted = mutableListOf<Byte>()

            var keyIndex = 0
            for (byte in data) {
                val encryptedByte = (byte.toInt() xor keyBytes[keyIndex % keyBytes.count()].toInt()).toByte()
                encrypted.add(encryptedByte)
                keyIndex++
            }

            return bytesToBase64(encrypted)
        } catch (e: Exception) {
            Napier.e("Encryption error: ${e.message}")
            return ""
        }
    }

    actual fun decrypt(value: String): String {
        try {
            val data = base64ToBytes(value)
            val keyBytes = encodeStringToBytes(secretKey)
            val decrypted = mutableListOf<Byte>()

            var keyIndex = 0
            for (byte in data) {
                val decryptedByte = (byte.toInt() xor keyBytes[keyIndex % keyBytes.count()].toInt()).toByte()
                decrypted.add(decryptedByte)
                keyIndex++
            }

            return decodeBytesToString(decrypted)
        } catch (e: Exception) {
            Napier.e("Decryption error: ${e.message}")
            return ""
        }
    }

    private fun encodeStringToBytes(str: String): List<Byte> {
        val result = mutableListOf<Byte>()
        for (char in str) {
            result.add(char.code.toByte())
        }
        return result
    }

    private fun decodeBytesToString(bytes: List<Byte>): String {
        var result = ""
        for (byte in bytes) {
            result += byte.toInt().toChar()
        }
        return result
    }

    private fun bytesToBase64(bytes: List<Byte>): String {
        var binaryString = ""
        for (byte in bytes) {
            binaryString += byte.toInt().toChar()
        }
        return jsBtoa(binaryString)
    }

    private fun base64ToBytes(base64: String): List<Byte> {
        val decoded = jsAtob(base64)
        val result = mutableListOf<Byte>()
        for (i in 0 until decoded.length) {
            result.add(decoded[i].code.toByte())
        }
        return result
    }
}