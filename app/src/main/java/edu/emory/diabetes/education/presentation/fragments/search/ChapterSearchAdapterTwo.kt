package edu.emory.diabetes.education.presentation.fragments.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentChapterSearchItemBinding
import edu.emory.diabetes.education.domain.model.ChapterSearch


class ChapterSearchAdapterTwo(private val onItemClick: SearchUtil.OnItemClick) : ListAdapter<ChapterSearch, ChapterSearchAdapterTwo.ViewHolder>(diffUtil) {

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<ChapterSearch>() {
            override fun areItemsTheSame(oldItem: ChapterSearch, newItem: ChapterSearch): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ChapterSearch,
                newItem: ChapterSearch
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(private val bind: FragmentChapterSearchItemBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(chapterSearch: ChapterSearch) = bind.apply {
            this.chapterSearch = chapterSearch
            root.setOnClickListener{
                onItemClick.onItemClick(chapterSearch)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentChapterSearchItemBinding.inflate(LayoutInflater.from(parent.context))

        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}