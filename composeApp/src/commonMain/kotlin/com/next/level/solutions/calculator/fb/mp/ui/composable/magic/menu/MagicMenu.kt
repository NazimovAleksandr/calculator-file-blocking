package com.next.level.solutions.calculator.fb.mp.ui.composable.magic.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.next
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.composable.lifecycle.event.listener.LifecycleEventListener
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Menu
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

class MagicMenuItem(
  val text: StringResource,
  val icon: ImageVector? = null,
  val action: () -> Unit,
)

@Composable
fun MagicMenu(
  items: State<ImmutableList<MagicMenuItem>>,
  withSelected: Boolean = false,
  textColor: Color = MaterialTheme.colorScheme.onBackground,
  iconColor: Color = textColor,
  offset: DpOffset = DpOffset(0.dp, 0.dp),
  modifier: Modifier = Modifier,
  menuModifier: Modifier = Modifier,
  itemModifier: Modifier = Modifier,
  title: @Composable (() -> Unit)? = null,
) {
  Content(
    items = items,
    withSelected = withSelected,
    textColor = textColor,
    iconColor = iconColor,
    offset = offset,
    modifier = modifier,
    menuModifier = menuModifier,
    itemModifier = itemModifier,
    title = title,
  )
}

@Composable
private fun Content(
  items: State<ImmutableList<MagicMenuItem>>,
  withSelected: Boolean = false,
  textColor: Color = MaterialTheme.colorScheme.onBackground,
  iconColor: Color = textColor,
  offset: DpOffset = DpOffset(0.dp, 0.dp),
  modifier: Modifier = Modifier,
  menuModifier: Modifier = Modifier,
  itemModifier: Modifier = Modifier,
  title: @Composable (() -> Unit)? = null,
) {
  val itemsValue by items

  var expanded by remember {
    mutableStateOf(false)
  }

  var selected by remember {
    mutableStateOf(itemsValue.firstOrNull())
  }

  val isWithSelected by remember(withSelected) {
    mutableStateOf(withSelected)
  }

  val menuIconModifier = remember {
    Modifier
      .padding(start = 16.dp)
  }

  val menuTextModifier = remember {
    Modifier
      .widthIn(min = 120.dp)
      .padding(vertical = 4.dp)
      .padding(end = 24.dp)
  }

  Box(
    contentAlignment = Alignment.TopStart,
    modifier = Modifier
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.let {
        if (title == null) it
        else modifier
            .clip(shape = MaterialTheme.shapes.small)
            .clickable(onClick = { expanded = true })
            .padding(all = 8.dp)
      }
    ) {
      title?.invoke()

      when (isWithSelected && selected != null) {
        true -> Text(
          text = stringResource(resource = selected?.text ?: Res.string.next),
          style = TextStyleFactory.FS15.w600(),
          color = textColor,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.let {
            if (title != null) it
            else modifier
              .clip(shape = MaterialTheme.shapes.small)
              .clickable(onClick = { expanded = true })
              .padding(all = 8.dp)
          }
        )

        else -> Image(
          vector = MagicIcons.All.Menu,
          colorFilter = MaterialTheme.colorScheme.onBackground,
          modifier = modifier
            .clip(shape = MaterialTheme.shapes.small)
            .clickable(onClick = { expanded = true })
            .padding(all = 8.dp)
        )
      }
    }

    DropdownMenu(
      expanded = expanded,
      offset = offset,
      onDismissRequest = { expanded = false },
      containerColor = MaterialTheme.colorScheme.surface,
      modifier = menuModifier,
    ) {
      itemsValue.forEachIndexed { _, item ->
        DropdownMenuItem(
          leadingIcon = item.icon?.let { icon ->
            {
              Image(
                vector = icon,
                colorFilter = iconColor,
                modifier = menuIconModifier
              )
            }
          },
          text = {
            Text(
              text = stringResource(resource = item.text),
              style = TextStyleFactory.FS16.w600(),
              color = textColor,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              modifier = menuTextModifier
            )
          },
          onClick = {
            selected = item
            item.action()
            expanded = false
          },
          modifier = itemModifier
        )
      }
    }
  }

  LifecycleEventListener(
    onPause = { expanded = false }
  )
}