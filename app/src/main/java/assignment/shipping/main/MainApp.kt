package assignment.shipping.main

import android.app.Application
import assignment.shipping.models.VesselJSONStore
import assignment.shipping.models.VesselStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var vessels: VesselStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        vessels = VesselJSONStore(applicationContext)
        i("Vessel Logger started")
    }
}
