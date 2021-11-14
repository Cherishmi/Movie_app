package com.example.movies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "07aaa3ae149d82a90ddc04ed888996b9",
        @Query("page") page: Int
    ): Call<GetMovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "07aaa3ae149d82a90ddc04ed888996b9",
        @Query("page") page: Int
    ): Call<GetMovieResponse>

    @GET("movie/trending/all/day")
    fun getTrendingMovies(
        @Query("api_key") apiKey: String = "07aaa3ae149d82a90ddc04ed888996b9",
        @Query("page") page: Int
    ): Call<GetMovieResponse>

}