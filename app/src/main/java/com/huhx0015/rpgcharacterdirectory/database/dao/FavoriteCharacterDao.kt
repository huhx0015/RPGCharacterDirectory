package com.huhx0015.rpgcharacterdirectory.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.huhx0015.rpgcharacterdirectory.database.entity.FavoriteCharacter

@Dao
interface FavoriteCharacterDao {

  @Query("SELECT * FROM favorite_characters")
  fun getFavoriteCharacters(): List<FavoriteCharacter>

  @Query("SELECT * From favorite_characters WHERE id LIKE :id LIMIT 1")
  fun findByCharacterId(id: Int): FavoriteCharacter?

  @Insert
  fun addFavoriteCharacters(vararg characters: FavoriteCharacter)

  @Delete
  fun deleteCharacter(character: FavoriteCharacter)
}