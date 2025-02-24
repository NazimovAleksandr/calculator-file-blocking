package com.next.level.solutions.calculator.fb.mp.entity.ui

import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType

data class PhotoModelUI(
  override val path: String,
  override val name: String,
  override val folder: String,
  override val size: Long,
  override val dateAdded: String,
  override val dateHidden: String,
  override val dateModified: String,
  override val hiddenPath: String? = null,
) : FileDataUI {
  override val fileType: FilePickerFileType = FilePickerFileType.Photo
  override val duration: String = ""
}
