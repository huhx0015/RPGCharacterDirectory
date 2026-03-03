package com.huhx0015.rpgcharacterdirectory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huhx0015.rpgcharacterdirectory.database.dao.FavoriteCharacterDao
import com.huhx0015.rpgcharacterdirectory.database.entity.FavoriteCharacter

@Database(entities = [FavoriteCharacter::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}