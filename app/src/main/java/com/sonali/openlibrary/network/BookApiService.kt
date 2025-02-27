package com.sonali.openlibrary.network

import com.sonali.openlibrary.model.MyBooks
import retrofit2.Response
import retrofit2.http.GET

interface BookApiService {
    @GET("people/george08/lists.json")
    suspend fun getBookList(): Response<MyBooks>
    //searchBook()
}