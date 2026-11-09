package com.themarto.recimechallenge.data

import com.themarto.recimechallenge.data.database.FakeRecipesStorage
import com.themarto.recimechallenge.data.database.RecipesLocalStorage
import com.themarto.recimechallenge.data.network.FakeRecipesApi
import com.themarto.recimechallenge.data.network.RecipesApi
import com.themarto.recimechallenge.data.repository.RecipesRepository
import com.themarto.recimechallenge.data.repository.RecipesRepositoryImpl
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