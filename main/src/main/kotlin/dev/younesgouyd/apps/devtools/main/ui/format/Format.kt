package dev.younesgouyd.apps.devtools.main.ui.format

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.younesgouyd.apps.devtools.main.ui.convert.DropdownMenu
import dev.younesgouyd.apps.devtools.main.ui.convert.Option
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.jdom2.Document
import org.jdom2.input.SAXBuilder
import org.jdom2.output.Format
import org.jdom2.output.XMLOutputter
import java.io.StringReader

@Composable
fun Format() {
    var value by remember { mutableStateOf("") }
    var fileFormat by remember { mutableStateOf(FileFormats.Json) }
    var errorMessage: String? by remember { mutableStateOf(null) }
    val selectedFileFormatOption = remember(fileFormat) { Option(fileFormat.name, fileFormat) }

    fun format() {
        errorMessage = null
        try {
            value = when (fileFormat) {
                FileFormats.Json -> {
                    val json = Json { prettyPrint = true }
                    val jsonElement = json.parseToJsonElement(value).jsonObject
                    json.encodeToString(JsonObject.serializer(), jsonElement)
                }
                FileFormats.Xml -> {
                    val saxBuilder = SAXBuilder()
                    val document: Document = saxBuilder.build(StringReader(value))

                    val xmlOutputter = XMLOutputter()
                    xmlOutputter.format = Format.getPrettyFormat()

                    xmlOutputter.outputString(document)
                }
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DropdownMenu<FileFormats>(
            modifier = Modifier.fillMaxWidth(),
            label = "Data Format",
            options = FileFormats.entries.map { Option(it.name, it) },
            selectedOption = selectedFileFormatOption,
            onValueChange = { fileFormat = it }
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            content = { Text("Format") },
            onClick = ::format
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Value") },
            value = value,
            onValueChange = { value = it; },
            minLines = 20,
            isError = errorMessage != null,
            supportingText = { errorMessage?.let { Text(it) } }
        )
    }
}

private enum class FileFormats { Json, Xml }