package assignment.shipping.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import assignment.shipping.databinding.CardVesselBinding
import assignment.shipping.models.VesselModel
import com.squareup.picasso.Picasso

interface VesselListener {
    fun onVesselClick(vessel: VesselModel)
}

class VesselAdapter constructor(private var vessels: List<VesselModel>,
                                   private val listener: VesselListener) :
    RecyclerView.Adapter<VesselAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardVesselBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val vessel = vessels[holder.adapterPosition]
        holder.bind(vessel, listener)
    }

    override fun getItemCount(): Int = vessels.size

    class MainHolder(private val binding : CardVesselBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vessel: VesselModel, listener: VesselListener) {
            binding.vesselName.text = vessel.name
            binding.arrivalTime.text = vessel.arrivalTime
            Picasso.get().load(vessel.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onVesselClick(vessel) }
        }
    }
}
