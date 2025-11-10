package com.coursework.lms

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.coursework.corePresentation.navigation.LocalNavControllersHolder
import com.coursework.lms.theme.LMSTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    LMSTheme {
        CompositionLocalProvider(
            LocalNavControllersHolder provides koinInject()
        ) {
            RootNavigation()
        }
    }
}