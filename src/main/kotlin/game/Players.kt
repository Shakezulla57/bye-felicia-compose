package game

import kotlinx.coroutines.flow.MutableStateFlow

object Players {

    val defaultEntries = listOf(
        Player("Bob"),
        Player("Tom")
    )

    val players = MutableStateFlow(defaultEntries)

    fun remove(player: Player) {
        val updated = players.value.toMutableList()
        updated.remove(player)
        players.value = updated
    }

    fun add(player: Player) {
        if (player.name.isNotBlank()) {
            val updated = players.value.toMutableList()
            updated.add(player)
            players.value = updated
        }
    }

    fun updateStatus(player: Player, isEliminated: Boolean) {
        val updated = players.value.map {
            if (player == it) {
                it.copy(isEliminated = isEliminated)
            } else {
                it
            }
        }
        players.value = updated
    }

    fun nextRound() {
        val current = players.value.toMutableList()
        players.value = emptyList()
        players.value = current.shuffled().sortedByDescending { it.isEliminated }
    }

}

data class Player(
    val name: String,
    val isEliminated: Boolean = false
)