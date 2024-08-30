package dev.younesgouyd.apps.devtools.main.ui.conversion2

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.younesgouyd.apps.devtools.main.ui.convert.Converter
import dev.younesgouyd.apps.devtools.main.ui.convert.DropdownMenu
import dev.younesgouyd.apps.devtools.main.ui.convert.Option
import dev.younesgouyd.apps.devtools.main.ui.convert.withDelimiter
import java.nio.charset.Charset

private enum class DataType {
    Binary, Decimal, Hexadecimal, Text
}

@Composable
fun BinaryToBinary(
    modifier: Modifier = Modifier
) {
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
    val decimalOptions: List<Option<Converter.BinaryNumberFormat>> = remember {
        Converter.BinaryNumberFormat.entries.map { Option(it.name, it) }
    }
    var decimalOptions2: List<Option<Converter.BinaryNumberFormat>> by remember { mutableStateOf(decimalOptions) }
    val floatOptions: List<Option<Converter.BinaryNumberFormat>> = remember {
        listOf(
            Option(Converter.BinaryNumberFormat.Float32.name, Converter.BinaryNumberFormat.Float32),
            Option(Converter.BinaryNumberFormat.Float64.name, Converter.BinaryNumberFormat.Float64)
        )
    }
    val signedDecimalOptions: List<Option<Converter.BinaryNumberFormat>> = remember {
        listOf(
            Option(Converter.BinaryNumberFormat.Byte.name, Converter.BinaryNumberFormat.Byte),
            Option(Converter.BinaryNumberFormat.Short.name, Converter.BinaryNumberFormat.Short),
            Option(Converter.BinaryNumberFormat.Int32.name, Converter.BinaryNumberFormat.Int32),
            Option(Converter.BinaryNumberFormat.Int64.name, Converter.BinaryNumberFormat.Int64),
            Option(Converter.BinaryNumberFormat.Float32.name, Converter.BinaryNumberFormat.Float32),
            Option(Converter.BinaryNumberFormat.Float64.name, Converter.BinaryNumberFormat.Float64)
        )
    }
    val defaultDelimiterOption = Option("None", "")
    val delimiterOptions = remember {
        listOf(
            defaultDelimiterOption,
            Option("Space", " "),
            Option(",", ","),
            Option(".", "."),
            Option(";", ";"),
            Option(":", ","),
            Option("|", "|")
        )
    }

    var val1 by remember { mutableStateOf("") }
    var val2 by remember { mutableStateOf("") }

    var delimiter1 by remember { mutableStateOf("") }
    var delimiter2 by remember { mutableStateOf("") }

    fun convert() {
        val2 = Converter.binaryToBinary(binary = val1, delimiter1, delimiter2)
    }

    Conversion(
        modifier = modifier,
        parameters1 = {
            DropdownMenu<String>(
                label = "Delimiter",
                options = delimiterOptions,
                selectedOption = defaultDelimiterOption,
                onValueChange = { delimiter1 = it; convert() },
            )
        },
        parameters2 = {
            DropdownMenu<String>(
                label = "Delimiter",
                options = delimiterOptions,
                selectedOption = defaultDelimiterOption,
                onValueChange = {
                    val oldDelimiter = delimiter2
                    delimiter2 = it
                    val2 = val2.withDelimiter(partLength = 8, oldDelimiter = oldDelimiter, newDelimiter = delimiter2)
                },
            )
        },
        value1 = val1,
        value2 = val2,
        onValue1Change = { val1 = it; convert() },
    )
}

@Composable
private fun Conversion(
    modifier: Modifier = Modifier,
    parameters1: @Composable RowScope.() -> Unit = {},
    parameters2: @Composable RowScope.() -> Unit = {},
    value1: String,
    value2: String,
    onValue1Change: (String) -> Unit
) {
    var error1 by remember { mutableStateOf(false) }
    var errorMessage1 by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
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
            Row(
                modifier = Modifier.weight(.1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = parameters1
            )
            Row(
                modifier = Modifier.weight(.1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = parameters2
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(.1f),
                label = { Text("Value") },
                value = value1,
                onValueChange = {
                    error1 = false
                    errorMessage1 = ""
                    try {
                        onValue1Change(it)
                    } catch (e: Exception) {
                        error1 = true
                        errorMessage1 = e.message ?: ""
                    }
                },
                minLines = 20,
                isError = error1,
                supportingText = { Text(errorMessage1) }
            )
            OutlinedTextField(
                modifier = Modifier.weight(.1f),
                label = { Text("Value") },
                value = value2,
                onValueChange = {},
                minLines = 20
            )
        }
    }
}
