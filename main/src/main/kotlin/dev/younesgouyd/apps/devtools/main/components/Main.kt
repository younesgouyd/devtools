package dev.younesgouyd.apps.devtools.main.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.younesgouyd.apps.devtools.main.Component
import dev.younesgouyd.apps.devtools.main.ui.DarkThemeOptions
import dev.younesgouyd.apps.devtools.main.ui.Main
import dev.younesgouyd.apps.devtools.main.ui.NavigationDrawerItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class Main : Component() {
    override val title: String
    private val mainComponentController: MainComponentController
    private val currentMainComponent: MutableStateFlow<Component>
    private val selectedNavigationDrawerItem: MutableStateFlow<NavigationDrawerItems>
    private val components: Map<NavigationDrawerItems, Component>

    init {
        title = ""
        mainComponentController = MainComponentController()
        val format = Format()
        currentMainComponent = MutableStateFlow(format)
        selectedNavigationDrawerItem = MutableStateFlow(NavigationDrawerItems.Format)
        components = mapOf(
            NavigationDrawerItems.Convert to Convert(),
            NavigationDrawerItems.Format to format
        )
    }

    @Composable
    override fun show() {
        val currentMainComponent by currentMainComponent.collectAsState()
        val selectedNavigationDrawerItem by selectedNavigationDrawerItem.collectAsState()

        Main(
            darkTheme = DarkThemeOptions.Disabled, // todo
            currentMainComponent = currentMainComponent,
            selectedNavigationDrawerItem = selectedNavigationDrawerItem,
            onNavigationDrawerItemClick = {
                when (it) {
                    NavigationDrawerItems.Convert -> mainComponentController.showConvert()
                    NavigationDrawerItems.Format -> mainComponentController.showFormat()
                }
            }
        )
    }

    override fun clear() {
        for ((_, component) in components) {
            component.clear()
        }
    }

    private inner class MainComponentController {
        fun showConvert() {
            currentMainComponent.update { Convert() }
            selectedNavigationDrawerItem.update { NavigationDrawerItems.Convert }
        }

        fun showFormat() {
            currentMainComponent.update { Format() }
            selectedNavigationDrawerItem.update { NavigationDrawerItems.Format }
        }
    }
}
