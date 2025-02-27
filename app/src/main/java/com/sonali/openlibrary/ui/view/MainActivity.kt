package com.sonali.openlibrary.ui.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sonali.openlibrary.R
import com.sonali.openlibrary.databinding.ActivityMainBinding
import com.sonali.openlibrary.local.dao.entity.Entry
import com.sonali.openlibrary.ui.adapter.BookListAdapter
import com.sonali.openlibrary.ui.viewmodel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: BookViewModel by viewModels()
    private var filteredList = mutableListOf<Entry>()
    private var libList = mutableListOf<Entry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Setup Toolbar
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            viewModel.getBookList()
            viewModel.booklist.observe(this@MainActivity) { lists ->
                Log.e("MainActivity", "Found data : $lists")
                if (lists.isNotEmpty()) {
                    libList.addAll(lists)
                    binding.recyclerView.adapter = BookListAdapter(lists)
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.tvErrorMessage.visibility = View.GONE  // Hide error message if data exists
                }else{
                    binding.recyclerView.visibility = View.GONE
                    binding.tvErrorMessage.visibility = View.VISIBLE  // show error message if data dos not exist
                }

            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = "Search Library..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               // filter(query)
                lifecycleScope.launch {
                    query?.let {
                        viewModel.searchBooks(it)
                        viewModel.filteredBookList.observe(this@MainActivity) { list ->
                            if(list.isEmpty()){
                                binding.recyclerView.visibility = View.GONE
                                binding.tvErrorMessage.visibility = View.VISIBLE  // show error message if data dos not exist
                            }else{
                                binding.recyclerView.adapter = BookListAdapter(list)
                                binding.recyclerView.visibility = View.VISIBLE
                                binding.tvErrorMessage.visibility = View.GONE  // Hide error message if data exists
                            }
                        }

                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //filter
                if(newText?.length==0) {
                    binding.recyclerView.adapter = BookListAdapter(libList)
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.tvErrorMessage.visibility = View.GONE  // Hide error message if data exists
                }
                return true
            }
        })

        return true
    }

}