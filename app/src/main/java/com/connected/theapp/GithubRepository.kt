package com.connected.theapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GithubRepository {
    suspend fun searchUsers(keyword: String): List<GithubUser>
    suspend fun searchRepositories(keyword: String): List<GithubRepo>
}

class GithubRepositoryImpl(val service: GithubService) : GithubRepository {

    override suspend fun searchUsers(keyword: String): List<GithubUser> {
        return withContext(Dispatchers.IO) {
            service.searchUsers(keyword).users
        }
    }

    override suspend fun searchRepositories(keyword: String): List<GithubRepo> {
        return withContext(Dispatchers.IO) {
            service.searchRepositories(keyword).repos
        }
    }

}