package com.huhx0015.rpgcharacterdirectory.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.huhx0015.rpgcharacterdirectory.model.RPGCharacter

@Entity(tableName = "favorite_characters")
data class FavoriteCharacter(
  @PrimaryKey val id: Int,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "class") val characterClass: String,
  @ColumnInfo(name = "game") val game: String,
  @ColumnInfo(name = "gameId") val gameId: Int
)

fun RPGCharacter.toFavoriteCharacter(): FavoriteCharacter = FavoriteCharacter(
  id = id,
  name = name,
  characterClass = characterClass,
  game = game,
  gameId = gameId
)

fun FavoriteCharacter.toRPGCharacter(): FavoriteCharacter = FavoriteCharacter(
  id = id,
  name = name,
  characterClass = characterClass,
  game = game,
  gameId = gameId
)