@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.LanguageComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class LanguageConfiguration(
  val changeMode: Boolean,
) : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return LanguageComponent.Handler(changeMode)
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::LanguageComponent, context)
  }
}

fun RootComponent.Configuration.Companion.language(changeMode: Boolean): LanguageConfiguration {
  return LanguageConfiguration(changeMode)
}