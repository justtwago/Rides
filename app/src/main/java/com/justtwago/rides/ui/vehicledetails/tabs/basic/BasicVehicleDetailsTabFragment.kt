package com.justtwago.rides.ui.vehicledetails.tabs.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.justtwago.rides.R
import com.justtwago.rides.databinding.FragmentBasicVehicleDetailsTabBinding

class BasicVehicleDetailsTabFragment : Fragment() {

    private var _binding: FragmentBasicVehicleDetailsTabBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasicVehicleDetailsTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderVehicleDetails()
    }

    private fun renderVehicleDetails() {
        binding.color.text = getString(R.string.color_template, arguments?.getString(COLOR_KEY))
        binding.vin.text = getString(R.string.vin_template, arguments?.getString(VIN_KEY))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val COLOR_KEY = "color_key"
        private const val VIN_KEY = "vin_key"

        fun createArguments(
            color: String,
            vin: String
        ): Bundle {
            return Bundle().apply {
                putString(COLOR_KEY, color)
                putString(VIN_KEY, vin)
            }
        }
    }
}