package com.mediaflix.app.data.api

import com.mediaflix.app.data.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("trending/all/week")
    suspend fun getTrending(@Query("api_key") apiKey: String, @Query("language") language: String = "en-US", @Query("page") page: Int = 1): TmdbMovieResponse
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String, @Query("language") language: String = "en-US", @Query("page") page: Int = 1): TmdbMovieResponse
    @GET("tv/popular")
    suspend fun getPopularSeries(@Query("api_key") apiKey: String, @Query("language") language: String = "en-US", @Query("page") page: Int = 1): TmdbMovieResponse
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Long, @Query("api_key") apiKey: String, @Query("language") language: String = "en-US"): TmdbMedia
    @GET("tv/{tv_id}")
    suspend fun getSeriesDetail(@Path("tv_id") seriesId: Long, @Query("api_key") apiKey: String, @Query("language") language: String = "en-US"): TmdbMedia
    @GET("search/multi")
    suspend fun search(@Query("api_key") apiKey: String, @Query("query") query: String, @Query("language") language: String = "en-US", @Query("page") page: Int = 1): TmdbMovieResponse
    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Long, @Query("api_key") apiKey: String, @Query("language") language: String = "en-US", @Query("page") page: Int = 1): TmdbMovieResponse
    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarSeries(@Path("tv_id") seriesId: Long, @Query("api_key") apiKey: String, @Query("language") language: String = "en-US", @Query("page") page: Int = 1): TmdbMovieResponse
}

interface JikanApi {
    @GET("anime")
    suspend fun getAnimeSearch(@Query("q") query: String, @Query("page") page: Int = 1, @Query("limit") limit: Int = 20): JikanAnimeResponse
    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int = 1, @Query("limit") limit: Int = 20, @Query("type") type: String? = null, @Query("filter") filter: String? = null): JikanAnimeResponse
    @GET("seasons/now")
    suspend fun getCurrentSeason(@Query("page") page: Int = 1, @Query("limit") limit: Int = 20): JikanAnimeResponse
    @GET("anime/{id}/full")
    suspend fun getAnimeDetail(@Path("id") animeId: Long): JikanAnimeResponse
    @GET("manga")
    suspend fun getMangaSearch(@Query("q") query: String, @Query("page") page: Int = 1, @Query("limit") limit: Int = 20): JikanMangaResponse
    @GET("top/manga")
    suspend fun getTopManga(@Query("page") page: Int = 1, @Query("limit") limit: Int = 20): JikanMangaResponse
    @GET("manga/{id}/full")
    suspend fun getMangaDetail(@Path("id") mangaId: Long): JikanMangaResponse
    @GET("top/manga")
    suspend fun getTopManhwa(@Query("page") page: Int = 1, @Query("limit") limit: Int = 20, @Query("type") type: String = "manhwa"): JikanMangaResponse
}

interface AniListApi {
    // We'll use a raw call with graphql query body
}

interface MangaDexApi {
    @GET("manga")
    suspend fun searchManga(@Query("title") title: String, @Query("limit") limit: Int = 20, @Query("offset") offset: Int = 0, @Query("includes[]") includes: List<String> = listOf("cover_art"), @Query("order[relevance]") orderRelevance: String = "desc"): MangaDexMangaResponse
    @GET("manga")
    suspend fun getMangaList(@Query("limit") limit: Int = 20, @Query("offset") offset: Int = 0, @Query("includes[]") includes: List<String> = listOf("cover_art"), @Query("order[followedCount]") order: String = "desc", @Query("originalLanguage[]") originalLanguage: List<String>? = null): MangaDexMangaResponse
    @GET("manga/{id}")
    suspend fun getMangaDetail(@Path("id") mangaId: String, @Query("includes[]") includes: List<String> = listOf("cover_art", "author", "artist")): MangaDexMangaResponse
    @GET("manga/{id}/feed")
    suspend fun getMangaChapters(@Path("id") mangaId: String, @Query("limit") limit: Int = 50, @Query("offset") offset: Int = 0, @Query("translatedLanguage[]") translatedLanguage: List<String> = listOf("en"), @Query("order[chapter]") order: String = "asc"): MangaDexChapterResponse
    @GET("cover")
    suspend fun getCover(@Query("manga[]") mangaIds: List<String>, @Query("limit") limit: Int = 10): MangaDexCoverResponse
}

interface ShineiApi {
    @GET("search")
    suspend fun search(@Query("q") query: String): ShineiApiResponse<List<ShineiSeries>>
    @GET("series/{slug}")
    suspend fun getSeriesDetail(@Path("slug") slug: String): ShineiApiResponse<ShineiSeries>
    @GET("popular")
    suspend fun getPopular(@Query("type") type: String? = null): ShineiApiResponse<List<ShineiSeries>>
    @GET("top")
    suspend fun getTop(): ShineiApiResponse<List<ShineiSeries>>
    @GET("series/{slug}/chapters")
    suspend fun getChapters(@Path("slug") slug: String): ShineiApiResponse<List<ShineiChapter>>
}