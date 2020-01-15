package com.connected.theapp

interface GithubRepository {

    fun searchUser(keyword: String): List<GithubUser>
    fun searchRepositories(keyword: String): List<GithubRepo>

}

class GithubRepositoryImpl : GithubRepository {

    override fun searchUser(keyword: String): List<GithubUser> {
        return emptyList()
    }

    override fun searchRepositories(keyword: String): List<GithubRepo> {
        return emptyList()
    }

}

data class GithubUser(
    val login: String,
    val avatarUrl: String
)

data class GithubRepo(
    val name: String,
    val owner: GithubUser
)