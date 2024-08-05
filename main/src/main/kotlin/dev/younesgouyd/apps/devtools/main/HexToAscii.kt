package dev.younesgouyd.apps.devtools.main

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HexToAscii() {
    var hex by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var encoding by remember { mutableStateOf(Charsets.UTF_8) }

    fun toText() {
        text = hex.chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
            .toString(encoding)
    }

    fun toHex() {
        hex = text
            .toByteArray(encoding)
            .joinToString("") { "%02x".format(it) }
            .uppercase()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Hexadecimal to ASCII",
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Hex") },
            value = hex,
            onValueChange = { hex = it; toText() }
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Encoding: ",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "UTF-8",
                        style = MaterialTheme.typography.labelMedium
                    )
                    RadioButton(
                        selected = encoding == Charsets.UTF_8,
                        onClick = { encoding = Charsets.UTF_8; toText() }
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "ASCII",
                        style = MaterialTheme.typography.labelMedium
                    )
                    RadioButton(
                        selected = encoding == Charsets.US_ASCII,
                        onClick = { encoding = Charsets.US_ASCII; toText() }
                    )
                }
            }
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Text") },
            value = text,
            onValueChange = { text = it; toHex() }
        )
    }
}
