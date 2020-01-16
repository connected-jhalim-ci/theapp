package com.connected.theapp

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.get
import org.robolectric.RobolectricTestRunner
import java.io.FileInputStream
import java.util.concurrent.TimeUnit


@RunWith(RobolectricTestRunner::class)
class GithubRepositoryTest : KoinTest {

    private val server = MockWebServer()

    @Before
    fun setup() {
        server.start(PORT)
        val url = "http://localhost:$PORT"
        getKoin().setProperty(App.NAME_BASE_URL, url)
    }

    @After
    fun finish() {
        server.shutdown()
    }

    @Test
    fun `search users`() {
        val repository: GithubRepository = get()
        val keyword = "connected"
        server.enqueue(MockResponse().apply {
            setBody(Buffer().readFrom(FileInputStream("src/test/resources/search_users_sample.json")))
        })

        val result = runBlocking { repository.searchUsers(keyword) }

        val request = server.takeRequest(1, TimeUnit.SECONDS)
        assertNotNull(request)
        assertEquals("/search/users", request!!.requestUrl!!.encodedPath)
        assertEquals(keyword, request.requestUrl!!.queryParameter("q"))

        assertEquals(30, result.size)
        assertEquals("connected", result[0].login)
        assertEquals("https://avatars2.githubusercontent.com/u/1562674?v=4", result[0].avatarUrl)
        assertEquals("erinxocon", result[29].login)
        assertEquals("https://avatars1.githubusercontent.com/u/208567?v=4", result[29].avatarUrl)
    }

    @Test
    fun `search repositories`() {
        val repository: GithubRepository = get()
        val keyword = "connected"
        server.enqueue(MockResponse().apply {
            setBody(Buffer().readFrom(FileInputStream("src/test/resources/search_repositories_sample.json")))
        })

        val result = runBlocking { repository.searchRepositories(keyword) }

        val request = server.takeRequest(1, TimeUnit.SECONDS)
        assertNotNull(request)
        assertEquals("/search/repositories", request!!.requestUrl!!.encodedPath)
        assertEquals(keyword, request.requestUrl!!.queryParameter("q"))

        assertEquals(30, result.size)
        assertEquals("connected-react-router", result[0].name)
        assertEquals(
            "https://avatars3.githubusercontent.com/u/237753?v=4",
            result[0].owner.avatarUrl
        )
        assertEquals("marketplaces-connectedpath-php-demo", result[29].name)
        assertEquals(
            "https://avatars1.githubusercontent.com/u/476675?v=4",
            result[29].owner.avatarUrl
        )
    }

    companion object {
        private const val PORT = 8181
    }

}