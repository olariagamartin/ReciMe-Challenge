package com.themarto.recimechallenge

import android.app.Application
import com.themarto.recimechallenge.data.DataModuleProvider
import com.themarto.recimechallenge.features.explore.ExploreModuleProvider
import com.themarto.recimechallenge.features.recipe_details.DetailsModuleProvider
import com.themarto.recimechallenge.features.saved.SavedModuleProvider
import com.themarto.recimechallenge.features.search.SearchModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                ExploreModuleProvider.getModules() +
                        DetailsModuleProvider.getModules() +
                        SearchModuleProvider.getModules() +
                        SavedModuleProvider.getModules() +
                        DataModuleProvider.getModules()
            )
        }
    }
}