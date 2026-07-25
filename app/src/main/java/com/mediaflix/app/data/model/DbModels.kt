package com.mediaflix.app.data.model

import androidx.room.*

@Entity(tableName = "custom_links")
data class CustomLink(
    @PrimaryKey val mediaId: String,
    val streamUrl: String? = null,
    val pdfUrl: String? = null,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarks")
data class Bookmark(
    @PrimaryKey val mediaId: String,
    val title: String,
    val posterUrl: String? = null,
    val mediaType: String,
    val addedAt: Long = System.currentTimeMillis()
)