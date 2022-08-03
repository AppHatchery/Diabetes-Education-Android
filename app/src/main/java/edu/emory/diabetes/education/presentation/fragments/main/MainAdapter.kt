package edu.emory.diabetes.education.presentation.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentMainItemBinding
import edu.emory.diabetes.education.domain.model.Chapter
import edu.emory.diabetes.education.presentation.fragments.main.MainAdapter.MainViewHolder

class MainAdapter(
    val onClick: (Chapter) -> Unit
) : ListAdapter<Chapter, MainViewHolder>(MainDiffUtil.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            FragmentMainItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainViewHolder(
        private val bind: FragmentMainItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(chapter: Chapter) = bind.apply {
            this.chapter = chapter
            executePendingBindings()
        }

        init {
            bind.root.setOnClickListener { onClick.invoke(currentList[adapterPosition]) }
        }

    }
}