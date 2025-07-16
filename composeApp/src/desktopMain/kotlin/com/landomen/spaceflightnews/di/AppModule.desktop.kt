package com.landomen.spaceflightnews.di

import com.landomen.spaceflightnews.cache.DatabaseDriverFactory
import com.landomen.spaceflightnews.cache.DesktopDatabaseDriverFactory
import com.landomen.spaceflightnews.share.DesktopShareService
import com.landomen.spaceflightnews.share.ShareService
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<DatabaseDriverFactory> { DesktopDatabaseDriverFactory() }
        single<ShareService> { DesktopShareService() }
    }