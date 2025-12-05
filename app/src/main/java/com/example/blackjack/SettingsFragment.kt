package com.example.blackjack

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.TextLanguage
import com.example.blackjack.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibSettingsFv.setOnClickListener {
            removeSettingsFragment()
        }

        //took help by AI to make this work
        val orientation = resources.configuration.orientation
        binding.switchLand.isChecked = orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

        binding.switchLand.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }else {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }
        binding.switchHand.setOnCheckedChangeListener { _, isChecked ->
            sharedViewModel.setTextVisible(!isChecked)
            }
    }

    private fun removeSettingsFragment(){
        requireActivity().findViewById<View>(R.id.settingsOverlay).visibility = View.GONE

        parentFragmentManager.beginTransaction()
            .remove(this)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}