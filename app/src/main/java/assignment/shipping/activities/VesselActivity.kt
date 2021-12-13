package assignment.shipping.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        app = application as MainApp
        i("Vessel Activity Started")
        binding.btnAdd.setOnClickListener() {
            vessel.name = binding.vesselName.text.toString()
            vessel.arrivalTime = binding.arrivalTime.text.toString()
            if (vessel.name.isNotEmpty()) {
                app.vessels.add(vessel.copy())
                i("Vessel Added: ${vessel}")
                for (i in app.vessels.indices) {
                    i("Vessel[$i]:${this.app.vessels[i]}")
                }
                setResult(RESULT_OK)
                finish()
            } else {
                Snackbar.make(it, "Please Enter a Vessel", Snackbar.LENGTH_LONG)
                    .show()
            }
        }


    }
}