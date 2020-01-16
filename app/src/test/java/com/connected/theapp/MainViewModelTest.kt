package com.connected.theapp

import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainViewModelTest : KoinTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun `search() should update search result value by combining repository searchUser() and searchRepositories() result`() {
        val githubUsers = listOf(
            GithubUser(
                login = "connected",
                avatarUrl = "https://avatars2.githubusercontent.com/u/1562674?v=4"
            ),
            GithubUser(
                login = "aep",
                avatarUrl = "https://avatars3.githubusercontent.com/u/136926?v=4"
            ),
            GithubUser(
                login = "connectedCat",
                avatarUrl = "https://avatars1.githubusercontent.com/u/1493312?v=4"
            )
        )
        val githubRepos = listOf(
            GithubRepo(
                name = "connected-react-router",
                owner = GithubUser(
                    login = "supasate",
                    avatarUrl = "https://avatars3.githubusercontent.com/u/237753?v=4"
                )
            ),
            GithubRepo(
                name = "IoT-ConnectedCar",
                owner = GithubUser(
                    login = "pivotal-legacy",
                    avatarUrl = "https://avatars1.githubusercontent.com/u/9148?v=4"
                )
            ),
            GithubRepo(
                name = "DenseNet",
                owner = GithubUser(
                    login = "liuzhuang13",
                    avatarUrl = "https://avatars1.githubusercontent.com/u/8370623?v=4"
                )
            )
        )
        val users = listOf(
            SearchData("connected", "https://avatars2.githubusercontent.com/u/1562674?v=4"),
            SearchData("aep", "https://avatars3.githubusercontent.com/u/136926?v=4"),
            SearchData("connectedCat", "https://avatars1.githubusercontent.com/u/1493312?v=4"),
            SearchData(
                "connected-react-router",
                "https://avatars3.githubusercontent.com/u/237753?v=4"
            ),
            SearchData("IoT-ConnectedCar", "https://avatars1.githubusercontent.com/u/9148?v=4"),
            SearchData("DenseNet", "https://avatars1.githubusercontent.com/u/8370623?v=4")

        )
        val text = "Hello"
        declareMock<GithubRepository> {
            runBlocking {
                whenever(searchUsers(text)).thenReturn(githubUsers)
                whenever(searchRepositories(text)).thenReturn(githubRepos)
            }
        }
        val viewModel = get<MainViewModel>()

        viewModel.search(text)

        Assert.assertEquals(users, viewModel.searchResult.value)
    }

}
