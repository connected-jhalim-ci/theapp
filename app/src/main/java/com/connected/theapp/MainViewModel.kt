package com.connected.theapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@AllOpen
class MainViewModel(val repository: GithubRepository) : ViewModel() {

    val searchResult: MutableLiveData<List<SearchData>> = MutableLiveData()

    fun search(keyword: String) {
        val users = repository.searchUser(keyword)
            .map {
                SearchData(title = it.login, imageUrl = it.avatarUrl)
            }
        val repos = repository.searchRepositories(keyword)
            .map {
                SearchData(title = it.name, imageUrl = it.owner.avatarUrl)
            }
        searchResult.value = users + repos
    }


}