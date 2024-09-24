package com.justtwago.rides.ui.vehiclelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.justtwago.rides.R
import com.justtwago.rides.databinding.FragmentVehicleListBinding
import com.justtwago.rides.ui.model.VehicleNavArgument
import com.justtwago.rides.ui.vehiclelist.adapter.VehiclesAdapter
import com.justtwago.rides.ui.vehiclelist.model.VehicleListUiEvent
import com.justtwago.rides.ui.vehiclelist.model.VehicleSorting
import com.justtwago.rides.ui.vehiclelist.model.VehiclesState
import com.justtwago.rides.ui.vehiclelist.util.onSearchClicked
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VehicleListFragment : Fragment() {
    private var _binding: FragmentVehicleListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VehicleListViewModel by viewModels()
    private val vehiclesAdapter by lazy {
        VehiclesAdapter(onItemClicked = viewModel::onVehicleClicked)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSortingChipGroup()
        setupVehiclesRecyclerView()
        setupSearchBar()
        observeUiState()
        observeUiEvents()
    }

    private fun setupSortingChipGroup() {
        binding.sortingChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val sorting = when (checkedIds.first()) {
                R.id.vin_chip -> VehicleSorting.VIN
                R.id.car_type_chip -> VehicleSorting.CAR_TYPE
                else -> error("Unknown sorting chip id")
            }
            viewModel.onSortingSelected(sorting)
        }
    }

    private fun setupVehiclesRecyclerView() {
        binding.vehicleList.adapter = vehiclesAdapter
    }

    private fun setupSearchBar() {
        val searchInput = binding.searchInput.searchEditText
        binding.searchInput.searchButton.setOnClickListener {
            val searchQuery = searchInput.text
            if (searchQuery.isNotBlank()) {
                viewModel.onSearchVehiclesClicked(
                    vehicleCount = searchQuery.toString().toInt()
                )
            }
        }
        binding.searchInput.searchEditText.onSearchClicked { searchQuery ->
            if (searchQuery.isNotBlank()) {
                viewModel.onSearchVehiclesClicked(vehicleCount = searchQuery.toInt())
            }
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                updateSortingState(sorting = uiState.sorting)
                updateVehiclesState(state = uiState.vehicles)
            }
        }
    }

    private fun updateSortingState(sorting: VehicleSorting) {
        binding.sortingChipGroup.check(
            when (sorting) {
                VehicleSorting.VIN -> R.id.vin_chip
                VehicleSorting.CAR_TYPE -> R.id.car_type_chip
            }
        )
    }

    private fun updateVehiclesState(state: VehiclesState) {
        when (state) {
            is VehiclesState.Content -> {
                vehiclesAdapter.submitList(state.vehicles) {
                    binding.vehicleList.scrollToPosition(0)
                }
                setSearchInputState(searching = false)
                setSortingState(searching = false)
            }

            is VehiclesState.Loading -> {
                setSearchInputState(searching = true)
                setSortingState(searching = true)
            }
        }
    }

    private fun setSearchInputState(searching: Boolean) {
        binding.searchInput.searchEditText.isEnabled = !searching
        binding.searchInput.progressBar.isVisible = searching
        binding.searchInput.searchButton.isVisible = !searching
    }

    private fun setSortingState(searching: Boolean) {
        binding.vinChip.isEnabled = !searching
        binding.carTypeChip.isEnabled = !searching
    }

    private fun observeUiEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiEvents.collect { event ->
                when (event) {
                    is VehicleListUiEvent.NavigateToVehicleDetails -> {
                        navigateToVehicleDetails(argument = event.vehicleNavArgument)
                    }
                }
            }
        }
    }

    private fun navigateToVehicleDetails(argument: VehicleNavArgument) {
        val action = VehicleListFragmentDirections
            .actionVehicleListFragmentToVehicleDetailsFragment(vehicle = argument)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}