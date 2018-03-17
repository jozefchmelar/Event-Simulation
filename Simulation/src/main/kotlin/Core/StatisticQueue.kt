package Core

import java.util.*

interface Statistical {
    var arrivedToSystem: Double
}

class StatisticQueue<T : Statistical, out S : State>(private val simCore: Simulation<S>) {

    private val queue: Queue<T> = LinkedList<T>()
    private var lastChange = 0.0
    private var totalTime = 0.0
    private var weightTime = 0.0
    private var totalWaitTime = 0.0
    private var served = 0.0

    private fun beforeChange() {
        weightTime += (simCore.currentTime - lastChange) * queue.size
        totalTime += (simCore.currentTime - lastChange)
    }

    fun push(t: T) {
        beforeChange()
        queue.add(t)
        lastChange = simCore.currentTime
    }

    fun pop(): T {
        beforeChange()
        val popped = queue.poll()
        lastChange = simCore.currentTime
        totalWaitTime += simCore.currentTime - popped.arrivedToSystem
        served++
        return popped
    }

    fun averageWaitTime() = totalWaitTime / served

    fun averageSize() = weightTime / totalTime

    fun isEmpty() = queue.isEmpty()

    fun isNotEmpty() = queue.isNotEmpty()
}