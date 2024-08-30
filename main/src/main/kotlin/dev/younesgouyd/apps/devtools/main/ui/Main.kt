package dev.younesgouyd.apps.devtools.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.younesgouyd.apps.devtools.main.Component

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(
    darkTheme: DarkThemeOptions,
    currentMainComponent: Component,
    selectedNavigationDrawerItem: NavigationDrawerItems,
    onNavigationDrawerItemClick: (NavigationDrawerItems) -> Unit
) {
    Theme(
        darkTheme = darkTheme,
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                PermanentNavigationDrawer(
                    modifier = Modifier.fillMaxWidth(),
                    drawerContent = {
                        PermanentDrawerSheet {
                            NavigationDrawerItems.entries.forEach {
                                NavigationDrawerItem(
                                    label = { Text(it.toString()) },
                                    selected = it == selectedNavigationDrawerItem,
                                    onClick = { onNavigationDrawerItemClick(it) }
                                )
                            }
                        }
                    },
                    content = {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    navigationIcon = {},
                                    title = { Text(text = currentMainComponent.title, style = MaterialTheme.typography.headlineMedium) }
                                )
                            },
                            content = { paddingValues ->
                                Box(modifier = Modifier.padding(paddingValues)) {
                                    currentMainComponent.show()
                                }
                            }
                        )
                    }
                )
            }
        }
    )
}