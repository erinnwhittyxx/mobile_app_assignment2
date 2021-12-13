package assignment.shipping.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VesselModel(var id: Long = 0,
                       var name: String = "",
                       var arrivalTime: String = "",
                       var draught: Double = 0.0,
                       var image: Uri = Uri.EMPTY) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable