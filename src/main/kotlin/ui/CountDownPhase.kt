import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import game.CountdownState

@Composable
fun CountDown(
    countdownState: CountdownState,
    onStop: () -> Unit,
    onResume: () -> Unit,
    onReset: () -> Unit
) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (countdownState is CountdownState.InProgress) {
            Text(countdownState.remainingTime.toString(), style = MaterialTheme.typography.h1)
            Spacer(Modifier.height(16.dp))
            Row {
                Button(onClick = onStop) {
                    Text("Stop")
                }
                Spacer(Modifier.width(8.dp))
                TextButton(onClick = onReset) {
                    Text("Reset")
                }
            }
        }

        if (countdownState is CountdownState.Paused) {
            Text(countdownState.remainingTime.toString(), style = MaterialTheme.typography.h1)
            Spacer(Modifier.height(16.dp))
            Row {
                Button(onClick = onResume) {
                    Text("Resume")
                }
                Spacer(Modifier.width(8.dp))
                TextButton(onClick = onReset) {
                    Text("Reset")
                }
            }
        }
    }
}