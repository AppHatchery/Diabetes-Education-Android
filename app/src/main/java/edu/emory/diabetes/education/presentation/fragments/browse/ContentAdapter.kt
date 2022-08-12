package edu.emory.diabetes.education.presentation.fragments.browse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentContentItemBinding
import edu.emory.diabetes.education.domain.model.Content

class ContentAdapter : ListAdapter<Content, ContentAdapter.ViewHolder>(ContentDiffUtil.diffUtil) {

    inner class ViewHolder(
        private val bind: FragmentContentItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(content: Content) = bind.apply {
            this.content = content
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentContentItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}