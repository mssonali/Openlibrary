package com.sonali.openlibrary.repository

import com.sonali.openlibrary.local.dao.LibraryDao
import com.sonali.openlibrary.model.MyBooks
import com.sonali.openlibrary.network.BookApiService
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BookApiService, private val libraryDao: LibraryDao?){
    suspend fun getBookList(): MyBooks? {
        val libEntry = api.getBookList()
        return libEntry.body()
    }
//        suspend fun insertEntries(entries: List<Entry>) {
//            entries.forEach { entry ->
//                libraryDao.insertBook(entry) // Inserting each entry
//            }
//        }

}