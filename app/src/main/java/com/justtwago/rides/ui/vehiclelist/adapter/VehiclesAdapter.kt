package com.justtwago.rides.ui.vehiclelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.justtwago.rides.R
import com.justtwago.rides.databinding.ItemVehicleBinding
import com.justtwago.rides.domain.model.Vehicle

class VehiclesAdapter(
    private val onItemClicked: (vehicle: Vehicle) -> Unit
) : ListAdapter<Vehicle, VehiclesAdapter.VehicleViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val binding = ItemVehicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VehicleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicle = getItem(position))
    }

    inner class VehicleViewHolder(
        private val binding: ItemVehicleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(vehicle: Vehicle) {
            binding.vin.text = binding.root.context.getString(R.string.vin_template, vehicle.vin)
            binding.makeAndModel.text = vehicle.makeAndModel
            binding.root.setOnClickListener { onItemClicked(vehicle) }
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<Vehicle>() {
        override fun areItemsTheSame(old: Vehicle, new: Vehicle): Boolean = old.vin == new.vin
        override fun areContentsTheSame(old: Vehicle, new: Vehicle): Boolean = old == new
    }
}
