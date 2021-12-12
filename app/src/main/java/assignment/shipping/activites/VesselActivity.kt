package assignment.shipping.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import assignment.shipping.databinding.ActivityShippingBinding
import assignment.shipping.models.VesselModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import timber.log.Timber.i


class VesselActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShippingBinding
    var vessel = VesselModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Vessel Activity started")

        binding.btnAdd.setOnClickListener() {
            vessel.title = binding.vesselTitle.text.toString()
            if (vessel.title.isNotEmpty()) {
                i("Vessel Added: $vessel.title")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a Vessel", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}