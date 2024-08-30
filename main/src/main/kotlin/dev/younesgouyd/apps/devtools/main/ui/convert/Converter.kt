package dev.younesgouyd.apps.devtools.main.ui.convert

import dev.younesgouyd.apps.devtools.main.ui.convert.Converter.Util.binaryStringToByteArray
import dev.younesgouyd.apps.devtools.main.ui.convert.Converter.Util.toBinaryString
import java.nio.charset.Charset

object Converter {
    fun binaryToBinary(
        binary: String,
        inputDelimiter: String,
        outputDelimiter: String
    ): String {
        return binary.withDelimiter(partLength = 8, oldDelimiter = inputDelimiter, newDelimiter = outputDelimiter)
    }

    fun binaryToDecimal(
        binary: String,
        binaryNumberFormat: BinaryNumberFormat,
        inputDelimiter: String
    ): String {
        val binaryWithoutDelimiter = binary.withDelimiter(partLength = 8, oldDelimiter = inputDelimiter, newDelimiter = "")
        return when (binaryNumberFormat) {
            BinaryNumberFormat.Byte -> {
                require(binaryWithoutDelimiter.length <= 8) { "Binary value must be 8 bits long or less" }
                binaryWithoutDelimiter.toUByte(2).toByte().toString(10)
            }
            BinaryNumberFormat.UByte -> {
                require(binaryWithoutDelimiter.length <= 8) { "Binary value must be 8 bits long or less" }
                binaryWithoutDelimiter.toUByte(2).toString(10)
            }
            BinaryNumberFormat.Short -> {
                require(binaryWithoutDelimiter.length <= 16) { "Binary value must be 16 bits long or less" }
                binaryWithoutDelimiter.toUShort(2).toShort().toString(10)
            }
            BinaryNumberFormat.UShort -> {
                require(binaryWithoutDelimiter.length <= 16) { "Binary value must be 16 bits long or less" }
                binaryWithoutDelimiter.toUShort(2).toString(10)
            }
            BinaryNumberFormat.Int32 -> {
                require(binaryWithoutDelimiter.length <= 32) { "Binary value must be 32 bits long or less" }
                binaryWithoutDelimiter.toUInt(2).toInt().toString(10)
            }
            BinaryNumberFormat.UInt32 -> {
                require(binaryWithoutDelimiter.length <= 32) { "Binary value must be 32 bits long or less" }
                binaryWithoutDelimiter.toUInt(2).toString(10)
            }
            BinaryNumberFormat.Int64 -> {
                require(binaryWithoutDelimiter.length <= 64) { "Binary value must be 64 bits long or less" }
                binaryWithoutDelimiter.toULong(2).toLong().toString(10)
            }
            BinaryNumberFormat.UInt64 -> {
                require(binaryWithoutDelimiter.length <= 64) { "Binary value must be 64 bits long or less" }
                binaryWithoutDelimiter.toULong(2).toString(10)
            }
            BinaryNumberFormat.Float32 -> {
                require(binaryWithoutDelimiter.length == 32) { "Binary value must be 32 bits long" }
                Float.fromBits(binaryWithoutDelimiter.toUInt(2).toInt()).toString()
            }
            BinaryNumberFormat.Float64 -> {
                require(binaryWithoutDelimiter.length == 64) { "Binary value must be 64 bits long" }
                Double.fromBits(binaryWithoutDelimiter.toULong(2).toLong()).toString()
            }
        }
    }

    fun binaryToHex(
        binary: String,
        inputDelimiter: String,
        outputDelimiter: String,
        toUppercase: Boolean
    ): String {
        val format = if (toUppercase) "%02X" else "%02x"
        return binary
            .withDelimiter(8, inputDelimiter, "")
            .binaryStringToByteArray()
            .joinToString(outputDelimiter) { format.format(it) }
    }

    fun binaryToText(
        binary: String,
        charset: Charset,
        inputDelimiter: String
    ): String {
        return binary
            .withDelimiter(8, inputDelimiter, "")
            .binaryStringToByteArray()
            .toString(charset)
    }

