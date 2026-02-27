package com.huhx0015.rpgcharacterdirectory.data

import android.content.Context
import android.util.Log
import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter
import com.huhx0015.rpgcharacterdirectory.model.RPGGame
import com.huhx0015.rpgcharacterdirectory.ui.CharacterListViewModel
import com.huhx0015.rpgcharacterdirectory.utils.JsonUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CharacterDataRepository {

  // Stores the total character list data after deserializing all JSON files.
  private val totalCharacterListData: MutableMap<Int, List<RPGCharacter>> = mutableMapOf()

  // StateFlow objects observed by the ViewModel layer to send up to the View layer.
  val characterListDataFlow: MutableStateFlow<List<RPGCharacter>> =
    MutableStateFlow(emptyList())
  val leaderListDataFlow: MutableStateFlow<Map<Int, RPGCharacter>> =
    MutableStateFlow(emptyMap())
  val gameListDataFlow: MutableStateFlow<Map<Int, RPGGame>> =
    MutableStateFlow(emptyMap())

  companion object {
    private const val FILE_JSON_LEADERS = "leaders.json"
    private val TAG = CharacterListViewModel::class.java.simpleName
  }

  // loadAllJsonFileData(): Loads all JSON files from the `assets` folder and deserializes the data
  // into data objects, which will then update all StateFlows in this repository.
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
        // Maps `List<RPGCharacter>` to `Map<Int, RPGCharacter>`.
        leaderMap = characterList.associateBy { character ->
          character.gameId
        }
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

    // Updates the totalCharacterListData with the data from characterListMap.
    totalCharacterListData.clear()
    totalCharacterListData.putAll(characterListMap)

    // Updates the StateFlow objects, which will be consumed by the view model layer when updated.
    characterListDataFlow.update {
      Log.d(TAG, "Updating the characterListDataFlow: ${characterListMap.size}")
      characterListMap.values.flatten()
    }
    leaderListDataFlow.update {
      Log.d(TAG, "Updating the leaderListDataFlow: ${leaderMap.size}")
      leaderMap
    }
    gameListDataFlow.update {
      Log.d(TAG, "Updating the gameListDataFlow: ${gameMap.size}")
      gameMap
    }
  }

  // loadUpdatedCharacterList(): Updates the characterListDataFlow based on the specified gameId.
  // When a gameId is specified, the characterListDataFlow is updated with the character list
  // associated with the gameId. If no gameId is provided, the
  fun loadUpdatedCharacterList(gameId: Int?) {
    val characterListData = gameId?.let { totalCharacterListData[gameId] }
      ?: totalCharacterListData.values.flatten()
    // Updates the StateFlow objects, which will be consumed by the view model layer when updated.
    characterListDataFlow.update {
      Log.d(TAG, "Updating the characterListDataFlow with gameId: $gameId and size: ${characterListData.size}")
      characterListData
    }
  }
}