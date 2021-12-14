package assignment.shipping.models

import android.content.Context
import android.net.Uri
import assignment.shipping.helpers.exists
import assignment.shipping.helpers.read
import assignment.shipping.helpers.write
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "vessels.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<VesselModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class VesselJSONStore(private val context: Context) : VesselStore {

    var vessels = mutableListOf<VesselModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<VesselModel> {
        logAll()
        return vessels
    }

    override fun create(vessel: VesselModel) {
        vessel.id = generateRandomId()
        vessels.add(vessel)
        serialize()
    }


    override fun update(vessel: VesselModel) {
        val foundVessel: VesselModel? = vessels.find { p -> p.id == vessel.id }
        if (foundVessel != null) {
            foundVessel.name = vessel.name
            foundVessel.arrivalTime = vessel.arrivalTime
            foundVessel.departureTime = vessel.departureTime
            foundVessel.draught = vessel.draught
            foundVessel.image = vessel.image
            foundVessel.lat = vessel.lat
            foundVessel.lng = vessel.lng
            foundVessel.zoom = vessel.zoom
            serialize()
        }
    }

    override fun delete(vessel: VesselModel) {
        vessels.remove(vessel)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(vessels, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        vessels = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        vessels.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}