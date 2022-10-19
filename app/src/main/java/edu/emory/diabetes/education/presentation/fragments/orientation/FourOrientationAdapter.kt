package edu.emory.diabetes.education.presentation.fragments.orientation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.emory.diabetes.education.databinding.FragmentOrientationLifeIsItemBinding
import edu.emory.diabetes.education.domain.model.FourOrientation

class FourOrientationAdapter: ListAdapter<FourOrientation, FourOrientationAdapter.FourOrientationViewHolder>(diffUtil) {

    class FourOrientationViewHolder(
        private val bind: FragmentOrientationLifeIsItemBinding
    ): RecyclerView.ViewHolder(bind.root){
        fun bind(fourOrientation: FourOrientation) = bind.apply {
            data = fourOrientation
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FourOrientationViewHolder {
        return FourOrientationViewHolder(
            FragmentOrientationLifeIsItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: FourOrientationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        private val diffUtil = object: DiffUtil.ItemCallback<FourOrientation>(){
            override fun areItemsTheSame(
                oldItem: FourOrientation,
                newItem: FourOrientation
            ): Boolean {
                return newItem == oldItem
            }

            override fun areContentsTheSame(
                oldItem: FourOrientation,
                newItem: FourOrientation
            ): Boolean {
                return oldItem.age == newItem.age
            }

        }
    }

}