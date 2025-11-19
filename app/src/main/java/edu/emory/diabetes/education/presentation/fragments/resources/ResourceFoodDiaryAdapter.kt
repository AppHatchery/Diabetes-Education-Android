package edu.emory.diabetes.education.presentation.fragments.resources

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentResourceFoodDiaryItemBinding
import edu.emory.diabetes.education.domain.model.FoodDiary
import edu.emory.diabetes.education.presentation.fragments.resources.ResourceFoodDiaryAdapter.ViewHolder

class ResourceFoodDiaryAdapter(
    val onEvent: (FoodDiary) -> Unit
) : ListAdapter<FoodDiary, ViewHolder>(diffUtil) {

    inner class ViewHolder(
        private val bind: FragmentResourceFoodDiaryItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(mustHaveApp: FoodDiary) = bind.apply {
            data = mustHaveApp
            executePendingBindings()
        }

        init {
            bind.root.setOnClickListener { onEvent.invoke(currentList[adapterPosition]) }
        }


    }


    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<FoodDiary>() {
            override fun areItemsTheSame(oldItem: FoodDiary, newItem: FoodDiary): Boolean {
                return newItem == oldItem
            }

            override fun areContentsTheSame(oldItem: FoodDiary, newItem: FoodDiary): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentResourceFoodDiaryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}