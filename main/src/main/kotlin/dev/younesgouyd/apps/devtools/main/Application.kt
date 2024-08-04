package dev.younesgouyd.apps.devtools.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.*

object Application {
    fun start() {
        application {
            Window(
                state = rememberWindowState(
                    placement = WindowPlacement.Maximized,
                    position = WindowPosition(Alignment.Center)
                ),
                onCloseRequest = ::exitApplication,
                content = {
                    MaterialTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Hello DevTools")
                            }
                        }
                    }
                }
            )
        }
    }
}