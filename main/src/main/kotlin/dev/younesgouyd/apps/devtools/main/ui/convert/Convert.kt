package dev.younesgouyd.apps.devtools.main.ui.convert

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.nio.charset.Charset

@Composable
fun Convert() {
    val dataTypeOptions: List<Option<DataType>> = remember { DataType.entries.map { Option(it.name, it) } }
    val charsetOptions: List<Option<Charset>> = remember {
        listOf(
            Option(Charsets.UTF_8.displayName(), Charsets.UTF_8),
            Option(Charsets.UTF_16.displayName(), Charsets.UTF_16),
            Option(Charsets.UTF_16BE.displayName(), Charsets.UTF_16BE),
            Option(Charsets.UTF_16LE.displayName(), Charsets.UTF_16LE),
            Option(Charsets.US_ASCII.displayName(), Charsets.US_ASCII),
            Option(Charsets.ISO_8859_1.displayName(), Charsets.ISO_8859_1),
            Option(Charsets.UTF_32.displayName(), Charsets.UTF_32),
            Option(Charsets.UTF_32LE.displayName(), Charsets.UTF_32LE),
            Option(Charsets.UTF_32BE.displayName(), Charsets.UTF_32BE)
        )
    }
    val binaryNumberFormatOptions: List<Option<Converter.BinaryNumberFormat>> = remember {
        Converter.BinaryNumberFormat.entries.map { Option(it.name, it) }
    }
    val delimiterOptions = remember {
        listOf(
            Option("None", ""),
            Option("Space", " "),
            Option("-", "-"),
            Option(",", ","),
            Option(".", "."),
            Option(";", ";"),
            Option(":", ":"),
            Option("|", "|")
        )
    }
    val toUppercaseOptions = remember {
        listOf(
            Option("True", true),
            Option("False", false)
        )
    }
    val unicodeControlsPictures = remember {
        mapOf(
            '\u0000' to '␀',
            '\u0001' to '␁',
            '\u0002' to '␂',
            '\u0003' to '␃',
            '\u0004' to '␄',
            '\u0005' to '␅',
            '\u0006' to '␆',
            '\u0007' to '␇',
            '\u0008' to '␈',
            '\u0009' to '␉',
            '\u000A' to '␊',
            '\u000B' to '␋',
            '\u000C' to '␌',
            '\u000D' to '␍',
            '\u000E' to '␎',
            '\u000F' to '␏',
            '\u0010' to '␐',
            '\u0011' to '␑',
            '\u0012' to '␒',
            '\u0013' to '␓',
            '\u0014' to '␔',
            '\u0015' to '␕',
            '\u0016' to '␖',
            '\u0017' to '␗',
            '\u0018' to '␘',
            '\u0019' to '␙',
            '\u001A' to '␚',
            '\u001B' to '␚',
            '\u001C' to '␜',
            '\u001D' to '␝',
            '\u001E' to '␞',
            '\u001F' to '␟',
        )
    }

    var type1 by remember { mutableStateOf(DataType.Text) }
    var type2 by remember { mutableStateOf(DataType.Binary) }
    var val1 by remember { mutableStateOf("") }
    var val2 by remember { mutableStateOf("") }
    var charset1 by remember { mutableStateOf(Charsets.UTF_8) }
    var charset2 by remember { mutableStateOf(Charsets.UTF_8) }
    var delimiter1 by remember { mutableStateOf("") }
    var delimiter2 by remember { mutableStateOf("") }
    var binaryNumberFormat1 by remember { mutableStateOf(Converter.BinaryNumberFormat.Int32) }
    var binaryNumberFormat2 by remember { mutableStateOf(Converter.BinaryNumberFormat.Int32) }
    var toUppercase2 by remember { mutableStateOf(true) }
    var convertToControlPictures by remember { mutableStateOf(true) }
    var error1 by remember { mutableStateOf(false) }
    var errorMessage1 by remember { mutableStateOf("") }

    val selectedType1Option = remember(type1) { Option(type1.name, type1) }
    val selectedType2Option = remember(type2) { Option(type2.name, type2) }
    val selectedCharset1Option = remember(charset1) { Option(charset1.displayName(), charset1) }
    val selectedCharset2Option = remember(charset2) { Option(charset2.displayName(), charset2) }
    val selectedDelimiter1Option = remember(delimiter1) {
        val label = if (delimiter1 == "") "None" else if (delimiter1 == " ") "Space" else delimiter1
        Option(label, delimiter1)
    }
    val selectedDelimiter2Option = remember(delimiter2) {
        val label = if (delimiter2 == "") "None" else if (delimiter2 == " ") "Space" else delimiter2
        Option(label, delimiter2)
    }
    val selectedBinaryNumberFormatOption1 = remember(binaryNumberFormat1) { Option(binaryNumberFormat1.name, binaryNumberFormat1) }
    val selectedBinaryNumberFormatOption2 = remember(binaryNumberFormat2) { Option(binaryNumberFormat2.name, binaryNumberFormat2) }
    val selectedToUppercase2Option = remember(toUppercase2) { Option(toUppercase2.toString(), toUppercase2) }

    fun String.convertControlCharactersToControlPictures(): String {
        return this.map {
            unicodeControlsPictures[it] ?: it
        }.joinToString("")
    }

    fun convert() {
        error1 = false
        errorMessage1 = ""
        if (val1.isEmpty()) return
        try {
            val2 = when (type1) {
                DataType.Binary -> when (type2) {
                    DataType.Binary -> Converter.binaryToBinary(binary = val1, inputDelimiter = delimiter1, outputDelimiter = delimiter2)
                    DataType.Decimal -> Converter.binaryToDecimal(binary = val1, binaryNumberFormat = binaryNumberFormat1, inputDelimiter = delimiter1)
                    DataType.Hexadecimal -> Converter.binaryToHex(binary = val1, inputDelimiter = delimiter1, outputDelimiter = delimiter2, toUppercase = toUppercase2)
                    DataType.Text -> Converter.binaryToText(binary = val1, inputDelimiter = delimiter1, charset = charset1).let {
                        if (convertToControlPictures) it.convertControlCharactersToControlPictures()
                        else it
                    }
                }
                DataType.Decimal -> when (type2) {
                    DataType.Binary -> Converter.decimalToBinary(decimal = val1, binaryNumberFormat = binaryNumberFormat2, outputDelimiter = delimiter2)
                    DataType.Decimal -> Converter.decimalToDecimal(val1)
                    DataType.Hexadecimal -> Converter.decimalToHex(decimal = val1, binaryNumberFormat = binaryNumberFormat2, outputDelimiter = delimiter2, toUppercase = toUppercase2)
                    DataType.Text -> Converter.decimalToText(decimal = val1)
                }
                DataType.Hexadecimal -> when (type2) {
                    DataType.Binary -> Converter.hexToBinary(hex = val1, inputDelimiter = delimiter1, outputDelimiter = delimiter2)
                    DataType.Decimal -> Converter.hexToDecimal(hex = val1, binaryNumberFormat = binaryNumberFormat1, inputDelimiter = delimiter1)
                    DataType.Hexadecimal -> Converter.hexToHex(hex = val1, inputDelimiter = delimiter1, outputDelimiter = delimiter2, toUppercase = toUppercase2)
                    DataType.Text -> Converter.hexToText(hex = val1, charset = charset1, inputDelimiter = delimiter1).let {
                        if (convertToControlPictures) it.convertControlCharactersToControlPictures()
                        else it
                    }
                }
                DataType.Text -> when (type2) {
                    DataType.Binary -> Converter.textToBinary(text = val1, charset = charset2, outputDelimiter = delimiter2)
                    DataType.Decimal -> Converter.textToDecimal(text = val1)
                    DataType.Hexadecimal -> Converter.textToHex(text = val1, charset = charset2, toUppercase = toUppercase2, outputDelimiter = delimiter2)
                    DataType.Text -> Converter.textToText(text = val1)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error1 = true
            errorMessage1 = e.message ?: ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropdownMenu(
                modifier = Modifier.weight(.1f),
                label = "Data Type",
                options = dataTypeOptions,
                selectedOption = selectedType1Option,
                onValueChange = { type1 = it; },
            )
            IconButton(
                onClick = ::convert,
                content = { Icon(Icons.AutoMirrored.Default.ArrowRightAlt, null) }
            )
            DropdownMenu<DataType>(
                modifier = Modifier.weight(.1f),
                label = "Data Type",
                options = dataTypeOptions,
                selectedOption = selectedType2Option,
                onValueChange = { type2 = it; },
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(.1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if ((type1 == DataType.Binary || type1 == DataType.Hexadecimal) && type2 == DataType.Decimal) {
                    DropdownMenu<Converter.BinaryNumberFormat>(
                        label = "Format",
                        options = binaryNumberFormatOptions,
                        selectedOption = selectedBinaryNumberFormatOption1,
                        onValueChange = { binaryNumberFormat1 = it; convert() },
                    )
                }
                if ((type1 == DataType.Binary || type1 == DataType.Hexadecimal) && type2 == DataType.Text) {
                    DropdownMenu<Charset>(
                        label = "Character Encoding",
                        options = charsetOptions,
                        selectedOption = selectedCharset1Option,
                        onValueChange = { charset1 = it; convert() },
                    )
                }
                if (type1 == DataType.Binary || type1 == DataType.Hexadecimal) {
                    DropdownMenu<String>(
                        label = "Delimiter",
                        options = delimiterOptions,
                        selectedOption = selectedDelimiter1Option,
                        onValueChange = { delimiter1 = it; convert() },
                    )
                }
            }
            Row(
                modifier = Modifier.weight(.1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if ((type2 == DataType.Binary || type2 == DataType.Hexadecimal) && type1 == DataType.Decimal) {
                    DropdownMenu<Converter.BinaryNumberFormat>(
                        label = "Format",
                        options = binaryNumberFormatOptions,
                        selectedOption = selectedBinaryNumberFormatOption2,
                        onValueChange = { binaryNumberFormat2 = it; convert() },
                    )
                }
                if ((type2 == DataType.Binary || type2 == DataType.Hexadecimal) && type1 == DataType.Text) {
                    DropdownMenu<Charset>(
                        label = "Character Encoding",
                        options = charsetOptions,
                        selectedOption = selectedCharset2Option,
                        onValueChange = { charset2 = it; convert() },
                    )
                }
                if (type2 == DataType.Binary || type2 == DataType.Hexadecimal) {
                    DropdownMenu<String>(
                        label = "Delimiter",
                        options = delimiterOptions,
                        selectedOption = selectedDelimiter2Option,
                        onValueChange = { delimiter2 = it; convert() },
                    )
                }
                if (type2 == DataType.Hexadecimal) {
                    DropdownMenu<Boolean>(
                        label = "To Uppercase",
                        options = toUppercaseOptions,
                        selectedOption = selectedToUppercase2Option,
                        onValueChange = { toUppercase2 = it; convert() },
                    )
                }
                if ((type1 == DataType.Binary || type1 == DataType.Hexadecimal) && type2 == DataType.Text) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = convertToControlPictures,
                            onCheckedChange = { convertToControlPictures = !convertToControlPictures; convert() }
                        )
                        Text(
                            text = "Control Characters To Control Pictures",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(.1f),
                label = { Text("Value") },
                value = val1,
                onValueChange = { val1 = it; convert() },
                minLines = 20,
                isError = error1,
                supportingText = { Text(errorMessage1) }
            )
            OutlinedTextField(
                modifier = Modifier.weight(.1f),
                label = { Text("Value") },
                value = val2,
                readOnly = true,
                onValueChange = {},
                minLines = 20,
                isError = false,
                supportingText = {}
            )
        }
    }
}

private enum class DataType {
    Binary, Decimal, Hexadecimal, Text
}