package dev.younesgouyd.apps.devtools.main

import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*
import dev.younesgouyd.apps.devtools.main.components.Main

object Application {
    fun start() {
        application {
            Window(
                state = rememberWindowState(
                    placement = WindowPlacement.Maximized,
                    position = WindowPosition(Alignment.Center)
                ),
                onCloseRequest = ::exitApplication,
                content = { Main().show() }
            )
        }
    }
}