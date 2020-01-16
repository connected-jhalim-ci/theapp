package com.connected.theapp

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubService {

    @GET("search/users")
    suspend fun searchUsers(@Query("q") keyword: String): SearchUsersResponse

    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") keyword: String): SearchRepositoriesResponse

    companion object {
        fun create(baseUrl: String): GithubService {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GithubService::class.java)
        }
    }
}

data class SearchUsersResponse(
    @SerializedName("items") val users: List<GithubUser> = emptyList()
)

data class GithubUser(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)

data class SearchRepositoriesResponse(
    @SerializedName("items") val repos: List<GithubRepo> = emptyList()
)

data class GithubRepo(
    val name: String,
    val owner: GithubUser
)