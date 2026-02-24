package com.huhx0015.rpgcharacterdirectory.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter
import com.huhx0015.rpgcharacterdirectory.utils.JsonUtil

class CharacterListViewModel: ViewModel() {

  fun loadJsonData(context: Context): List<RPGCharacter>? {
    val characterList = JsonUtil.getJsonCharacterDataFromAsset(context, "final_fantasy_ii.json")
    println("${characterList?.size} : contents: $characterList")
    return characterList
  }
}