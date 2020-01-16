package com.connected.theapp

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.nhaarman.mockitokotlin2.given
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest : KoinTest {

    @get:Rule
    val uiRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    private val testSearchResult: MutableLiveData<List<SearchData>> = MutableLiveData()
    private val mockViewModel: MainViewModel by uiRule.viewModel()

    @Before
    fun setup() {
        declareMock<MainViewModel> {
            given(searchResult).willReturn(testSearchResult)
        }
        uiRule.launchActivity(null)
    }

    @Test
    fun `search user`() {
        //setup
        val text = "hello"

        onView(withId(R.id.search_text)).perform(typeText(text))
        onView(withId(R.id.search_button)).perform(click())

        //verify
        Mockito.verify(mockViewModel).search(text)
    }

    @Test
    fun `display search result`() {
        val results = listOf(
            SearchData("connected", "https://avatars2.githubusercontent.com/u/1562674?v=4"),
            SearchData("aep", "https://avatars3.githubusercontent.com/u/136926?v=4"),
            SearchData("connectedCat", "https://avatars1.githubusercontent.com/u/1493312?v=4")
        )

        testSearchResult.value = results

        for (i in 0..2) {
            searchResultAvatar(i).check(matches(withContentDescription(results[i].imageUrl)))
            searchResultLogin(i).check(matches(withText(results[i].title)))
        }


    }

    private fun searchResultLogin(position: Int) =
        onView(RecyclerViewMatcher(R.id.search_result).atPositionOnView(position, R.id.login))

    private fun searchResultAvatar(position: Int) =
        onView(RecyclerViewMatcher(R.id.search_result).atPositionOnView(position, R.id.avatar))

}