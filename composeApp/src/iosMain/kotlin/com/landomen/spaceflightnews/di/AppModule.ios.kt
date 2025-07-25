package com.landomen.spaceflightnews.di

import com.landomen.spaceflightnews.cache.DatabaseDriverFactory
import com.landomen.spaceflightnews.cache.IOSDatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module { single<DatabaseDriverFactory> { IOSDatabaseDriverFactory() } }