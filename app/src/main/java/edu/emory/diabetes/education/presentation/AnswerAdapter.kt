package edu.emory.diabetes.education.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentAnswerItemBinding

class AnswerAdapter : ListAdapter<String, AnswerAdapter.ViewHolder>(AnswerAdapter.diff) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentAnswerItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val bind: FragmentAnswerItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(answer: String) = bind.apply {
            this.answer = answer
            executePendingBindings()
        }
    }
}
