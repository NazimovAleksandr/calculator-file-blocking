package com.next.level.solutions.calculator.fb.mp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.next.level.solutions.calculator.fb.mp.entity.db.FileDataDB
import com.next.level.solutions.calculator.fb.mp.entity.db.FileModelDB
import kotlinx.coroutines.flow.Flow

@Dao
interface FileDao : DaoDB {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(vararg item: FileModelDB)

  @Query("SELECT * FROM files_db ORDER by date_added DESC")
  fun getByDateAdded(): Flow<List<FileModelDB>>

  @Query("SELECT * FROM files_db ORDER by size DESC")
  fun getByFileSize(): Flow<List<FileModelDB>>

  @Query("SELECT * FROM files_db ORDER by name ASC")
  fun getByName(): Flow<List<FileModelDB>>

  @Delete
  suspend fun delete(vararg item: FileModelDB)

  override suspend fun insert(vararg item: FileDataDB) {
    insert(*item.mapNotNull { it as? FileModelDB }.toTypedArray())
  }

  override suspend fun delete(vararg item: FileDataDB) {
    delete(*item.mapNotNull { it as? FileModelDB }.toTypedArray())
  }

  override fun byDate(): Flow<List<FileDataDB>> {
    return getByDateAdded()
  }

  override fun bySize(): Flow<List<FileDataDB>> {
    return getByFileSize()
  }

  override fun byName(): Flow<List<FileDataDB>> {
    return getByName()
  }
}