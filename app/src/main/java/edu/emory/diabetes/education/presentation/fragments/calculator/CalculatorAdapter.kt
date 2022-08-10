package edu.emory.diabetes.education.presentation.fragments.calculator

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentCalculatorItemBinding
import edu.emory.diabetes.education.domain.model.InsulinCalculator
import edu.emory.diabetes.education.presentation.fragments.calculator.CalculatorAdapter.CalculatorViewHolder

class CalculatorAdapter :
    ListAdapter<InsulinCalculator, CalculatorViewHolder>(CalculatorDiffUtil.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorViewHolder {
        return CalculatorViewHolder(
            FragmentCalculatorItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CalculatorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CalculatorViewHolder(
        private val bind: FragmentCalculatorItemBinding
    ) : RecyclerView.ViewHolder(bind.root) {
        fun bind(data: InsulinCalculator) = bind.apply {
            insulinCalculator = data
            executePendingBindings()
        }
    }
}