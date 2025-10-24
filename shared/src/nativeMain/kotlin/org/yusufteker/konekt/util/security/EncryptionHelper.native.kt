package org.yusufteker.konekt.util.security

import kotlinx.cinterop.*
import platform.CoreCrypto.*
import platform.Foundation.*

actual class EncryptionHelperImpl actual constructor() {

    private val secretKey = "KonektSecretKey1" // 16-byte

    @OptIn(ExperimentalForeignApi::class)
    actual fun encrypt(value: String): String {
        val data = value.encodeToByteArray()
        val keyData = secretKey.encodeToByteArray()

        val dataLength = data.size
        val bufferSize = dataLength + kCCBlockSizeAES128.toInt()
        val buffer = ByteArray(bufferSize)

        memScoped {
            val numBytesEncrypted = alloc<ULongVar>()

            data.usePinned { pinnedData ->
                keyData.usePinned { pinnedKey ->
                    buffer.usePinned { pinnedBuffer ->
                        val status = CCCrypt(
                            kCCEncrypt.toUInt(),
                            kCCAlgorithmAES.toUInt(),
                            (kCCOptionECBMode.toUInt() or kCCOptionPKCS7Padding.toUInt()),
                            pinnedKey.addressOf(0),
                            keyData.size.toULong(),
                            null,
                            pinnedData.addressOf(0),
                            dataLength.toULong(),
                            pinnedBuffer.addressOf(0),
                            bufferSize.toULong(),
                            numBytesEncrypted.ptr
                        )

                        if (status == kCCSuccess) {
                            val encryptedData = buffer.copyOf(numBytesEncrypted.value.toInt())
                            return encryptedData.toNSData().base64EncodedStringWithOptions(0u)
                        }
                    }
                }
            }
        }

        return ""
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    actual fun decrypt(value: String): String {
        val data = NSData.create(base64EncodedString = value, options = 0u) ?: return ""
        val keyData = secretKey.encodeToByteArray()

        val dataLength = data.length.toInt()
        val bufferSize = dataLength + kCCBlockSizeAES128.toInt()
        val buffer = ByteArray(bufferSize)

        memScoped {
            val numBytesDecrypted = alloc<ULongVar>()

            keyData.usePinned { pinnedKey ->
                buffer.usePinned { pinnedBuffer ->
                    val status = CCCrypt(
                        kCCDecrypt.toUInt(),
                        kCCAlgorithmAES.toUInt(),
                        (kCCOptionECBMode.toUInt() or kCCOptionPKCS7Padding.toUInt()),
                        pinnedKey.addressOf(0),
                        keyData.size.toULong(),
                        null,
                        data.bytes,
                        dataLength.toULong(),
                        pinnedBuffer.addressOf(0),
                        bufferSize.toULong(),
                        numBytesDecrypted.ptr
                    )

                    if (status == kCCSuccess) {
                        val decryptedData = buffer.copyOf(numBytesDecrypted.value.toInt())
                        return decryptedData.decodeToString()
                    }
                }
            }
        }

        return ""
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun ByteArray.toNSData(): NSData {
    return this.usePinned { pinned ->
        NSData.create(bytes = pinned.addressOf(0), length = this.size.toULong())
    }
}