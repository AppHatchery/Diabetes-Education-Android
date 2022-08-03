package edu.emory.diabetes.education.presentation.fragments.orientation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentOrientationLifeIsItemBinding
import edu.emory.diabetes.education.presentation.fragments.orientation.AgendaAdapter.AgendaViewHolder

class AgendaAdapter(
    val onClick: (Int) -> Unit
) : ListAdapter<Int, AgendaViewHolder>(AgendaDiffUtil.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaViewHolder {
        return AgendaViewHolder(
            FragmentOrientationLifeIsItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: AgendaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AgendaViewHolder(
        private val bind: FragmentOrientationLifeIsItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(color: Int) = bind.apply {
            this.color = color
            executePendingBindings()
        }

        init {
            bind.root.setOnClickListener { onClick.invoke(currentList[adapterPosition]) }
        }

    }
}