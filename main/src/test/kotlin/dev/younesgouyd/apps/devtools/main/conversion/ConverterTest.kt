package dev.younesgouyd.apps.devtools.main.conversion

import dev.younesgouyd.apps.devtools.main.ui.convert.Converter
import kotlin.test.Test
import kotlin.test.assertEquals

object ConverterTest {
    @Test
    fun `binary to binary`() {
        assertEquals(
            expected = "01101000-01100101-01101100-01101100-01101111-00100000-01110111-01101111-01110010-01101100-01100100",
            actual = Converter.binaryToBinary(
                binary = "01101000 01100101 01101100 011011000110111100100000 01110111 01101111 01110010 01101100 01100100",
                inputDelimiter = " ",
                outputDelimiter = "-"
            )
        )
    }

    @Test
    fun `binary to binary 2`() {
        assertEquals(
            expected = "01101000::01100101::01101100::01101100::01101111::00100000::01110111::01101111::01110010::01101100::01100100",
            actual = Converter.binaryToBinary(
                binary = "01101000-01100101-0110110001101100-01101111-00100000-01110111-01101111-01110010-01101100-01100100",
                inputDelimiter = "-",
                outputDelimiter = "::"
            )
        )
    }

    @Test
    fun `binary to decimal - positive integer`() {
        assertEquals(
            expected = "35",
            actual = Converter.binaryToDecimal(
                binary = "00000000000000000000000000100011",
                binaryNumberFormat = Converter.BinaryNumberFormat.Int32,
                inputDelimiter = "-"
            )
        )
    }

    @Test
    fun `binary to decimal 2 - negative integer`() {
        assertEquals(
            expected = "-35",
            actual = Converter.binaryToDecimal(
                binary = "1111111111011101",
                binaryNumberFormat = Converter.BinaryNumberFormat.Short,
                inputDelimiter = ""
            )
        )
    }

    @Test
    fun `binary to decimal 3 - positive float`() {
        assertEquals(
            expected = "35.203",
            actual = Converter.binaryToDecimal(
                binary = "01000010000011001100111111011111",
                binaryNumberFormat = Converter.BinaryNumberFormat.Float32,
                inputDelimiter = ""
            )
        )
    }

    @Test
    fun `binary to decimal 4 - negative float`() {
        assertEquals(
            expected = "-35.203",
            actual = Converter.binaryToDecimal(
                binary = "11000010000011001100111111011111",
                binaryNumberFormat = Converter.BinaryNumberFormat.Float32,
                inputDelimiter = ""
            )
        )
    }

    @Test
    fun `binary to hex`() {
        assertEquals(
            expected = "68 65 6C 6C 6F 20 77 6F 72 6C 64",
            actual = Converter.binaryToHex(
                binary = "01101000 01100101 01101100 01101100 01101111 00100000 01110111 01101111 01110010 01101100 01100100",
                inputDelimiter = " ",
                outputDelimiter = " ",
                toUppercase = true
            )
        )
    }

    @Test
    fun `binary to text`() {
        assertEquals(
            expected = "hello world\n" +
                    "مرحبا بالعالم",
            actual = Converter.binaryToText(
                binary = "01101000 01100101 01101100 01101100 01101111 00100000 01110111 01101111 01110010 01101100 01100100 00001010 11011001 10000101 11011000 10110001 11011000 10101101 11011000 10101000 11011000 10100111 00100000 11011000 10101000 11011000 10100111 11011001 10000100 11011000 10111001 11011000 10100111 11011001 10000100 11011001 10000101",
                charset = Charsets.UTF_8,
                inputDelimiter = " "
            )
        )
    }

    @Test
    fun `decimal to binary - negative Int32`() {
        assertEquals(
            expected = "11111111111111111111111111011101",
            actual = Converter.decimalToBinary(
                decimal = "-35",
                binaryNumberFormat = Converter.BinaryNumberFormat.Int32,
                outputDelimiter = ""
            )
        )
    }

    @Test
    fun `decimal to binary 2- positive Int32`() {
        assertEquals(
            expected = "00000000000000000000000000100011",
            actual = Converter.decimalToBinary(
                decimal = "35",
                binaryNumberFormat = Converter.BinaryNumberFormat.Int32,
                outputDelimiter = ""
            )
        )
    }

