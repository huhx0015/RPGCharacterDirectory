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
    private const val FILE_JSON_FINAL_FANTASY_IV = "final_fantasy_iv.json"
    private const val FILE_JSON_FINAL_FANTASY_V = "final_fantasy_v.json"
    private const val FILE_JSON_FINAL_FANTASY_VI = "final_fantasy_vi.json"
    private const val FILE_JSON_FINAL_FANTASY_VII = "final_fantasy_vii.json"
    private const val FILE_JSON_FINAL_FANTASY_VIII = "final_fantasy_viii.json"
    private const val FILE_JSON_FINAL_FANTASY_IX = "final_fantasy_ix.json"
    private const val FILE_JSON_FINAL_FANTASY_X = "final_fantasy_x.json"
    private const val FILE_JSON_FINAL_FANTASY_XII = "final_fantasy_xii.json"
    private const val FILE_JSON_FINAL_FANTASY_XIII = "final_fantasy_xiii.json"
    private const val FILE_JSON_FINAL_FANTASY_XV = "final_fantasy_xv.json"
    private const val FILE_JSON_FINAL_FANTASY_XVI = "final_fantasy_xvi.json"
    private const val FILE_JSON_SUIKODEN = "suikoden.json"
    private const val FILE_JSON_SUIKODEN_II = "suikoden_ii.json"
    private const val FILE_JSON_SUIKODEN_III = "suikoden_iii.json"
    private const val FILE_JSON_SUIKODEN_IV = "suikoden_iv.json"
    private const val FILE_JSON_SUIKODEN_V = "suikoden_v.json"
    private const val FILE_JSON_LEADERS = "leaders.json"
    private val TAG = CharacterListViewModel::class.java.simpleName
  }

  fun loadAllCharacterDate(context: Context) {
    // Stores the entire character list data.
    val characterListMap: MutableMap<String, List<RPGCharacter>> = mutableMapOf()

    // Stores the unique set of game names from the character list data.
    val gameNameSet: MutableSet<String> = mutableSetOf()

    // Parses all JSON character lists.
    val ff2CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_II
    ) ?: emptyList()



    characterListMap[FILE_JSON_FINAL_FANTASY_II] = ff2CharacterList

    val ff4CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_IV
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_IV] = ff4CharacterList

    val ff5CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_V
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_V] = ff5CharacterList

    val ff6CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_VI
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_VI] = ff6CharacterList

    val ff7CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_VII
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_VII] = ff7CharacterList

    val ff8CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_VIII
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_VIII] = ff8CharacterList

    val ff9CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_IX
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_IX] = ff9CharacterList

    val ff10CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_X
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_X] = ff10CharacterList

    val ff12CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_XII
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_XII] = ff12CharacterList

    val ff13CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_XIII
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_XIII] = ff13CharacterList

    val ff15CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_XV
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_XV] = ff15CharacterList

    val ff16CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_FINAL_FANTASY_XVI
    ) ?: emptyList()
    characterListMap[FILE_JSON_FINAL_FANTASY_XVI] = ff16CharacterList

    val s1CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_SUIKODEN
    ) ?: emptyList()
    characterListMap[FILE_JSON_SUIKODEN] = s1CharacterList

    val s2CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_SUIKODEN_II
    ) ?: emptyList()
    characterListMap[FILE_JSON_SUIKODEN_II] = s2CharacterList

    val s3CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_SUIKODEN_III
    ) ?: emptyList()
    characterListMap[FILE_JSON_SUIKODEN_III] = s3CharacterList

    val s4CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_SUIKODEN_IV
    ) ?: emptyList()
    characterListMap[FILE_JSON_SUIKODEN_IV] = s4CharacterList

    val s5CharacterList = JsonUtil.getJsonCharacterDataFromAsset(
      context = context,
      jsonFileName = FILE_JSON_SUIKODEN_V
    ) ?: emptyList()
    characterListMap[FILE_JSON_SUIKODEN_V] = s5CharacterList

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