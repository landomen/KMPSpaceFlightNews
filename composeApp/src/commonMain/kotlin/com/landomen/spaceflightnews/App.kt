package com.landomen.spaceflightnews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.landomen.spaceflightnews.di.appModule
import com.landomen.spaceflightnews.di.platformModule
import com.landomen.spaceflightnews.ui.ArticleListScreen
import com.landomen.spaceflightnews.ui.details.ArticleDetailsScreen
import com.landomen.spaceflightnews.ui.navigation.MainNavigationDestination
import com.landomen.spaceflightnews.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration


@Composable
@Preview
fun App(koinAppDeclaration: KoinAppDeclaration? = null) {
    KoinApplication(application = {
        koinAppDeclaration?.invoke(this)
        modules(appModule, platformModule)
    }) {
        AppTheme {
            MainNavigationHost()
        }
    }
}

@Composable
private fun MainNavigationHost() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = MainNavigationDestination.Home,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<MainNavigationDestination.Home> {
            ArticleListScreen(onArticleClick = { articleId ->
                navController.navigate(MainNavigationDestination.Details(articleId))
            })
        }
        composable<MainNavigationDestination.Details> {
            val route = it.toRoute<MainNavigationDestination.Details>()
            ArticleDetailsScreen(
                articleId = route.articleId,
                onBackClick = {
                    navController.popBackStack()
                })
        }
    }
}
