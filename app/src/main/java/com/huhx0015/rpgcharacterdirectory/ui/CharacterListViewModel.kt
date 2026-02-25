package com.huhx0015.rpgcharacterdirectory.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter
import com.huhx0015.rpgcharacterdirectory.model.RPGGame
import com.huhx0015.rpgcharacterdirectory.utils.JsonUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CharacterListViewModel: ViewModel() {

  val stateFlow = MutableStateFlow(CharacterListState())

  companion object {
    private const val FILE_JSON_LEADERS = "leaders.json"
    private val TAG = CharacterListViewModel::class.java.simpleName
  }

  fun loadAllJsonFileData(context: Context) {
    // Stores the entire character list data.
    val characterListMap: MutableMap<Int, List<RPGCharacter>> = mutableMapOf()

    // Stores the leader list data from `leaders.json`.
    var leaderMap: Map<Int, RPGCharacter> = mutableMapOf()

    // Stores the unique set of games from the character list data.
    val gameSet: MutableSet<RPGGame> = mutableSetOf()

    val jsonFileList = JsonUtil.getJsonFileNameListFromAssetFolder(context)
    jsonFileList.forEach { jsonFileName ->
      // Deserializes the JSON data for the specified jsonFileName into a List<RPGCharacter>. If the
      // JSON file is invalid or does not conform to the expected format for RPGCharacter, move to
      // next JSON file to process.
      val characterList = JsonUtil.getJsonCharacterDataFromAsset(context, jsonFileName) ?: return

      // Retrieves the name of the game from the characterList.
      val game = characterList.firstNotNullOf { character ->
        RPGGame(gameId = character.gameId, gameName = character.game)
      }

      // If jsonFileName is `leaders.json`, this data is handled separately.
      if (jsonFileName == FILE_JSON_LEADERS) {
        leaderMap = characterList.associateBy { it.id } // Maps `List<RPGCharacter>` to `Map<Int, RPGCharacter>`.
        Log.d(TAG, "Leader character data found, size is: ${characterListMap.size}")
      } else {
        // Adds game to the gameSet.
        gameSet.add(game)
        characterListMap[game.gameId] = characterList
      }
    }

    // Converts the gameSet into a gameMap, where the gameId is the key and gameName is the value.
    val gameMap = gameSet.associateBy { it.gameId }

    Log.d(TAG, "Total character data size: ${characterListMap.size}")

    // Updates the stateFlow, which will be consumed by the view layer when updated.
    stateFlow.update {
      it.copy(
        characterListMap = characterListMap,
        leaderMap = leaderMap,
        gameMap = gameMap
      )
    }
  }
}