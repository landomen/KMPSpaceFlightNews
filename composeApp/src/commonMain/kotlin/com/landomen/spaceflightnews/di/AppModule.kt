package com.landomen.spaceflightnews.di

import com.landomen.spaceflightnews.data.ArticlesRepository
import com.landomen.spaceflightnews.network.ApiService
import com.landomen.spaceflightnews.ui.ArticleListViewModel
import com.landomen.spaceflightnews.ui.details.ArticleDetailsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ApiService> { ApiService() }
    single<ArticlesRepository> {
        ArticlesRepository(
            databaseDriverFactory = get(),
            api = get()
        )
    }

    viewModel { ArticleListViewModel(repository = get()) }
    viewModel { ArticleDetailsViewModel(repository = get()) }
}

expect val platformModule: Module