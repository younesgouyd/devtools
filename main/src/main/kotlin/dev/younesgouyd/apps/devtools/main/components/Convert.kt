package dev.younesgouyd.apps.devtools.main.components

import androidx.compose.runtime.Composable
import dev.younesgouyd.apps.devtools.main.Component
import dev.younesgouyd.apps.devtools.main.ui.convert.Convert

class Convert : Component() {
    override val title: String = "Convert"

    @Composable
    override fun show() {
        Convert()
    }

    override fun clear() {}
}