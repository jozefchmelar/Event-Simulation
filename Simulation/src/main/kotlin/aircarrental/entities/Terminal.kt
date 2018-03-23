package aircarrental.entities

import aircarrental.AirCarRentalState
import aircarrental.entities.Customer
import core.StatisticQueue


enum class Buildings { TerminalOne, TerminalTwo, AirCarRental }


data class Terminal(
    var arrivals:Int = 0,
    val description: Buildings,
    val queue: StatisticQueue<Customer, AirCarRentalState>
)

data class CarRental(
    val description: Buildings,
    val queue: StatisticQueue<Customer, AirCarRentalState>,
    val employees: List<Employee>
)

data class Employee(var isBusy: Boolean = false) {
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

