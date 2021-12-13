package assignment.shipping.main

import android.app.Application
import assignment.shipping.models.VesselMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val vessels = VesselMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Vessel App started")
    }
}
