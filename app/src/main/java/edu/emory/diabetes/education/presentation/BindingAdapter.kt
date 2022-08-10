package edu.emory.diabetes.education.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImageResourceWithGlide")
fun setImageResourceWithGlide(view: ImageView, drawableRes: Int) {
    Glide.with(view.context).load(drawableRes).into(view)
}


@BindingAdapter("setBackgroundTintColorResource")
fun setBackgroundTintColorResource(view: View, backgroundColor: Int) {
    view.background.setTint(ContextCompat.getColor(view.context, backgroundColor))
}

@BindingAdapter("setBackgroundColorResource")
fun setBackgroundColorResource(view: CardView, backgroundColor: Int) {
    view.setCardBackgroundColor(ContextCompat.getColor(view.context, backgroundColor))
}

@BindingAdapter("setImageIcon")
fun setImageIcon(view: ImageView, image: Int) {
    if (image != 0)
        view.setImageDrawable(ContextCompat.getDrawable(view.context, image))
}
