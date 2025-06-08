package com.landomen.spaceflightnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landomen.spaceflightnews.model.Article
import com.landomen.spaceflightnews.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleListViewModel(private val apiService: ApiService) : ViewModel(){
    private val _state = MutableStateFlow(ArticleListViewState())
    val state: StateFlow<ArticleListViewState> = _state

    init {
        viewModelScope.launch {
            val articles = apiService.getArticles()
            _state.value = _state.value.copy(articles = articles.filter { !it.imageUrl.isEmpty() })
        }
    }
}

data class ArticleListViewState(
    val articles: List<Article> = emptyList(),
)