package edu.emory.diabetes.education.presentation.fragments.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentCalculatorDialogItemBinding
import edu.emory.diabetes.education.presentation.fragments.calculator.InsulinDialogAdapter.InsulinDialogViewHolder

class InsulinDialogAdapter(
    val onClick: (Int) -> Unit
) : ListAdapter<Int, InsulinDialogViewHolder>(InsulinDialogDiffUtil.diffUtil) {

    companion object {
        var currentPosition = 14
    }

    override fun onBindViewHolder(holder: InsulinDialogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsulinDialogViewHolder {
        return InsulinDialogViewHolder(
            FragmentCalculatorDialogItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    inner class InsulinDialogViewHolder(
        private val bind: FragmentCalculatorDialogItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(int: Int) = bind.apply {
            integer = int
            if (int == currentPosition) {
                root.setBackgroundResource(R.color.green_50)
            } else root.setBackgroundResource(android.R.color.white)
            executePendingBindings()
        }

        init {
            bind.root.setOnClickListener { onClick.invoke(currentList[adapterPosition]) }
        }
    }


}