package com.connected.theapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

@AllOpen
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    protected fun setupKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(appModule)
            properties(
                mapOf(
                    NAME_BASE_URL to GITHUB_BASE_URL
                )
            )
        }
    }

    companion object {
        private val appModule = module {
            viewModel { MainViewModel(get()) }
            factory<GithubRepository> { GithubRepositoryImpl(get()) }
            factory { GithubService.create(getProperty(NAME_BASE_URL)) }
        }
        const val NAME_BASE_URL = "base_url"
        private const val GITHUB_BASE_URL = "https://api.github.com/"
    }

}
