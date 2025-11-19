package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.InsulinCalculatorItemBinding
import edu.emory.diabetes.education.domain.model.CalculatorTypes

class InsulinCalculatorAdapter(
    val onEvent: (CalculatorTypes) -> Unit
): ListAdapter<CalculatorTypes, InsulinCalculatorAdapter.InsulinCalculatorViewHolder>(diffUtil) {

    inner class InsulinCalculatorViewHolder(
        private val bind: InsulinCalculatorItemBinding
    ): RecyclerView.ViewHolder(bind.root){
        fun bind(calculatorTypes: CalculatorTypes) = bind.apply {
            data = calculatorTypes
            executePendingBindings()
        }

        init {
            bind.root.setOnClickListener { onEvent.invoke(currentList[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsulinCalculatorViewHolder {
        return InsulinCalculatorViewHolder(
            InsulinCalculatorItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: InsulinCalculatorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        private val diffUtil = object: DiffUtil.ItemCallback<CalculatorTypes>(){
            override fun areItemsTheSame(
                oldItem: CalculatorTypes,
                newItem: CalculatorTypes
            ): Boolean {
                return newItem == oldItem
            }

            override fun areContentsTheSame(
                oldItem: CalculatorTypes,
                newItem: CalculatorTypes
            ): Boolean {
                return oldItem.title == newItem.title
            }

        }
    }
}