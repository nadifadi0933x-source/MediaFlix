package com.mediaflix.app.data.db

import androidx.room.*
import com.mediaflix.app.data.model.Bookmark
import com.mediaflix.app.data.model.CustomLink
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomLinkDao {
    @Query("SELECT * FROM custom_links WHERE mediaId = :mediaId")
    suspend fun getLink(mediaId: String): CustomLink?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLink(link: CustomLink)

    @Query("UPDATE custom_links SET streamUrl = :url, updatedAt = :now WHERE mediaId = :mediaId")
    suspend fun updateStreamUrl(mediaId: String, url: String, now: Long = System.currentTimeMillis())

    @Query("UPDATE custom_links SET pdfUrl = :url, updatedAt = :now WHERE mediaId = :mediaId")
    suspend fun updatePdfUrl(mediaId: String, url: String, now: Long = System.currentTimeMillis())

    @Delete
    suspend fun deleteLink(link: CustomLink)

    @Query("SELECT * FROM custom_links")
    fun getAllLinks(): Flow<List<CustomLink>>
}

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY addedAt DESC")
    fun getAllBookmarks(): Flow<List<Bookmark>>

    @Query("SELECT * FROM bookmarks WHERE mediaId = :mediaId")
    suspend fun getBookmark(mediaId: String): Bookmark?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: Bookmark)

    @Query("DELETE FROM bookmarks WHERE mediaId = :mediaId")
    suspend fun removeBookmark(mediaId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarks WHERE mediaId = :mediaId)")
    suspend fun isBookmarked(mediaId: String): Boolean
}