package AirCarRental

import AirCarRental.Entities.Building
import AirCarRental.Entities.MiniBus
import Core.Simulation
import Core.State
import java.util.*

//data class SimTate(val nvm: Long = 5, override var running: Boolean) : State

//class AirCarRentalSimulation(
//        private val numberOFMiniBuses: Int,
//        private val employees: Int,
//        maxSimTime: Long
//) : Simulation<SimTate>(maxSimTime) {
//
//    private val rndService    = Random(rndSeed.nextLong())
//
//    private val rndGetOnBus   = Random(rndSeed.nextLong())
//
//    private val rndGetFromBus = Random(rndSeed.nextLong())
//
//    val busses = Collections.nCopies(numberOFMiniBuses,MiniBus(location = Building.TerminalOne))
//
//
//    override fun toState(simTime: Long) = SimTate(nvm = simTime, running = isRunning)
//
//}