package com.huhx0015.rpgcharacterdirectory.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.huhx0015.rpgcharacterdirectory.database.AppDatabase
import com.huhx0015.rpgcharacterdirectory.database.entity.toFavoriteCharacter
import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter
import com.huhx0015.rpgcharacterdirectory.model.RPGGame
import com.huhx0015.rpgcharacterdirectory.ui.CharacterListViewModel
import com.huhx0015.rpgcharacterdirectory.utils.JsonUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class CharacterDataRepository {

  // Stores character lists by gameId. LinkedHashMap preserves iteration order for stable flatten.
  private val totalCharacterListData: MutableMap<Int, MutableList<RPGCharacter>> =
    linkedMapOf()

  // Preserves game order for stable "all characters" list when no filter is applied.
  private val gameIdOrder: MutableList<Int> = mutableListOf()

  private lateinit var favoriteCharacterDatabase: AppDatabase

  val characterListDataFlow: MutableStateFlow<List<RPGCharacter>> =
    MutableStateFlow(emptyList())
  val leaderListDataFlow: MutableStateFlow<Map<Int, RPGCharacter>> =
    MutableStateFlow(emptyMap())
  val gameListDataFlow: MutableStateFlow<Map<Int, RPGGame>> =
    MutableStateFlow(emptyMap())

  companion object {
    private const val DB_FAVORITE_CHARACTERS = "favorite-characters"
    private const val FILE_JSON_LEADERS = "leaders.json"
    private val TAG = CharacterListViewModel::class.java.simpleName
  }

  fun loadFavoriteCharacterDatabase(context: Context) {
    favoriteCharacterDatabase = Room.databaseBuilder(
      context,
      AppDatabase::class.java,
      DB_FAVORITE_CHARACTERS
    ).build()
  }

  /**
   * loadAllJsonFileData(): Loads all JSON files from assets, deserializes into data objects, merges
   * favorite status from Room database, and updates all StateFlows. Must be called after
   * loadFavoriteCharacterDatabase().
   */
  fun loadAllJsonFileData(context: Context) {
    val characterListMap: MutableMap<Int, MutableList<RPGCharacter>> = linkedMapOf()
    var leaderMap: Map<Int, RPGCharacter> = emptyMap()
    val gameSet = mutableSetOf<RPGGame>()
    val gameIdOrderList = mutableListOf<Int>()

    val jsonFileList = JsonUtil.getJsonFileNameListFromAssetFolder(context)
    jsonFileList.forEach { jsonFileName ->
      val characterList = JsonUtil.getJsonCharacterDataFromAsset(context, jsonFileName)
        ?: return@forEach

      val game = characterList.first().let { c ->
        RPGGame(gameId = c.gameId, gameName = c.game)
      }

      if (jsonFileName == FILE_JSON_LEADERS) {
        leaderMap = characterList.associateBy { it.gameId }
        Log.d(TAG, "Leader character data found, size is: ${leaderMap.size}")
      } else {
        gameSet.add(game)
        gameIdOrderList.add(game.gameId)
        characterListMap[game.gameId] = characterList.toMutableList()
      }
    }

    totalCharacterListData.clear()
    totalCharacterListData.putAll(characterListMap)
    gameIdOrder.clear()
    gameIdOrder.addAll(gameIdOrderList)

    mergeFavoritesIntoCharacters()

    val gameMap = gameSet.associateBy { it.gameId }
    Log.d(TAG, "Total character data size: ${characterListMap.size}")

    characterListDataFlow.value = buildCharacterListForFilter(null).toList()
    leaderListDataFlow.value = leaderMap
    gameListDataFlow.value = gameMap
  }

  fun loadUpdatedCharacterList(gameId: Int?) {
    characterListDataFlow.value = buildCharacterListForFilter(gameId).toList()
  }

  /**
   * updateFavoritedCharacter(): Updates favorite status for a character: toggles in DB and updates
   * in-memory list in place.
   */
  fun updateFavoritedCharacter(characterId: Int, gameId: Int?) {
    val targetList = totalCharacterListData.values
      .firstOrNull { list -> list.any { it.id == characterId } } ?: run {
      Log.e(TAG, "Failed to update character favorite, character $characterId not found.")
      return
    }
    val index = targetList.indexOfFirst { it.id == characterId }
    if (index < 0) return

    val character = targetList[index]
    val updated = character.copy(favorited = !character.favorited)

    if (updated.favorited) {
      favoriteCharacterDatabase.favoriteCharacterDao().addFavoriteCharacters(
        updated.toFavoriteCharacter()
      )
    } else {
      favoriteCharacterDatabase.favoriteCharacterDao().deleteCharacter(
        updated.toFavoriteCharacter()
      )
    }

    targetList[index] = updated
    characterListDataFlow.value = buildCharacterListForFilter(gameId).toList()
  }

  /**
   * mergeFavoritesIntoCharacters(): Loads favorited character IDs from Room and sets
   * RPGCharacter.favorited for matching characters.
   */
  private fun mergeFavoritesIntoCharacters() {
    if (!::favoriteCharacterDatabase.isInitialized) return
    val favoriteIds = favoriteCharacterDatabase.favoriteCharacterDao()
      .getFavoriteCharacters()
      .map { it.id }
      .toSet()

    totalCharacterListData.values.forEach { list ->
      list.forEachIndexed { index, character ->
        if (character.id in favoriteIds && !character.favorited) {
          list[index] = character.copy(favorited = true)
        }
      }
    }
  }

  /**
   * buildCharacterListForFilter: Returns character list for the given gameId, or all characters in
   * stable order when null.
   */
  private fun buildCharacterListForFilter(gameId: Int?): List<RPGCharacter> =
    gameId?.let { totalCharacterListData[it] }
      ?: gameIdOrder.mapNotNull { id -> totalCharacterListData[id] }.flatten()
}
