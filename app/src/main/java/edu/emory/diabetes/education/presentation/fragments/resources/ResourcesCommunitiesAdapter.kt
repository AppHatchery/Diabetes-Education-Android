package edu.emory.diabetes.education.presentation.fragments.resources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentResourceCommunitiesItemBinding
import edu.emory.diabetes.education.domain.model.Communities

class ResourcesCommunitiesAdapter :
    ListAdapter<Communities, ResourcesCommunitiesAdapter.CommunitiesViewHolder>(diffUtil) {

    class CommunitiesViewHolder(
        private val bind: FragmentResourceCommunitiesItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(communities: Communities) = bind.apply {
            data = communities
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunitiesViewHolder {
        return CommunitiesViewHolder(
            FragmentResourceCommunitiesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CommunitiesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Communities>() {
            override fun areItemsTheSame(oldItem: Communities, newItem: Communities): Boolean {
                return newItem == oldItem
            }

            override fun areContentsTheSame(oldItem: Communities, newItem: Communities): Boolean {
                return oldItem.descriptor == newItem.descriptor
            }
        }
    }
}