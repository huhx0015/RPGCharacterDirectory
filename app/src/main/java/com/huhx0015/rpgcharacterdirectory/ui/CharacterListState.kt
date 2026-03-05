package com.huhx0015.rpgcharacterdirectory.ui

import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter
import com.huhx0015.rpgcharacterdirectory.model.RPGGame

data class CharacterListState(
  val characterList: List<RPGCharacter> = emptyList(),
  val leaderMap: Map<Int, RPGCharacter> = emptyMap(),
  val gameMap: Map<Int, RPGGame> = emptyMap(),
  val selectedGameId: Int? = null
) {

  // getGameNameList(): Returns set of all RPGGame in gameMap.
  fun getGameNameList(): Set<RPGGame> {
    return gameMap.entries.map { gameMap -> gameMap.value }.toSet()
  }
}