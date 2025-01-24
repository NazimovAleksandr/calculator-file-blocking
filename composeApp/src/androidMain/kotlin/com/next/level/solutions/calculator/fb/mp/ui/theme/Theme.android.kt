package com.next.level.solutions.calculator.fb.mp.ui.theme

import androidx.activity.compose.LocalActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat

@Composable
actual fun UpdateTheme(
  darkTheme: Boolean,
) {
  val activity = LocalActivity.current

  when (darkTheme) {
    true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
  }

  DisposableEffect(key1 = darkTheme) {
    activity?.window?.apply {
      val color = when (darkTheme) {
        true -> Color.Black.copy(alpha = 0.01f)
        else -> Color.White.copy(alpha = 0.01f)
      }.toArgb()

      @Suppress("DEPRECATION")
      statusBarColor = color
      @Suppress("DEPRECATION")
      navigationBarColor = color

      WindowCompat.getInsetsController(this, decorView).apply {
        isAppearanceLightStatusBars = !darkTheme
        isAppearanceLightNavigationBars = !darkTheme
      }
    }

    onDispose { }
  }
}