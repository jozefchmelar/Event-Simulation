package Core

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.launch
import java.util.*
import java.util.concurrent.PriorityBlockingQueue

abstract class SimCore<S : State>(val maxSimTime: Double, val replications: Int) {

    val currentReplicationChannel = Channel<S>()
    val afterReplicationChannel = Channel<List<S>>()

    private val timeLine = PriorityBlockingQueue<Event>()
    private val replicationStates = mutableListOf<S>()
    private val oneSecond = 1/60.0 //1.0
    private var runs = 0
    private var isWatched = true

    var sleepTime = 1000L

    var currentTime = 0.0
        private set(value) {
            field = value
        }

    var speed = oneSecond //* 60

    private var isRunning = true

    var stop = false
        private set(value) {
            field = value
        }

     fun start() = launch {
        beforeSimulation()
        repeat(replications) {
            beforeReplication()
            simulate()
            replicationStates += toState(runs, currentTime)
            if (!stop)
                afterReplicationChannel.send(replicationStates)
            clear()
            afterReplication()
        }
        afterSimulation()
        isRunning = false
    }

    private suspend fun simulate() {
         if (isWatched())
             planTick()

        while (shouldSimulate()) {
            if (isSimulationRunning()) {
                val currentEvent = timeLine.poll()
                currentTime = currentEvent.occurrenceTime
                currentEvent.execute()
                val state = toState(runs++, currentTime)
                if (isWatched())
                    currentReplicationChannel.send(state)
            }

            if (stop) {
                val state = toState(runs++, currentTime)
                if (isWatched())
                    currentReplicationChannel.send(state)
            }

        }
    }

    fun planTick() {
        val tick = Tick(currentTime + speed,this@SimCore)
        timeLine.add(tick)
    }

    open fun plan(event: Event) {
        if (event.occurrenceTime >= currentTime)
            timeLine.add(event)
        else
            throw IllegalStateException("Time travel")
    }


    private fun isSimulationRunning() = isRunning && !stop
    private fun shouldSimulate() = currentTime < maxSimTime && timeLine.isNotEmpty()

    protected abstract fun afterReplication()
    protected abstract fun beforeReplication()
    protected abstract fun afterSimulation()
    protected abstract fun beforeSimulation()
    protected abstract fun toState(run: Int, simTime: Double): S
    protected val rndSeed = Random()

    open fun clear() {
        currentTime = 0.0
        runs = 0
        isRunning = true
        stop = false
    }

    fun pause() {
        isRunning = false
    }

    fun resume() {
        isRunning = true
    }

    fun stop() {
        stop = true
    }

    fun isWatched() = isWatched

    fun startWatching() {
        isWatched = true
        planTick()
    }

    fun stopWatching() {
        isWatched = false
    }


}

