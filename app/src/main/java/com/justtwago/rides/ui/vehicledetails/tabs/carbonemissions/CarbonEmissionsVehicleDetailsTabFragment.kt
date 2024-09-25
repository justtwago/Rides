package com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.justtwago.rides.R
import com.justtwago.rides.databinding.FragmentCarbonEmissionsVehicleDetailsTabBinding
import com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions.CarbonEmissionsVehicleDetailsViewModel.Factory
import com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions.model.CarbonEmissionsVehicleDetailsUiState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch

private typealias Ignored = Unit

@AndroidEntryPoint
class CarbonEmissionsVehicleDetailsTabFragment : Fragment() {
    private var _binding: FragmentCarbonEmissionsVehicleDetailsTabBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CarbonEmissionsVehicleDetailsViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras.withCreationCallback<Factory> { factory ->
                factory.create(kilometrage = requireArguments().getInt(KILOMETRAGE_KEY))
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarbonEmissionsVehicleDetailsTabBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is CarbonEmissionsVehicleDetailsUiState.Initial -> Ignored
                    is CarbonEmissionsVehicleDetailsUiState.Details -> {
                        renderDetails(state)
                    }
                }
            }
        }
    }

    private fun renderDetails(state: CarbonEmissionsVehicleDetailsUiState.Details) {
        binding.kilometrage.text = getString(
            R.string.kilometrage_template,
            state.kilometrage
        )
        binding.carbonEmissions.text = getString(
            R.string.carbon_emissions_template,
            state.carbonEmissions
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val KILOMETRAGE_KEY = "kilometrage_key"

        fun createArguments(kilometrage: Int): Bundle {
            return Bundle().apply {
                putInt(KILOMETRAGE_KEY, kilometrage)
            }
        }
    }
}