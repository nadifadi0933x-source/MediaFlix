package com.mediaflix.app.data.model

import com.google.gson.annotations.SerializedName

data class TmdbMovieResponse(
    val page: Int,
    val results: List<TmdbMedia>,
    @SerializedName("total_pages") val totalPages: Int
)

data class TmdbMedia(
    val id: Long,
    val title: String? = null,
    val name: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    val overview: String? = null,
    @SerializedName("vote_average") val voteAverage: Double? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("first_air_date") val firstAirDate: String? = null,
    val genres: List<TmdbGenre>? = null,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int? = null,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int? = null,
    val runtime: Int? = null,
    val status: String? = null,
    val tagline: String? = null,
    @SerializedName("production_companies") val productionCompanies: List<TmdbCompany>? = null
)

data class TmdbGenre(val id: Int, val name: String)

data class TmdbCompany(val id: Int, val name: String, @SerializedName("logo_path") val logoPath: String? = null)

data class JikanAnimeResponse(
    val data: List<JikanAnime>,
    val pagination: JikanPagination
)

data class JikanAnime(
    @SerializedName("mal_id") val malId: Long,
    val title: String,
    @SerializedName("title_english") val titleEnglish: String? = null,
    @SerializedName("title_japanese") val titleJapanese: String? = null,
    val type: String? = null,
    val episodes: Int? = null,
    val status: String? = null,
    val score: Double? = null,
    val synopsis: String? = null,
    val duration: String? = null,
    @SerializedName("images") val images: JikanImages,
    val genres: List<JikanGenre>? = null,
    val studios: List<JikanStudio>? = null,
    @SerializedName("aired") val aired: JikanAired? = null,
    val trailer: JikanTrailer? = null
)

data class JikanMangaResponse(
    val data: List<JikanManga>,
    val pagination: JikanPagination
)

data class JikanManga(
    @SerializedName("mal_id") val malId: Long,
    val title: String,
    @SerializedName("title_english") val titleEnglish: String? = null,
    val type: String? = null,
    val chapters: Int? = null,
    val volumes: Int? = null,
    val status: String? = null,
    val score: Double? = null,
    val synopsis: String? = null,
    @SerializedName("images") val images: JikanImages,
    val genres: List<JikanGenre>? = null,
    val authors: List<JikanAuthor>? = null
)

data class JikanImages(val jpg: JikanImageUrls, val webp: JikanImageUrls? = null)

data class JikanImageUrls(
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("large_image_url") val largeImageUrl: String? = null)

data class JikanGenre(@SerializedName("mal_id") val malId: Long, val name: String)

data class JikanStudio(@SerializedName("mal_id") val malId: Long, val name: String)

data class JikanAuthor(@SerializedName("mal_id") val malId: Long, val name: String)

data class JikanAired(val from: String?, val to: String?)

data class JikanTrailer(@SerializedName("youtube_id") val youtubeId: String?, val url: String?)

data class JikanPagination(
    @SerializedName("last_visible_page") val lastVisiblePage: Int,
    @SerializedName("has_next_page") val hasNextPage: Boolean,
    @SerializedName("current_page") val currentPage: Int
)

data class AniListResponse<T>(val data: AniListData<T>)

data class AniListData<T>(val Page: AniListPage<T>? = null, val Media: T? = null)

data class AniListPage<T>(val pageInfo: AniListPageInfo, val media: List<T>)

data class AniListPageInfo(val total: Int, val currentPage: Int, val lastPage: Int, val hasNextPage: Boolean, val perPage: Int)

data class AniListMedia(
    val id: Long,
    val idMal: Long? = null,
    val title: AniListTitle? = null,
    val type: String? = null,
    val format: String? = null,
    val status: String? = null,
    val description: String? = null,
    val episodes: Int? = null,
    val chapters: Int? = null,
    val volumes: Int? = null,
    val duration: Int? = null,
    val averageScore: Int? = null,
    val meanScore: Int? = null,
    val popularity: Int? = null,
    val coverImage: AniListCoverImage? = null,
    val bannerImage: String? = null,
    val genres: List<String>? = null,
    val studios: AniListStudios? = null,
    val season: String? = null,
    val seasonYear: Int? = null,
    val countryOfOrigin: String? = null,
    val trailer: AniListTrailer? = null,
    val startDate: AniListDate? = null,
    val endDate: AniListDate? = null
)

