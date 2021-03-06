package assignment.shipping.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import assignment.shipping.R
import assignment.shipping.databinding.ActivityShippingBinding
import assignment.shipping.helpers.showImagePicker
import assignment.shipping.main.MainApp
import assignment.shipping.models.Location
import assignment.shipping.models.VesselModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import timber.log.Timber.i


class VesselActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShippingBinding
    var vessel = VesselModel()
    lateinit var app: MainApp

    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
//    private lateinit var database : DatabaseReference

    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("Vessel Activity started.")

        if (intent.hasExtra("vessel_edit")) {
            edit = true
            vessel = intent.extras?.getParcelable("vessel_edit")!!
            binding.vesselName.setText(vessel.name)
            binding.arrivalTime.setText(vessel.arrivalTime.toString())
            binding.departureTime.setText(vessel.departureTime.toString())
            binding.draught.setText(vessel.draught.toString())
            binding.btnAdd.setText(R.string.save_vessel)
            Picasso.get()
                .load(vessel.image)
                .into(binding.vesselImage)
            if (vessel.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_vessel_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            vessel.name = binding.vesselName.text.toString()
            vessel.arrivalTime = binding.arrivalTime.text.toString().toLong()
            vessel.departureTime = binding.departureTime.text.toString().toLong()
            vessel.draught = binding.draught.text.toString().toDouble()

            if (vessel.name.isEmpty()) {
                Snackbar.make(it,R.string.enter_vessel_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.vessels.update(vessel.copy())
                } else {
                    app.vessels.create(vessel.copy())
                }
            }
            i("Vessel Added: $vessel")
            setResult(RESULT_OK)
            setContentView(binding.root)
            finish()

//            val database = FirebaseDatabase.getInstance().getReference("/models/VesselModel")
//            val vessels = VesselModel(vessel.id, vessel.name, vessel.arrivalTime, vessel.departureTime, vessel.draught)
//
//            database.child(vessel.name).setValue(vessels).addOnSuccessListener {
//
//                binding.vesselName.text.clear()
//                binding.arrivalTime.text.clear()
//                binding.departureTime.text.clear()
//                binding.draught.text.clear()
//
//                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
//
//            }.addOnFailureListener {
//
//                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
//
//            }
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.vesselLocation.setOnClickListener {
            val location = Location(52.26697905691473, -7.03113731197637, 15f)
            if (vessel.zoom != 0f) {
                location.lat =  vessel.lat
                location.lng = vessel.lng
                location.zoom = vessel.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_vessel, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                app.vessels.delete(vessel)
                finish()
            }
            R.id.item_back -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }



    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            vessel.image = result.data!!.data!!
                            Picasso.get()
                                .load(vessel.image)
                                .into(binding.vesselImage)
                            binding.chooseImage.setText(R.string.change_vessel_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            vessel.lat = location.lat
                            vessel.lng = location.lng
                            vessel.zoom = location.zoom
                        }
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}

