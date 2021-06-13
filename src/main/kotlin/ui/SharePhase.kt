import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import game.Player

@Composable
fun SharePhase(
    players: List<Player>,
    onNextRound: () -> Unit
) {
    Column {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(players.toList(), key = { it.name }) {
                PlayerStatus(it)
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onNextRound) {
            Text("Next Round")
        }
    }
}

@Composable
fun PlayerStatus(
    player: Player
) {
    Column {
        Row {
            Switch(checked = !player.isEliminated, onCheckedChange = { game.Players.updateStatus(player, !it) })
            Spacer(Modifier.width(8.dp))
            Text(
                player.name,
                textDecoration = if (player.isEliminated) TextDecoration.LineThrough else TextDecoration.None
            )
        }
    }
}