data class AniListTitle(val romaji: String?, val english: String?, val native: String?)

data class AniListCoverImage(val extraLarge: String?, val large: String?, val medium: String?)

data class AniListTag(val id: Long, val name: String, val rank: Int?)

data class AniListStudios(val nodes: List<AniListStudioNode>?)

data class AniListStudioNode(val id: Long, val name: String)

data class AniListStaffNode(val id: Long, val name: AniListStaffName?, val primaryOccupations: List<String>?)

data class AniListStaffName(val full: String?)

data class AniListCharacterNode(val id: Long, val name: AniListCharacterName?, val image: AniListCoverImage?)

data class AniListCharacterName(val full: String?)

data class AniListDate(val year: Int?, val month: Int?, val day: Int?)

data class AniListTrailer(val id: String?, val site: String?)

data class AniListRecommendationNode(val mediaRecommendation: AniListMedia?)

data class AniListMediaEdge(val relationType: String?, val node: AniListMedia?)

data class MangaDexMangaResponse(
    val result: String,
    val data: List<MangaDexManga>,
    val total: Int,
    val limit: Int,
    val offset: Int
)

data class MangaDexManga(
    val id: String,
    val type: String,
    val attributes: MangaDexMangaAttributes,
    val relationships: List<MangaDexRelationship>? = null
)

data class MangaDexMangaAttributes(
    val title: Map<String, String?>? = null,
    @SerializedName("altTitles") val altTitles: List<Map<String, String?>>? = null,
    val description: Map<String, String?>? = null,
    val status: String? = null,
    @SerializedName("year") val year: Int? = null,
    @SerializedName("contentRating") val contentRating: String? = null,
    val tags: List<MangaDexTag>? = null,
    @SerializedName("originalLanguage") val originalLanguage: String? = null,
    @SerializedName("lastChapter") val lastChapter: String? = null
)

data class MangaDexTag(val id: String, val type: String, val attributes: MangaDexTagAttributes)

data class MangaDexTagAttributes(val name: Map<String, String?>? = null, val group: String? = null)

data class MangaDexRelationship(val id: String, val type: String, val attributes: Map<String, Any>? = null)

data class MangaDexCoverResponse(val result: String, val data: List<MangaDexCover>)

data class MangaDexCover(val id: String, val attributes: MangaDexCoverAttributes)

data class MangaDexCoverAttributes(val fileName: String)

data class MangaDexChapterResponse(val result: String, val data: List<MangaDexChapter>, val total: Int)

data class MangaDexChapter(val id: String, val attributes: MangaDexChapterAttributes)

data class MangaDexChapterAttributes(
    val title: String? = null,
    val chapter: String? = null,
    val volume: String? = null,
    @SerializedName("translatedLanguage") val translatedLanguage: String? = null,
    @SerializedName("externalUrl") val externalUrl: String? = null,
    val pages: Int? = null
)

data class ShineiApiResponse<T>(val success: Boolean, val data: T)

data class ShineiSeries(
    val title: String,
    val rating: Double? = null,
    val status: String? = null,
    val genres: List<String>? = null,
    @SerializedName("cover_url") val coverUrl: String? = null,
    val description: String? = null,
    val type: String? = null,
    val chapters: List<ShineiChapter>? = null
)

data class ShineiChapter(
    val number: Double? = null,
    val title: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null
)

data class MediaItem(
    val id: String,
    val source: MediaSource,
    val title: String,
    val titleEnglish: String? = null,
    val titleNative: String? = null,
    val type: MediaType,
    val format: String? = null,
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val coverUrl: String? = null,
    val description: String? = null,
    val rating: Double? = null,
    val genres: List<String> = emptyList(),
    val status: String? = null,
    val year: Int? = null,
    val episodes: Int? = null,
    val chapters: Int? = null,
    val duration: Int? = null,
    val country: String? = null,
    val trailerUrl: String? = null,
    val studios: List<String> = emptyList(),
    val authors: List<String> = emptyList(),
    val customStreamUrl: String? = null,
    val customPdfUrl: String? = null,
    val mangaDexId: String? = null,
    val coverFileName: String? = null,
    val shineiSlug: String? = null
)

enum class MediaSource { TMDB, JEQHAN, ANILIST, MANGADEX, SHINEI }

enum class MediaType { MOVIE, SERIES, ANIME, MANGA, MANHWA }