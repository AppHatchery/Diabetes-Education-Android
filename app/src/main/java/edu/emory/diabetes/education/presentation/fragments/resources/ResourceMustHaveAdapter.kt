package edu.emory.diabetes.education.presentation.fragments.resources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentResourceMustHaveItemBinding
import edu.emory.diabetes.education.domain.model.MustHaveApp
import edu.emory.diabetes.education.presentation.fragments.resources.ResourceMustHaveAdapter.ViewHolder

class ResourceMustHaveAdapter : ListAdapter<MustHaveApp, ViewHolder>(diffUtil) {

    class ViewHolder(
        private val bind: FragmentResourceMustHaveItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(mustHaveApp: MustHaveApp) = bind.apply {
            data = mustHaveApp
            executePendingBindings()
        }
    }


    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MustHaveApp>() {
            override fun areItemsTheSame(oldItem: MustHaveApp, newItem: MustHaveApp): Boolean {
                return newItem == oldItem
            }

            override fun areContentsTheSame(oldItem: MustHaveApp, newItem: MustHaveApp): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentResourceMustHaveItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}