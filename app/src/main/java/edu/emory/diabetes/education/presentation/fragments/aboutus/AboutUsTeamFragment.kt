package edu.emory.diabetes.education.presentation.fragments.aboutus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.databinding.FragmentAboutUsTeamBinding
import edu.emory.diabetes.education.presentation.BaseFragment


class AboutUsTeamFragment : BaseFragment(R.layout.fragment_about_us__team){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(FragmentAboutUsTeamBinding.bind(view)){
        imgApphatcherylink.setOnClickListener{
            val openUrlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Ext.getAppHatcheryUrl()))
            val chooser = Intent.createChooser(openUrlIntent, "Opens with")
            if (openUrlIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(chooser)
            }
        }
        }

    }
}