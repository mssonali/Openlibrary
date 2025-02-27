package com.sonali.openlibrary.local.dao

import androidx.room.*
import com.sonali.openlibrary.local.dao.entity.Entry

@Dao
interface LibraryDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(entry: List<Entry>)

    @Query("SELECT * FROM library")
    suspend fun getAllLibraries():List<Entry>

    @Query("DELETE FROM library")
    suspend fun deleteAlllibraries()
}