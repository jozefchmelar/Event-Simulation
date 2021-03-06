package aircarrental.entities

import aircarrental.AirCarRentalState
import core.StatisticQueue
import core.StatisticPriorityQueue

enum class Buildings { TerminalOne, TerminalTwo, AirCarRental }

data class Terminal(
    var arrivals: Int = 0,
    var busCount: Int,
    val description: Buildings,
    val queue: StatisticQueue<Customer, AirCarRentalState>
){}

data class CarRental(
    val description: Buildings,
    val queue: StatisticPriorityQueue<Customer, AirCarRentalState>,
    val employees: List<Employee>,
    var serviceTotalWaitTime: Double = 0.0,
    var served: Int = 0

) {
    fun avgWaitTimeForService() :Double = serviceTotalWaitTime / (served*1.0)
}

data class Employee(
    val id:Int,
    var isBusy: Boolean = false,
    var serving: Customer? = null
) {
    fun isNotBusy() = !isBusy
}




/**
 * @return distance to next building in meters
 */
fun Buildings.distanceToNext() = when (this) {
    Buildings.TerminalOne -> 500.0
    Buildings.TerminalTwo -> 2500.0
    Buildings.AirCarRental -> 6400.0
}

fun Buildings.nextStop() = when (this) {
    Buildings.TerminalOne -> Buildings.TerminalTwo
    Buildings.TerminalTwo -> Buildings.AirCarRental
    Buildings.AirCarRental -> Buildings.TerminalOne
}
