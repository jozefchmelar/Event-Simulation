package aircarrental.entities

import aircarrental.AirCarRentalState
import core.StatisticQueue

/**
 * [averageSpeed] is in meters per second
 */
data class Minibus(
    val id: Int,
    val capacity: Int = 12,
    val averageSpeed: Double = 35000.0 / 3600.0,
    val seats: StatisticQueue<Customer, AirCarRentalState>,
    var destination: Buildings,
    var source: Buildings,
    var isInDestination: Boolean = false,
    var leftAt: Double,
    var distanceToDestination: Double
) {

    fun enter(customer: Customer) {
        seats.push(customer)
        if (seats.size() > capacity)
            throw IllegalStateException("Bus is full")
    }

    fun isNotFull() = seats.size() < capacity
    fun isNotEmpty() = seats.isNotEmpty()

    /**
     * @return distance in meters from source
     */
    fun distanceFromSource(currentSimTime: Double): Double {
        return if (!isInDestination)
            Math.abs((currentSimTime - leftAt) * averageSpeed)
        else 0.0
    }

    fun distanceFromDestinatioN(currentSimTime: Double) :Double {
        return if(!isInDestination)
            source.distanceToNext() - distanceFromSource(currentSimTime)
        else 0.0
    }

}

