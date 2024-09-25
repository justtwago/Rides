package com.justtwago.rides.ui.vehicledetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.justtwago.rides.R
import com.justtwago.rides.databinding.FragmentVehicleDetailsBinding
import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.ui.vehicledetails.model.VehicleDetailsUiState
import com.justtwago.rides.ui.vehicledetails.tabs.VehicleDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private typealias Ignored = Unit

@AndroidEntryPoint
class VehicleDetailsFragment : Fragment() {
    private var _binding: FragmentVehicleDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VehicleDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleDetailsBinding.inflate(inflater, container, false)
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
                    is VehicleDetailsUiState.Initial -> Ignored
                    is VehicleDetailsUiState.Details -> {
                        renderDetails(state.vehicle)
                        renderTabs(state.vehicle)
                    }
                }
            }
        }
    }

    private fun renderDetails(vehicle: Vehicle) {
        binding.makeAndModel.text = vehicle.makeAndModel
        binding.carType.text = vehicle.carType
    }

    private fun renderTabs(vehicle: Vehicle) {
        binding.pager.adapter = VehicleDetailsAdapter(vehicle, this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.basic)
                1 -> getString(R.string.carbon_emissions)
                else -> error("Unknown tab position")
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}