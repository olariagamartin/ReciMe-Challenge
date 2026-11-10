package com.themarto.recimechallenge.core.data

import com.themarto.recimechallenge.core.data.database.FakeRecipesStorage
import com.themarto.recimechallenge.core.data.database.RecipesLocalStorage
import com.themarto.recimechallenge.core.data.network.FakeRecipesApi
import com.themarto.recimechallenge.core.data.network.RecipesApi
import com.themarto.recimechallenge.core.data.repository.RecipesRepository
import com.themarto.recimechallenge.core.data.repository.RecipesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModuleProvider {
    fun getModules(): List<Module> = listOf(repositoryModule, networkModule, storageModule)
}

val repositoryModule = module {
    single<RecipesRepository> {
        RecipesRepositoryImpl(
            get(),
            get()
        )
    }
}

val networkModule = module {
    single<RecipesApi> { FakeRecipesApi(androidContext()) }
}

val storageModule = module {
    single<RecipesLocalStorage> {
        FakeRecipesStorage()
    }
}