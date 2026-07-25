package com.mediaflix.app.di

import com.mediaflix.app.data.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    private const val JIKAN_BASE_URL = "https://api.jikan.moe/v4/"
    private const val ANILIST_BASE_URL = "https://graphql.anilist.co/"
    private const val MANGADEX_BASE_URL = "https://api.mangadex.org/"
    private const val SHINEI_BASE_URL = "https://shineiapi.vercel.app/api/v1/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideTmdbApi(okHttpClient: OkHttpClient): TmdbApi =
        Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApi::class.java)

    @Provides
    @Singleton
    fun provideJikanApi(okHttpClient: OkHttpClient): JikanApi =
        Retrofit.Builder()
            .baseUrl(JIKAN_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JikanApi::class.java)

    @Provides
    @Singleton
    fun provideMangaDexApi(okHttpClient: OkHttpClient): MangaDexApi =
        Retrofit.Builder()
            .baseUrl(MANGADEX_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MangaDexApi::class.java)

    @Provides
    @Singleton
    fun provideShineiApi(okHttpClient: OkHttpClient): ShineiApi =
        Retrofit.Builder()
            .baseUrl(SHINEI_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShineiApi::class.java)

    // AniList uses GraphQL — we provide a raw OkHttpClient-based service instead
    @Provides
    @Singleton
    fun provideAniListClient(okHttpClient: OkHttpClient): AniListClient =
        AniListClient(okHttpClient)
}