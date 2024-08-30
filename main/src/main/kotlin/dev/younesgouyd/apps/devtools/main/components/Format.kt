package dev.younesgouyd.apps.devtools.main.components

import androidx.compose.runtime.Composable
import dev.younesgouyd.apps.devtools.main.Component
import dev.younesgouyd.apps.devtools.main.ui.format.Format

class Format : Component() {
    override val title: String = "Format"

    @Composable
    override fun show() {
        Format()
    }

    override fun clear() {
    }
}