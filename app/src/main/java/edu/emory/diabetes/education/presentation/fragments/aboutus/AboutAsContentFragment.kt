package edu.emory.diabetes.education.presentation.fragments.aboutus

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import edu.emory.diabetes.education.BuildConfig
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentAboutUsContentBinding
import edu.emory.diabetes.education.presentation.BaseFragment

private const val PRIVACY_POLICY_URL =
    "https://www.choa.org/-/media/Files/Childrens/patients/patient-privacy-notice-2021.pdf"

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

            privacyPolicy.setOnClickListener {
                requireActivity().startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_POLICY_URL))
                )
            }

            // Set the version name in the build.gradle file
            version.text = String.format("v%s", BuildConfig.VERSION_NAME)

            /*Attach click event on the anchor tags in the html string resource*/
            sendMail.movementMethod = LinkMovementMethod.getInstance()

       }
    }



}

