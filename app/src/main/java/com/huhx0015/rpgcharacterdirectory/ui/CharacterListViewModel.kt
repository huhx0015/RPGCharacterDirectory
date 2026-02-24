package com.huhx0015.rpgcharacterdirectory.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter
import com.huhx0015.rpgcharacterdirectory.utils.JsonUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CharacterListViewModel: ViewModel() {

  val stateFlow = MutableStateFlow(CharacterListState())

  companion object {
    private const val FILE_JSON_FINAL_FANTASY_II = "final_fantasy_ii.json"
    private const val FILE_JSON_LEADERS = "leaders.json"
    private val TAG = CharacterListViewModel::class.java.simpleName
  }

  fun loadAllCharacterDate(context: Context) {
    val characterListMap: MutableMap<String, List<RPGCharacter>> = mutableMapOf()
    val ff2CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_II
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_II] = ff2CharacterList
    Log.d(TAG, "Character data size: ${characterListMap.size}")

    val leaderMap: Map<Int, RPGCharacter>
    val leadersList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_LEADERS
    ) ?: emptyList()
    leaderMap = leadersList.associateBy { it.id } // Maps `List<RPGCharacter>` to `Map<Int, RPGCharacter>`.
    Log.d(TAG, "Leader data size: ${characterListMap.size}")

    // Updates the stateFlow, which will be consumed by the view layer when updated.
    stateFlow.update {
      it.copy(
        characterListMap = characterListMap,
        leaderMap = leaderMap
      )
    }
  }
}