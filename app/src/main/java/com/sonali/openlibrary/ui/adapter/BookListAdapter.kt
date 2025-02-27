package com.sonali.openlibrary.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.sonali.openlibrary.AppUtils.Utils
import com.sonali.openlibrary.databinding.ListItemBinding
import com.sonali.openlibrary.local.dao.entity.Entry


class BookListAdapter(private val lists: List<Entry>) : RecyclerView.Adapter<BookListAdapter.ListViewHolder>() {

    class ListViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(list: Entry) {
            binding.listName.text = list.name
            val fomattedDate = Utils.getFormattedDate(list.last_update)
            binding.listDescription.text = fomattedDate?: "No date available"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(lists[position])
    }

    override fun getItemCount(): Int = lists.size
}
