package com.mediaflix.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mediaflix.app.data.model.Bookmark
import com.mediaflix.app.data.model.CustomLink

@Database(
    entities = [CustomLink::class, Bookmark::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customLinkDao(): CustomLinkDao
    abstract fun bookmarkDao(): BookmarkDao
}