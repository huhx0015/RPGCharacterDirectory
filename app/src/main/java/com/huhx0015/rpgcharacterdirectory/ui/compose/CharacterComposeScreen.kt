package com.huhx0015.rpgcharacterdirectory.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter

@Composable
fun CharacterComposeScreen(characterList: List<RPGCharacter>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .background(Color.White) // Sets background color to white.
      .padding(16.dp)
      .statusBarsPadding() // Android 15+: Edge-to-edge padding to prevent overlap with the status bar.
      .navigationBarsPadding() // Android 15+: Edge-to-edge padding to prevent overlap with the navigation bar.
  ) {
    // Compose UI for each item in characterList.
    items(characterList) { rpgCharacter ->
      CharacterComposeRow(character = rpgCharacter)
      Spacer(modifier = Modifier.height(4.dp))
    }
  }
}

@Composable
private fun CharacterComposeRow(character: RPGCharacter) {
  Card(modifier = Modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        Text(character.name)
        Text(character.characterClass)
        Text(character.game)
      }
    }
  }
}

@Composable
@Preview
private fun CharacterComposeScreenPreview() {
  CharacterComposeScreen(
    characterList = listOf(
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
  )
}