package com.justtwago.rides.ui.vehicledetails.tabs

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.justtwago.rides.domain.model.Vehicle
import com.justtwago.rides.ui.vehicledetails.tabs.basic.BasicVehicleDetailsTabFragment
import com.justtwago.rides.ui.vehicledetails.tabs.carbonemissions.CarbonEmissionsVehicleDetailsTabFragment

private const val VEHICLE_DETAILS_TABS_COUNT = 2
private const val BASIC_VEHICLE_DETAILS_TAB_POSITION = 0
private const val CARBON_EMISSIONS_VEHICLE_DETAILS_TAB_POSITION = 1

class VehicleDetailsAdapter(
    private val vehicle: Vehicle,
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = VEHICLE_DETAILS_TABS_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            BASIC_VEHICLE_DETAILS_TAB_POSITION -> {
                BasicVehicleDetailsTabFragment().also { fragment ->
                    fragment.arguments = BasicVehicleDetailsTabFragment.createArguments(
                        color = vehicle.color,
                        vin = vehicle.vin
                    )
                }
            }

            CARBON_EMISSIONS_VEHICLE_DETAILS_TAB_POSITION -> {
                CarbonEmissionsVehicleDetailsTabFragment().also { fragment ->
                    fragment.arguments = CarbonEmissionsVehicleDetailsTabFragment.createArguments(
                        kilometrage = vehicle.kilometrage
                    )
                }
            }

            else -> error("Unknown tab position")
        }
    }
}