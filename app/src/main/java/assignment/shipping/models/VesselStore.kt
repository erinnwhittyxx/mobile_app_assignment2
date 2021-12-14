package assignment.shipping.models

interface VesselStore {
    fun findAll(): List<VesselModel>
    fun create(vessel: VesselModel)
    fun update(vessel: VesselModel)
    fun delete(vessel: VesselModel)
}