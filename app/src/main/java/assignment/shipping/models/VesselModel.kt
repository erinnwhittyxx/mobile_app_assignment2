package assignment.shipping.models

data class VesselModel(var id: Long = 0,
                       var name: String = "",
                       var arrivalTime: String = "",
                       var draught: Double = 0.0)