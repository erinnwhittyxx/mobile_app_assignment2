package assignment.shipping.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import assignment.shipping.R
import assignment.shipping.adapters.VesselAdapter
import assignment.shipping.adapters.VesselListener
import assignment.shipping.databinding.ActivityVesselListBinding
import assignment.shipping.main.MainApp
import assignment.shipping.models.VesselModel

class VesselListActivity : AppCompatActivity(), VesselListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityVesselListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVesselListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadVessels()
        registerRefreshCallback()
        registerMapCallback()

        binding.deleteAll.setOnClickListener{
            app.vessels.deleteAll(VesselModel())
                val launcherIntent = Intent(this, VesselListActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, VesselActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, VesselMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onVesselClick(vessel: VesselModel) {
        val launcherIntent = Intent(this, VesselActivity::class.java)
        launcherIntent.putExtra("vessel_edit", vessel)
        refreshIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadVessels() }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }

    private fun loadVessels() {
        showVessels(app.vessels.findAll())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showVessels (vessels: List<VesselModel>) {
        binding.recyclerView.adapter = VesselAdapter(vessels, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}
