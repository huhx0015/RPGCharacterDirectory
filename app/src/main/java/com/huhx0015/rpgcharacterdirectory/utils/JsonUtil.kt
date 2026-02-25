package com.huhx0015.rpgcharacterdirectory.utils

import android.content.Context
import android.util.Log
import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.IOException

object JsonUtil {

  private const val EXT_JSON = ".json"

  // getJsonFileNameListFromAssetFolder(): Accesses the assets folder and returns a list of all
  // file names in the asset folder.
  fun getJsonFileNameListFromAssetFolder(context: Context): List<String> {
    val assetManager = context.assets
    val jsonFileList: MutableList<String> = mutableListOf()
    try {
      // List all files in the root assets folder.
      val fileList = assetManager.list("")
      fileList
        ?.filter { it.endsWith(EXT_JSON, ignoreCase = true) }
        ?.forEach { fileName ->
          jsonFileList.add(fileName)
          Log.d(JsonUtil::class.java.simpleName, "JSON file in assets: $fileName")
        }
    } catch (e: IOException) {
      Log.e(JsonUtil.javaClass.simpleName, "Failed to open asset folder: ${e.message}")
    }
    return jsonFileList
  }

  // getJsonCharacterDataFromAsset(): Loads the JSON specified JSON file and deserializes the JSON
  // string into a List<RPGCharacter>.
  fun getJsonCharacterDataFromAsset(context: Context, jsonFileName: String): List<RPGCharacter>? {
    // Builds the Moshi instance needed for deserializing the JSON into data objects.
    val moshi = Moshi.Builder()
      .addLast(KotlinJsonAdapterFactory())
      .build()

    // Establishes the class to be used for the resulting List when the JSON string is deserialized.
    val listType = Types.newParameterizedType(List::class.java, RPGCharacter::class.java)
    val moshiAdapter: JsonAdapter<List<RPGCharacter>> = moshi.adapter(listType)

    // Opens the specified JSON asset file name into String format.
    val jsonString = context.assets.open(jsonFileName).bufferedReader().use {
      it.readText()
    }
    // Deserializes the JSON string into a List<RPGCharacter> object.
    val characterList = moshiAdapter.fromJson(jsonString)

    return characterList
  }
}