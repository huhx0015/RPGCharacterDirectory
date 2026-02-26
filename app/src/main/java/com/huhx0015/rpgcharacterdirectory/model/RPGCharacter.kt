package com.huhx0015.rpgcharacterdirectory.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 *  Data class primarily to hold RPG character data, read from the asset JSON files.
 */
@JsonClass(generateAdapter = true) // Auto generates a JsonAdapter at compile time.
data class RPGCharacter(
  val id: Int, // ID of RPG character.
  val name: String, // Name of RPG character.
  @Json(name = "class") val characterClass: String, // Class of RPG character (need to specify JSON name to avoid 'class' key word clash.
  val game: String, // Name of RPG game character is from.
  val gameId: Int, // ID of the RPG game.
)