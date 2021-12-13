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
        var foundVessel: VesselModel? = vessels.find { p -> p.id == vessel.id }
        if (foundVessel != null) {
            foundVessel.name = vessel.name
            foundVessel.arrivalTime = vessel.arrivalTime
            foundVessel.image = vessel.image
            logAll()
        }
    }

    private fun logAll() {
        vessels.forEach { i("$it") }
    }
}