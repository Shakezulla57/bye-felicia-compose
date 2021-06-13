import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import game.Countdown
import game.Game
import game.GameState

fun main() = Window {
    Game.initializeGame()

    val viewState = Game.viewState.collectAsState()

    MaterialTheme {
        Column(Modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Bye Felicia", style = MaterialTheme.typography.h3, textAlign = TextAlign.Center)
            Spacer(Modifier.height(32.dp))

            when (viewState.value.gameState) {
                GameState.Build -> BuildPhase(viewState.value.players, onBegin = {
                    Game.startGame()
                })
                GameState.Answer -> {
                    CountDown(
                        viewState.value.countDown,
                        onResume = { Countdown.resume() },
                        onStop = { Countdown.stop() },
                        onReset = { Countdown.reset() }
                    )
                }
                GameState.Share -> SharePhase(viewState.value.players, onNextRound = game.Players::nextRound)
            }
        }
    }
}