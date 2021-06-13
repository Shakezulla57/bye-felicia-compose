package game

import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*
import kotlin.concurrent.schedule

object Countdown {
    val state = MutableStateFlow<CountdownState>(CountdownState.Idle)

    private var timer: TimerTask? = null

    private const val START = 45
    private var remainingTime = START

    fun start(shouldReset: Boolean = false) {
        if (shouldReset) {
            remainingTime = START
        }

        state.value = CountdownState.InProgress(remainingTime)

        timer = Timer().schedule(0, 1000) {
            remainingTime -= 1
            if (remainingTime == 0) {
                timer?.cancel()
                state.value = CountdownState.Done
            } else {
                state.value = CountdownState.InProgress(remainingTime)
            }
        }
    }

    fun stop() {
        timer?.cancel()
        state.value = CountdownState.Paused(remainingTime)
    }

    fun reset() {
        timer?.cancel()
        start(true)
    }

    fun resume() {
        timer?.cancel()
        start()
    }
}


sealed class CountdownState {
    object Idle : CountdownState()
    object Done : CountdownState()
    data class Paused(val remainingTime: Int) : CountdownState()
    data class InProgress(val remainingTime: Int) : CountdownState()
}