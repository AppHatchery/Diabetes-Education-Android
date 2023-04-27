package edu.emory.diabetes.education.presentation.fragments.aboutus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentAboutUsContentBinding
import edu.emory.diabetes.education.presentation.BaseFragment


class AboutAsContentFragment : BaseFragment(R.layout.fragment_about_us__content){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(FragmentAboutUsContentBinding.bind(view)){

        cardViewAboutUsContent.setOnClickListener {
            val openUrlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Ext.getAmericanDiabetesUrl()))
            val chooser = Intent.createChooser(openUrlIntent, "Open with")
            if (openUrlIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(chooser)
            }
        }

            /*Attach click event on the anchor tags in the html string resource*/
            sendMail.movementMethod = LinkMovementMethod.getInstance()

       }
    }



}

