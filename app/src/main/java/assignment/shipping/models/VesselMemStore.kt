package assignment.shipping.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class VesselMemStore : VesselStore {

    val vessels = ArrayList<VesselModel>()

    override fun findAll(): List<VesselModel> {
        return vessels
    }

    override fun create(vessel: VesselModel) {
        vessel.id = getId()
        vessels.add(vessel)
        logAll()
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
            logAll()
        }
    }

    override fun delete(vessel: VesselModel) {
        TODO()
    }

    override fun deleteAll(vessel: VesselModel) {
        TODO("Not yet implemented")
    }

    override fun findOne(id: Long): VesselModel? {
        TODO()
    }

    override fun findByName(name: String): VesselModel? {
        TODO()
    }

    private fun logAll() {
        vessels.forEach { i("$it") }
    }
}