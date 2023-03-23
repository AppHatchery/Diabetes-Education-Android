package edu.emory.diabetes.education.presentation.fragments.aboutus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.databinding.FragmentAboutUsTeamBinding


class AboutUsTeamFragment : Fragment(){

private lateinit var binding: FragmentAboutUsTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAboutUsTeamBinding.inflate(inflater, container, false)

        binding.imgApphatcherylink.setOnClickListener{
            val openUrlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Ext.getAppHatcheryUrl()))
            val chooser = Intent.createChooser(openUrlIntent, "Open with")
            if (openUrlIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(chooser)
            }
        }

        return binding.root
    }

}