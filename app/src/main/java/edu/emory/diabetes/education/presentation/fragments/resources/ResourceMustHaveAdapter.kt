package edu.emory.diabetes.education.presentation.fragments.resources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentMustHaveAppsItemBinding
import edu.emory.diabetes.education.databinding.FragmentResourceCommunitiesItemBinding
import edu.emory.diabetes.education.databinding.FragmentResourceMustHaveItemBinding
import edu.emory.diabetes.education.domain.model.Communities
import edu.emory.diabetes.education.domain.model.MustHaveApp


class ResourceMustHaveAdapter(
    val onEvent: (MustHaveApp) -> Unit?
) : ListAdapter<MustHaveApp, ResourceMustHaveAdapter.MustHaveAppsViewHolder>(diffUtil) {

    inner class MustHaveAppsViewHolder(
        private val bind: FragmentResourceMustHaveItemBinding
    ): RecyclerView.ViewHolder(bind.root){
        fun bind(mustHaveApp: MustHaveApp) =bind.apply {
            this.data = mustHaveApp
        }

        init {
            bind.rectShape.setOnClickListener { onEvent.invoke(currentList[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MustHaveAppsViewHolder {
        return MustHaveAppsViewHolder(
            FragmentResourceMustHaveItemBinding.inflate(
                LayoutInflater.from(parent.context),parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MustHaveAppsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<MustHaveApp>() {
            override fun areItemsTheSame(oldItem: MustHaveApp, newItem: MustHaveApp): Boolean {
                return newItem == oldItem
            }

            override fun areContentsTheSame(oldItem: MustHaveApp, newItem: MustHaveApp): Boolean {
                return oldItem.descriptor == newItem.descriptor
            }
        }
    }


}