package com.sonali.openlibrary.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonali.openlibrary.AppUtils.Utils
import com.sonali.openlibrary.local.dao.LibraryDao
import com.sonali.openlibrary.local.dao.db.LibraryDatabase
import com.sonali.openlibrary.local.dao.entity.Entry
import com.sonali.openlibrary.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val repository: BookRepository,
                                        val libraryDao: LibraryDao,
                                        val application: Application):ViewModel(){

    val booklist = MutableLiveData<List<Entry>>()
    private var bookList = mutableListOf<Entry>()
    val list: LiveData<List<Entry>> get() = booklist
    val items = mutableListOf<com.sonali.openlibrary.local.dao.entity.Entry>()  // Create a mutable list
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _filteredBookList = MutableLiveData<List<Entry>>() // Filtered list
    val filteredBookList: LiveData<List<Entry>> = _filteredBookList
     val _isLoading = MutableLiveData<Boolean>()

    fun getBookList() {
        viewModelScope.launch {
            delay(2000) // Simulating network delay
                try {
                    if(Utils.isInterConnectionIsAvailable(application)){
                        _isLoading.value = true
                        val response = repository.getBookList()
                        response?.entries?.let { entries: List<Entry> ->
                            libraryDao.deleteAlllibraries()
                            libraryDao.insertBook(entries)  // Insert into Room
                        }
                        booklist.postValue(response?.entries)
                        _isLoading.value = false // Hide Loader
                    }else{
                        val bookDao = LibraryDatabase.getDatabase(application).libraryDao().getAllLibraries()
                        if(!bookDao.isEmpty()) {
                            booklist.postValue(bookDao)
                        }else{
                            _error.postValue("No library available at the moment.")
                        }
                    }

                } catch (e: Exception) {
                    _isLoading.value = false // Hide Loader
                    _error.postValue("Failed to load library: ${e.message}")
                }
        }
    }
//    // Search function
//    fun searchBooks(query: String) {
//        val fullList = booklist.value ?: emptyList()
//
//        val filteredList = if (query.isEmpty()) {
//            fullList // Show all books if query is empty
//        } else {
//            fullList.filter { it.name.contains(query, ignoreCase = true) } // Filter list
//        }
//
//        _filteredBookList.value = filteredList
//    }

    fun searchBooks(query: String) {
        val fullList = booklist.value ?: emptyList()

        val filteredList = if (query.isEmpty()) {
            fullList // Return all books if query is empty
        } else {
            fullList.filter {
                it.name?.contains(query, ignoreCase = true) ?: false
            } // Ensure name is not null before filtering
        }

        _filteredBookList.value = filteredList // Update the filtered list
        if(_filteredBookList.value?.size==0){
            _error.postValue("Not found.")
        }

        // Debugging
        Log.d("SearchBooks", "Query: $query, Filtered Size: ${filteredList.size}")
    }
}