package assignment.shipping.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import assignment.shipping.R
import assignment.shipping.databinding.ActivityVesselListBinding
import assignment.shipping.databinding.CardVesselBinding
import assignment.shipping.main.MainApp
import assignment.shipping.models.VesselModel

class VesselListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityVesselListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVesselListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = VesselAdapter(app.vessels)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, VesselActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

class VesselAdapter constructor(private var vessels: List<VesselModel>) :
    RecyclerView.Adapter<VesselAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardVesselBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val vessel = vessels[holder.adapterPosition]
        holder.bind(vessel)
    }

    override fun getItemCount(): Int = vessels.size

    class MainHolder(private val binding : CardVesselBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vessel: VesselModel) {
            binding.vesselName.text = vessel.name
            binding.description.text = vessel.arrivalTime
        }
    }
}
