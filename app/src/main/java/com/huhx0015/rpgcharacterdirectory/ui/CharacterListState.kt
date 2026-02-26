package com.huhx0015.rpgcharacterdirectory.ui

import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter
import com.huhx0015.rpgcharacterdirectory.model.RPGGame
import kotlin.collections.flatten

data class CharacterListState(
  val characterListMap: Map<Int, List<RPGCharacter>> = emptyMap(),
  val leaderMap: Map<Int, RPGCharacter> = emptyMap(),
  val gameMap: Map<Int, RPGGame> = emptyMap(),
  val selectedGameId: Int? = null
) {

  // allCharacterList(): Returns the total list of RPGCharacters from characterListMap.
  fun allCharacterList(): List<RPGCharacter> {
    return characterListMap.values.flatten()
  }

  // getGameNameList(): Returns set of all RPGGame in gameMap.
  fun getGameNameList(): Set<RPGGame> {
    return gameMap.entries.map { gameMap -> gameMap.value }.toSet()
  }

  // getCharacterListByGameId: Returns list of RPGCharacters associated with the gameId.
  fun getCharacterListByGameId(gameId: Int): List<RPGCharacter> {
    return characterListMap[gameId] ?: emptyList()
  }
}