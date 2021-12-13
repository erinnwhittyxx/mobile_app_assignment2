package assignment.shipping.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import assignment.shipping.R
import assignment.shipping.databinding.ActivityShippingBinding
import assignment.shipping.main.MainApp
import assignment.shipping.models.VesselModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber.i


class VesselActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShippingBinding
    var vessel = VesselModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Vessel Activity Started")

        if (intent.hasExtra("vessel_edit")) {
            vessel = intent.extras?.getParcelable("vessel_edit")!!
            binding.vesselName.setText(vessel.name)
            binding.arrivalTime.setText(vessel.arrivalTime)
        }

        binding.btnAdd.setOnClickListener() {
            vessel.name = binding.vesselName.text.toString()
            vessel.arrivalTime = binding.arrivalTime.text.toString()
            if (vessel.name.isNotEmpty()) {
                app.vessels.create(vessel.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

