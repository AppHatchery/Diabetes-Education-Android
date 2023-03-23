package edu.emory.diabetes.education.presentation.fragments.aboutus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.emory.diabetes.education.Ext
import edu.emory.diabetes.education.databinding.FragmentAboutUsContentBinding


class AboutAsContentFragment : Fragment(){


    private lateinit var binding: FragmentAboutUsContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutUsContentBinding.inflate(inflater, container, false)

        binding.cardViewAboutUsContent.setOnClickListener {
            val openUrlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Ext.getAmericanDiabetesUrl()))
            val chooser = Intent.createChooser(openUrlIntent, "Open with")
            if (openUrlIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(chooser)
            }
        }
        return binding.root
    }
}

