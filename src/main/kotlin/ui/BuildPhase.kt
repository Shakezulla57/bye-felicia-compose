import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import game.Player
import ui.Player

@Composable
fun BuildPhase(
    players: List<Player>,
    onBegin: () -> Unit
) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Players(players, onPlayerAdded = {
            game.Players.add(Player(it))
        })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBegin) {
            Text("Begin")
        }
    }
}

@Composable
fun Players(
    players: List<Player>,
    onPlayerAdded: (String) -> Unit
) {
    Column() {
        var name by remember { mutableStateOf("") }
        OutlinedTextField(
            name,
            singleLine = true,
            onValueChange = {
                name = it
            },
            label = { Text("Add a game.Player") },
            modifier = Modifier.fillMaxWidth().onKeyEvent {
                if (it.key == Key.Enter) {
                    onPlayerAdded(name)
                    name = ""
                    false
                } else {
                    true
                }
            })
        Text("${players.count()} game.Player(s)", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(players.toList(), key = { it.name }) {
                Player(
                    it,
                    game.Players::remove
                )
                Divider()
            }
        }
    }
}