    @Test
    fun `decimal to binary 3 - Float32`() {
        assertEquals(
            expected = "11000010-00001100-11001111-11011111",
            actual = Converter.decimalToBinary(
                decimal = "-35.203",
                binaryNumberFormat = Converter.BinaryNumberFormat.Float32,
                outputDelimiter = "-"
            )
        )
    }

    @Test
    fun `decimal to binary 4 - Float64`() {
        assertEquals(
            expected = "1100000011100001001100101100011001111001100110000000011011110010",
            actual = Converter.decimalToBinary(
                decimal = "-35222.202343",
                binaryNumberFormat = Converter.BinaryNumberFormat.Float64,
                outputDelimiter = ""
            )
        )
    }

    @Test
    fun `decimal to decimal`() {
        assertEquals(
            expected = "-35222.202343",
            actual = Converter.decimalToDecimal(
                decimal = "-35222.202343"
            )
        )
    }

    @Test
    fun `decimal to hex - Float32`() {
        assertEquals(
            expected = "c2 0c cf df",
            actual = Converter.decimalToHex(
                decimal = "-35.203",
                binaryNumberFormat = Converter.BinaryNumberFormat.Float32,
                outputDelimiter = " ",
                toUppercase = false
            )
        )
    }

    @Test
    fun `decimal to hex 2 - Float64`() {
        assertEquals(
            expected = "C0_E1_32_C6_79_98_06_F2",
            actual = Converter.decimalToHex(
                decimal = "-35222.202343",
                binaryNumberFormat = Converter.BinaryNumberFormat.Float64,
                outputDelimiter = "_",
                toUppercase = true
            )
        )
    }

    @Test
    fun `decimal to text`() {
        assertEquals(
            expected = "-35.203",
            actual = Converter.decimalToText(
                decimal = "-35.203"
            )
        )
    }

    @Test
    fun `hex to binary`() {
        assertEquals(
            expected = "01101000 01100101 01101100 01101100 01101111 00100000 01110111 01101111 01110010 01101100 01100100 00001010 11011001 10000101 11011000 10110001 11011000 10101101 11011000 10101000 11011000 10100111 00100000 11011000 10101000 11011000 10100111 11011001 10000100 11011000 10111001 11011000 10100111 11011001 10000100 11011001 10000101",
            actual = Converter.hexToBinary(
                hex = "68656C6C6F20776F726C640AD985D8B1D8ADD8A8D8A720D8A8D8A7D984D8B9D8A7D984D985",
                inputDelimiter = "",
                outputDelimiter = " "
            )
        )
    }

    @Test
    fun `hex to decimal`() {
        assertEquals(
            expected = "-35.203",
            actual = Converter.hexToDecimal(
                hex = "c20ccfdf",
                binaryNumberFormat = Converter.BinaryNumberFormat.Float32,
                inputDelimiter = ""
            )
        )
    }

    @Test
    fun `hex to hex`() {
        assertEquals(
            expected = "C2:0C:CF:DF",
            actual = Converter.hexToHex(
                hex = "c20ccfdf",
                inputDelimiter = "",
                outputDelimiter = ":",
                toUppercase = true
            )
        )
    }

    @Test
    fun `hex to text`() {
        assertEquals(
            expected = "hello world",
            actual = Converter.hexToText(
                hex = "68 65 6C 6C 6F 20 77 6F 72 6C 64",
                charset = Charsets.UTF_8,
                inputDelimiter = " "
            )
        )
    }

    @Test
    fun `text to binary`() {
        assertEquals(
            expected = "01101000 01100101 01101100 01101100 01101111 00100000 01110111 01101111 01110010 01101100 01100100",
            actual = Converter.textToBinary(
                text = "hello world",
                charset = Charsets.UTF_8,
                outputDelimiter = " "
            )
        )
    }

    @Test
    fun `text to decimal`() {
        assertEquals(
            expected = "-35.203",
            actual = Converter.textToDecimal(
                text = "-35.203"
            )
        )
    }

    @Test
    fun `text to hex`() {
        assertEquals(
            expected = "68 65 6C 6C 6F 20 77 6F 72 6C 64",
            actual = Converter.textToHex(
                text = "hello world",
                charset = Charsets.UTF_8,
                toUppercase = true,
                outputDelimiter = " "
            )
        )
    }

    @Test
    fun `text to text`() {
        assertEquals(
            expected = "hello world",
            actual = Converter.textToText(
                text = "hello world"
            )
        )
    }
}