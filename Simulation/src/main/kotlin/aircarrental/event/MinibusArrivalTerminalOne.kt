package aircarrental.event

import aircarrental.entities.*

class MinibusArrivalTerminalOne(private val minibus: Minibus, time: Double) : AcrEvent(time) {

    override fun execute() = with(core) {
        if (terminalOne.queue.isNotEmpty())
            plan(EnterToMinibus(
                minibus = minibus,
                terminal = terminalOne,
                time = currentTime + rndTimeToEnterBus.next()
            ))
        else
            plan(MinibusGoTo(
                minibus = minibus,
                source = Buildings.TerminalOne,
                destination = Buildings.TerminalTwo,
                time = currentTime
            ))
    }

    override fun toString() = "Minibus ${minibus.id} ${super.toString()}"

}