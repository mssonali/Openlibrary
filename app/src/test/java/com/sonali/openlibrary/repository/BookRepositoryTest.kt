package com.sonali.openlibrary.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sonali.openlibrary.local.dao.entity.Entry
import com.sonali.openlibrary.model.Links
import com.sonali.openlibrary.model.MyBooks
import com.sonali.openlibrary.network.BookApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class BookRepositoryTest {

        @Mock
        private lateinit var bookApiService: BookApiService

        private lateinit var bookRepository: BookRepository
    @get:Rule
        val instantTaskExecutorRule = InstantTaskExecutorRule() // Allows LiveData to be tested synchronously

        @Before
        fun setUp() {
            MockitoAnnotations.openMocks(this)
            bookRepository = BookRepository(bookApiService, null)
        }

        @Test
        fun `fetch books from API and return success`() = runBlockingTest {
            val fakeBookList = listOf(
                Entry(id = 1, name = "Museum in a Box", full_url = "/people/george08/lists/OL95357L/Museum_in_a_Box", "/people/george08/lists/OL92303L",seed_count = 3, last_update = "2023-11-15T02:45:45.349490"),
                Entry(id = 2, name = "Museums", full_url = "/people/george08/lists/OL92303L/Museums", "/people/george08/lists/OL95357L",seed_count = 28, last_update = "2024-09-05T03:06:17.320946"))
            val mockResponse = Response.success(MyBooks(fakeBookList,Links("/people/george08/lists.json"),2))

            Mockito.`when`(bookApiService.getBookList()).thenReturn(mockResponse)

            // Fetch books from repository
            val result = bookRepository.getBookList()

            // Assertions
            assertNotNull(result)
            assertEquals(2, result!!.size)
            assertEquals("Museum in a Box", result.entries.get(0).name)

            // Verify API was called once
            verify(bookApiService, times(1)).getBookList()
        }

        @Test
        fun `fetch books from API and return empty list`() = runBlockingTest {
            // Mock empty response
            val mockResponse: Response<List<Entry>> = Response.success(listOf(Entry(0,"", "","",5,"")))

            val emptyBook = MyBooks(emptyList(), Links(""), 0) // Make sure MyBooks has default values
            `when`(bookApiService.getBookList()).thenReturn(Response.success(emptyBook))

            val result = bookRepository.getBookList()

            // Assertions
            assertNotNull(result)
            assertTrue(result?.size ==0)

            // Verify API was called once
            verify(bookApiService, times(1)).getBookList()
        }
    }

