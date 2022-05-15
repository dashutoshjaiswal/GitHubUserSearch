package com.example.githubusersearch.application

import android.app.Application
import com.example.githubusersearch.data.database.AppDatabase
import com.example.githubusersearch.data.network.GithubApi
import com.example.githubusersearch.data.preference.PreferenceProvider
import com.example.githubusersearch.data.repository.DetailsRepository
import com.example.githubusersearch.data.repository.SearchRepository
import com.example.githubusersearch.ui.details.DetailsViewModelFactory
import com.example.githubusersearch.ui.search.SearchViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { GithubApi() }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { SearchRepository(instance(), instance(), instance(), instance()) }
        bind() from singleton { DetailsRepository(instance(), instance()) }
        bind() from provider { SearchViewModelFactory(instance()) }
        bind() from provider { DetailsViewModelFactory(instance()) }
    }

}