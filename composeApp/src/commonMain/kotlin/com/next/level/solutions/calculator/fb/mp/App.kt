package com.next.level.solutions.calculator.fb.mp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.next.level.solutions.calculator.fb.mp.di.decomposeModule
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootContent
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppTheme
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
import org.koin.core.KoinApplication
import org.koin.ksp.generated.defaultModule

@Composable
@Preview
fun App(
  rootComponentFactory: (KoinFactory) -> RootComponent,
) {
  KoinApplication(
    application = { appModules() },
    content = {
      Content(
        rootComponentFactory = rootComponentFactory,
      )
    },
  )
}

@Composable
private fun Content(
  rootComponentFactory: (KoinFactory) -> RootComponent,
) {
  val koin = getKoin()

  val rootComponent = remember {
    rootComponentFactory(KoinFactory(koin::get))
  }

  AppTheme(
    darkTheme = true,
  ) {
    RootContent(
      component = rootComponent,
      modifier = Modifier.fillMaxSize(),
    )
  }
}

private fun KoinApplication.appModules() {
  this.modules(
    modules = listOf(
      defaultModule,
      decomposeModule,
    )
  )
}