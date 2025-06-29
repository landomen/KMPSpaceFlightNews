package com.landomen.spaceflightnews.cache

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.Properties

class DesktopDatabaseDriverFactory: DatabaseDriverFactory {

    override fun createDriver(): SqlDriver {
        return JdbcSqliteDriver("jdbc:sqlite:articles.db", Properties(), AppDatabase.Schema)
    }
}