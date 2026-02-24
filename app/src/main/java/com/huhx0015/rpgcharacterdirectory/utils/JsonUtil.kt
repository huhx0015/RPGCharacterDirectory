package com.huhx0015.rpgcharacterdirectory.utils

import android.content.Context
import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object JsonUtil {

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