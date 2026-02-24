package com.huhx0015.rpgcharacterdirectory.ui

import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter

data class CharacterListState(
  val characterListMap: Map<String, List<RPGCharacter>> = emptyMap(),
  val leaderMap: Map<Int, RPGCharacter> = emptyMap()
) {

  fun allCharacterList(): List<RPGCharacter> {
    return characterListMap.values.flatten()
  }
}