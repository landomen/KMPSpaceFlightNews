package com.landomen.spaceflightnews.di

import com.landomen.spaceflightnews.network.ApiService
import com.landomen.spaceflightnews.ui.ArticleListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val koinConfig: KoinAppDeclaration = {
    modules(appModule)
}

private val appModule = module {
    single<ApiService> { ApiService() }

    viewModel { ArticleListViewModel(apiService = get()) }
}