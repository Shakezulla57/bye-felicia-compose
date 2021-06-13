package game

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

object Game {

    val viewState = MutableStateFlow<ViewState>(ViewState(players = Players.defaultEntries))

    fun initializeGame() {
        Players.players.onEach {
            viewState.value = viewState.value.copy(players = it)
        }.launchIn(GlobalScope)

        Countdown.state.onEach {
            if (it is CountdownState.Done) {
                viewState.value = viewState.value.copy(countDown = it, gameState = GameState.Share)
            } else {
                viewState.value = viewState.value.copy(countDown = it)
            }
        }.launchIn(GlobalScope)
    }

    fun startGame() {
        viewState.value = viewState.value.copy(gameState = GameState.Answer)
        Countdown.start()
    }

}

data class ViewState(
    val gameState: GameState = GameState.Build,
    val players: List<Player>,
    val countDown: CountdownState = CountdownState.Idle
)
