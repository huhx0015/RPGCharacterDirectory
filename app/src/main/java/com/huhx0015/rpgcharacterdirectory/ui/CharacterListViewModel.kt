package com.huhx0015.rpgcharacterdirectory.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huhx0015.rpgcharacterdirectory.data.CharacterDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterListViewModel: ViewModel() {

  private val characterDataRepository: CharacterDataRepository = CharacterDataRepository()
  val stateFlow: MutableStateFlow<CharacterListState> = MutableStateFlow(CharacterListState())

  init {
    initStateFlowObserver()
  }

  private fun initStateFlowObserver() {
    // Updates the stateFlow when the data from the repository layer is updated, which will be
    // consumed by the view layer when updated. Each flow must be collected in its own coroutine
    // because collectLatest suspends indefinitely for StateFlow (which never completes).
    viewModelScope.launch(Dispatchers.IO) {
      characterDataRepository.characterListDataFlow.collectLatest { characterList ->
        stateFlow.update { it.copy(characterListMap = characterList) }
      }
    }
    viewModelScope.launch(Dispatchers.IO) {
      characterDataRepository.leaderListDataFlow.collectLatest { leaderList ->
        stateFlow.update { it.copy(leaderMap = leaderList) }
      }
    }
    viewModelScope.launch(Dispatchers.IO) {
      characterDataRepository.gameListDataFlow.collectLatest { gameList ->
        stateFlow.update { it.copy(gameMap = gameList) }
      }
    }
  }

  fun loadCharacterListData(context: Context) {
    characterDataRepository.loadAllJsonFileData(context = context)
  }

  fun onGameFilterButtonClicked(gameId: Int?) {
    stateFlow.update { it.copy(selectedGameId = gameId) }
  }
}