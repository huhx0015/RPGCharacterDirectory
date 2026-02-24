package com.huhx0015.rpgcharacterdirectory.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter

class CharacterListActivity: ComponentActivity() {

  private val viewModel: CharacterListViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    loadData()
  }

  private fun loadData() {
    val characterList = viewModel.loadJsonData(this)
    characterList?.let {
      renderCompose(characterList = characterList)
    }
  }

  private fun renderCompose(characterList: List<RPGCharacter>) {
    setContent {
      CharacterComposeScreen(characterList = characterList)
    }
  }
}