    fun decimalToBinary(
        decimal: String,
        binaryNumberFormat: BinaryNumberFormat,
        outputDelimiter: String
    ): String {
        return when (binaryNumberFormat) {
            BinaryNumberFormat.Byte -> decimal.toByte().toUByte().toString(2).padStart(8, '0')
            BinaryNumberFormat.UByte -> decimal.toUByte().toString(2).padStart(8, '0')
            BinaryNumberFormat.Short -> decimal.toShort().toUShort().toString(2).padStart(16, '0')
            BinaryNumberFormat.UShort -> decimal.toUShort().toString(2).padStart(16, '0')
            BinaryNumberFormat.Int32 -> decimal.toInt().toUInt().toString(2).padStart(32, '0')
            BinaryNumberFormat.UInt32 -> decimal.toUInt().toString(2).padStart(32, '0')
            BinaryNumberFormat.Int64 -> decimal.toLong().toULong().toString(2).padStart(64, '0')
            BinaryNumberFormat.UInt64 -> decimal.toULong().toString(2).padStart(64, '0')
            BinaryNumberFormat.Float32 -> decimal.toFloat().toRawBits().toUInt().toString(2).padStart(32, '0')
            BinaryNumberFormat.Float64 -> decimal.toDouble().toRawBits().toULong().toString(2).padStart(64, '0')
        }.withDelimiter(partLength = 8, oldDelimiter = "", newDelimiter = outputDelimiter)
    }

    fun decimalToDecimal(decimal: String): String {
        return decimal
    }

    fun decimalToHex(
        decimal: String,
        binaryNumberFormat: BinaryNumberFormat,
        outputDelimiter: String,
        toUppercase: Boolean
    ): String {
        return binaryToHex(
            binary = decimalToBinary(decimal = decimal, binaryNumberFormat = binaryNumberFormat, outputDelimiter = ""),
            inputDelimiter = "",
            outputDelimiter = outputDelimiter,
            toUppercase = toUppercase
        )
    }

    fun decimalToText(decimal: String): String {
        return decimal
    }

    fun hexToBinary(
        hex: String,
        inputDelimiter: String,
        outputDelimiter: String
    ): String {
        return hex
            .withDelimiter(2, inputDelimiter, "")
            .chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
            .toBinaryString(outputDelimiter)
    }

    fun hexToDecimal(
        hex: String,
        binaryNumberFormat: BinaryNumberFormat,
        inputDelimiter: String
    ): String {
        return binaryToDecimal(
            binary = hexToBinary(hex = hex, inputDelimiter = inputDelimiter, outputDelimiter = ""),
            binaryNumberFormat = binaryNumberFormat,
            inputDelimiter = ""
        )
    }

    fun hexToHex(
        hex: String,
        inputDelimiter: String,
        outputDelimiter: String,
        toUppercase: Boolean
    ): String {
        return hex.withDelimiter(partLength = 2, oldDelimiter = inputDelimiter, newDelimiter = outputDelimiter)
            .let { if (toUppercase) it.uppercase() else it }
    }

    fun hexToText(
        hex: String,
        charset: Charset,
        inputDelimiter: String = ""
    ): String {
        return hex
            .withDelimiter(partLength = 2, oldDelimiter = inputDelimiter, newDelimiter = "")
            .chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
            .toString(charset)
    }

    fun textToBinary(
        text: String,
        charset: Charset,
        outputDelimiter: String
    ): String {
        return text
            .toByteArray(charset)
            .toBinaryString(outputDelimiter)
    }

    fun textToDecimal(text: String): String {
        return text
    }

    fun textToHex(
        text: String,
        charset: Charset,
        toUppercase: Boolean,
        outputDelimiter: String
    ): String {
        return text
            .toByteArray(charset)
            .joinToString(outputDelimiter) { "%02${if (toUppercase) "X" else "x"}".format(it) }
    }

    fun textToText(text: String): String {
        return text
    }

    enum class BinaryNumberFormat { Byte, UByte, Short, UShort, Int32, UInt32, Int64, UInt64, Float32, Float64 }

    private object Util {
        fun ByteArray.toBinaryString(delimiter: String): String {
            return this.joinToString(delimiter) { byte ->
                String.format("%8s", Integer.toBinaryString(byte.toInt() and 0xFF))
                    .replace(' ', '0')
            }
        }

        fun String.binaryStringToByteArray(): ByteArray {
            return this
                .chunked(8)
                .map { it.toInt(2).toByte() }
                .toByteArray()
        }
    }
}
