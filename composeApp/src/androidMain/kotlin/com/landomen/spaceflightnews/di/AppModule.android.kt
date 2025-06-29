package com.landomen.spaceflightnews.di

import com.landomen.spaceflightnews.cache.AndroidDatabaseDriverFactory
import com.landomen.spaceflightnews.cache.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<DatabaseDriverFactory> { AndroidDatabaseDriverFactory(context = get()) }
    }
