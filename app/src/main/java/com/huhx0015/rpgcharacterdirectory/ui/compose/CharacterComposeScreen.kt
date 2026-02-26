package com.huhx0015.rpgcharacterdirectory.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huhx0015.rpgcharacterdirectory.R
import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter
import com.huhx0015.rpgcharacterdirectory.model.RPGGame
import com.huhx0015.rpgcharacterdirectory.ui.CharacterListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterComposeScreen(
  state: CharacterListState,
  filterButtonClickAction: ((Int?) -> Unit)
) {
  val selectedGameId = state.selectedGameId
  val characterList = selectedGameId?.let { gameId ->
    state.getCharacterListByGameId(gameId = gameId)
  } ?: state.allCharacterList()
  val leaderMap = state.leaderMap
  val gameList = state.getGameNameList()

  // Used for retaining scroll behavior for this screen.
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

  // Top app bar and contents.
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = Color.Black,
          titleContentColor = Color.Black,
        ),
        title = {
          Text(
            text = "RPG Character Directory",
            color = Color.White,
          )
        }
      )
    },
  ) { innerPadding ->
    CharacterComposeContent(
      characterList = characterList,
      leaderMap = leaderMap,
      gameList = gameList,
      filterButtonClickAction = filterButtonClickAction,
      innerPadding = innerPadding
    )
  }
}

@Composable
private fun CharacterComposeContent(
  characterList: List<RPGCharacter>,
  leaderMap: Map<Int, RPGCharacter>,
  gameList: Set<RPGGame>,
  filterButtonClickAction: ((Int?) -> Unit),
  innerPadding: PaddingValues
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(innerPadding)
  ) {
    CharacterComposeFilterButtonRow(
      gameList = gameList,
      filterButtonClickAction = filterButtonClickAction
    )
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color.White) // Sets background color to white.
        .padding(16.dp)
    ) {
      // Compose UI for each item in characterList.
      items(characterList) { rpgCharacter ->
        CharacterComposeRow(
          character = rpgCharacter,
          leader = leaderMap[rpgCharacter.gameId]
        )
        Spacer(modifier = Modifier.height(4.dp))
      }
    }
  }
}

@Composable
private fun CharacterComposeFilterButtonRow(
  gameList: Set<RPGGame>,
  filterButtonClickAction: ((Int?) -> Unit)
) {
  LazyRow(
    modifier = Modifier
      .fillMaxWidth()
      .padding(12.dp)
  ) {
    item {
      // Fixed filter buton to show all characters when tapped.
      Button(
        modifier = Modifier.padding(end = 4.dp),
        onClick = { filterButtonClickAction.invoke(null) }
      ) {
        Text(text = stringResource(R.string.all_button_text))
      }
    }
    items(gameList.toList()) { game ->
      Button(
        modifier = Modifier,
        onClick = { filterButtonClickAction.invoke(game.gameId) }
      ) {
        Text(text = game.gameName)
      }
      Spacer(modifier = Modifier.width(4.dp))
    }
  }
}

@Composable
private fun CharacterComposeRow(
  character: RPGCharacter,
  leader: RPGCharacter?
) {
  Card(modifier = Modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(
          text = character.name,
          color = Color.Black,
          fontWeight = FontWeight.Bold
        )
        Text(text = "Class: " + character.characterClass)
        Text(text = "Game: " + character.game)
        leader?.let { Text(text = "Leader: " + leader.name) }
      }
    }
  }
}

@Composable
@Preview
private fun CharacterComposeScreenPreview() {
  CharacterComposeScreen(
    state = CharacterListState(
      characterListMap = mapOf(
        1 to listOf(
          RPGCharacter(
            id = 1,
            name = "Crono",
            characterClass = "Time Traveler",
            game = "Chrono Trigger",
            gameId = 1234
          ),
          RPGCharacter(
            id = 2,
            name = "Lucca",
            characterClass = "Time Traveler",
            game = "Chrono Trigger",
            gameId = 1234
          )
        )
      ),
      gameMap = mapOf(
        1 to RPGGame(
          gameId = 1,
          gameName = "Final Fantasy II"
        ),
        2 to RPGGame(
          gameId = 2,
          gameName = "Final Fantasy IV"
        ),
        3 to RPGGame(
          gameId = 3,
          gameName = "Final Fantasy IX"
        )
      ),
    ),
    filterButtonClickAction = {}
  )
}