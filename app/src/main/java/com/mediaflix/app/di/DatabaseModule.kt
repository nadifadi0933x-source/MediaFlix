package com.mediaflix.app.di

import android.content.Context
import androidx.room.Room
import com.mediaflix.app.data.db.AppDatabase
import com.mediaflix.app.data.db.BookmarkDao
import com.mediaflix.app.data.db.CustomLinkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mediaflix.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideCustomLinkDao(db: AppDatabase): CustomLinkDao = db.customLinkDao()

    @Provides
    fun provideBookmarkDao(db: AppDatabase): BookmarkDao = db.bookmarkDao()
}