package edu.emory.diabetes.education.presentation.fragments.resources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentMustHaveAppsItemBinding
import edu.emory.diabetes.education.domain.model.MustHaveAppPage

class MustHaveRecyclerAdapter :
    ListAdapter<MustHaveAppPage, MustHaveRecyclerAdapter.MustHaveViewHolder>(diffUtil) {
    class MustHaveViewHolder(
        private val bind: FragmentMustHaveAppsItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(mustHave: MustHaveAppPage) = bind.apply {
            data = mustHave
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MustHaveViewHolder {
        return MustHaveViewHolder(
            FragmentMustHaveAppsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MustHaveViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MustHaveAppPage>() {
            override fun areItemsTheSame(
                oldItem: MustHaveAppPage, newItem: MustHaveAppPage
            ): Boolean {
                return newItem == oldItem
            }

            override fun areContentsTheSame(
                oldItem: MustHaveAppPage,
                newItem: MustHaveAppPage
            ): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }
}