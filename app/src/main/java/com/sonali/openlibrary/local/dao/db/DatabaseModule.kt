package com.sonali.openlibrary.local.dao.db

import android.content.Context
import androidx.room.Room
import com.sonali.openlibrary.local.dao.LibraryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LibraryDatabase {
        return Room.databaseBuilder(
            context,
            LibraryDatabase::class.java,
            "library_database"
        ).build()
    }

    @Provides
    fun provideLibraryDao(database: LibraryDatabase): LibraryDao {
        return database.libraryDao()
    }
}