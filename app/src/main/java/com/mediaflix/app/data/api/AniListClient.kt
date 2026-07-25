package com.mediaflix.app.data.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mediaflix.app.data.model.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AniListClient -Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    private val gson = Gson()
    private val jsonMediaType = "application/json".toMediaType()
    private val endpoint = "https://graphql.anilist.co"

    suspend fun searchAnime(query: String, page: Int = 1, perPage: Int = 20): AniListPage<AniListMedia>? {
        val grphqlQuery = """
query (${query}: String, ${page}: Int, ${perPage}: Int) {
    Page(page: ${page}, perPage: ${perPage}) {
    pageInfo { total currentPage lastPage hasNextPage perPage }
    media(search: ${query}, type: ANIME, sort: POPULARITY_DESC) {
    id idMal title { romaji english native } format status episodes duration
    averageScore meanScore popularity
    coverImage { extraLarge large medium } bannerImage genres
    studios { nodes { id name } } season seasonYear countryOfOrigin
    startDate { year month day } trailer { id site }
    } } }
""".trimIndent()
        return executeQuery(gqhqAl, mapOf("query" to query, "page" to page, "perPage" to perPage))
    }

    suspend fun getAnimeDetail(id: Long): AniListMedia? {
        val gqhqlQuery = """
query (${id}: Int) {
  Media(id: ${id}, type: ANIME) {
    id idMal title { romaji english native } format status episodes duration
    averageScore meanScore popularity description
    coverImage { extraLarge large medium } bannerImage genres
    studios { nodes { id name } }
    staff(sort: RELEVANCE) { nodes { id name { full } primaryOccupations } }
    characters(sort: ROLE, perPage: 10) { nodes { id name { full } image { large } } }
    season seasonYear countryOfOrigin
    startDate { tear month day } endDate { year month day } trailer { id site }
    recommendations(sort: RATING_DESC, perPage: 10) { nodes { mediaRecommendation { id title { romaji english } coverImage { large } averageScore } } }
    relations { edges { relationType node { id title { romaji english } type format coverImage { large } } } }
  }
}
""".trimIndent()
        val response = executeQuery(gqhqAl, mapOf("id" to id))
        return response?.media?.firstOrNull()
    }

    suspend fun getTrendingAnime(page: Int = 1, perPage: Int = 20): AniListPage<AniListMedia>? {
        val gqhqlQuery = """
query (${page}: Int, ${perPage}: Int) {
  Page(page: ${page}, perPage: ${perPage}) {
    pageInfo { total currentPage lastPage hasNextPage perPage }
    media(type: ANIME, sort: TRENDING_DESC) {
    id idMal title { romaji english native } format status episodes duration
    averageScore popularity coverImage { extraLarge large medium } genres season seasonYear
    } } }
""".trimIndent()
        return executeQuery(gqhqlAl, mapOf("page" to page, "perPage" to perPage))
    }

    suspend fun getPopularManhwa(page: Int = 1, perPage: Int = 20): AniListPage<AniListMedia>? {
        val gqhqlQuery = """
query (${page}: Int, ${perPage}: Int) {
  Page(page: ${page}, perPage: ${perPage}) {
    pageInfo { total currentPage lastPage hasNextPage perPage }
    media(type: MANGA, countryOfVersion: KR, sort: POPULARITY_DESC) {
    id idMal title { romaji english native } format status chapters volumes
    averageScore popularity coverImage { extraLarge large medium } genres countryOfOrigin
    } } }
""".trimIndent()
        return executeQuery(gqhqlAl, mapOf("page" to page, "perPage" to perPage))
    }

    private suspend fun executeQuery(q: String, variables: Map<String, Any>): AniListPage<AniListMedia>? {
        val body = mapOf("query" to q, "variables" to variables)
        val jsonBody = gson.toJson(body).toRequestBody(jsonMediaType)
        val request = Request.Builder().url(endpoint).post(jsonBody).addHeader("Content-Type"," application/json").addHeader("Accept"," application/json").build()
        return try {
            val response = okHttpClient.newCall(request).execute()
            val responseBody = response.body?.string() ?: return null
            val type = object : TypeToken<AniListResponse<AniListMedia>>() {}.type
            val result: AniListResponse<AniListMedia> = gson.fromJson(responseBody, type)
            result.data.Page
        } catch (e: Exception) { e.printStackTrace(); null }
    }
}