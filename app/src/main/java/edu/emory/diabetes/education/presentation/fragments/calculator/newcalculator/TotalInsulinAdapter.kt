package edu.emory.diabetes.education.presentation.fragments.calculator.newcalculator

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentTotalInsulinItemBinding
import edu.emory.diabetes.education.domain.model.InsulinCalculator
import edu.emory.diabetes.education.presentation.fragments.calculator.CalculatorDiffUtil

class TotalInsulinAdapter:
    ListAdapter<InsulinCalculator, TotalInsulinAdapter.TotalInsulinViewHolder>(CalculatorDiffUtil.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalInsulinViewHolder {
        return TotalInsulinViewHolder(
            FragmentTotalInsulinItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: TotalInsulinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class TotalInsulinViewHolder(
        private val bind: FragmentTotalInsulinItemBinding
    ): RecyclerView.ViewHolder(bind.root){
        fun bind(data: InsulinCalculator) = bind.apply {
            this.insulinCalculator = data
            executePendingBindings()
        }
    }

}