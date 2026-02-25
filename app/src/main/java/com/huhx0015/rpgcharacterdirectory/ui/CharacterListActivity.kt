package com.huhx0015.rpgcharacterdirectory.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.huhx0015.rpgcharacterdirectory.ui.compose.CharacterComposeScreen

class CharacterListActivity: ComponentActivity() {

  private val viewModel: CharacterListViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    renderCompose()
    loadData()
  }

  private fun loadData() {
    viewModel.loadAllJsonFileData(this)
  }

  private fun renderCompose() {
    setContent {
      val state = viewModel.stateFlow.collectAsState()
      CharacterComposeScreen(
        characterList = state.value.allCharacterList(),
        gameList = state.value.getGameNameList()
      )
    }
  }
}