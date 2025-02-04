package edu.emory.diabetes.education.presentation

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.Utils
import kotlin.math.roundToInt

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

@BindingAdapter("setBackgroundResource")
fun setBackgroundResource(view: View, backgroundShadow: Int) {
    val cornerRadius = 15 * view.context.resources.displayMetrics.density
    val yShift = (3 * view.context.resources.displayMetrics.density).roundToInt()
    view.outlineProvider = Utils.CustomOutlineProvider(cornerRadius, 1f, 1f,yShift)
    view.outlineSpotShadowColor = backgroundShadow
    view.outlineAmbientShadowColor = backgroundShadow
}

@BindingAdapter("setImageIcon")
fun setImageIcon(view: ImageView, image: Int) {
    view.setImageDrawable(ContextCompat.getDrawable(view.context, image))
}

@BindingAdapter("searchText")
fun searchText(view: TextView, search: String) {

    view.text = HtmlCompat.fromHtml(String.format("%s %s", "•", search.trim()),FROM_HTML_MODE_LEGACY)
}
