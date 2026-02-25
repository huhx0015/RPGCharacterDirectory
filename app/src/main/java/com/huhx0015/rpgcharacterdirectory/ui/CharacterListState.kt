package com.huhx0015.rpgcharacterdirectory.ui

import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter

data class CharacterListState(
  val characterListMap: Map<String, List<RPGCharacter>> = emptyMap(),
  val leaderMap: Map<Int, RPGCharacter> = emptyMap()
) {

  // allCharacterList(): Returns the total list of RPGCharacters from characterListMap.
  fun allCharacterList(): List<RPGCharacter> {
    return characterListMap.values.flatten()
  }

  // gameList(): Returns set of unique game name values.
  fun gameList(): Set<String> {
    return characterListMap.values.flatten().map { character -> character.game }.toSet()
  }
}