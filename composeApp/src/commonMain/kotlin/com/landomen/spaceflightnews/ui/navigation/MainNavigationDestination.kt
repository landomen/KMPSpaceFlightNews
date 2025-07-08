package com.landomen.spaceflightnews.ui.navigation

import kotlinx.serialization.Serializable

sealed interface MainNavigationDestination {
    @Serializable
    data object Home : MainNavigationDestination
    @Serializable
    data class Details(val articleId: Long) : MainNavigationDestination
}