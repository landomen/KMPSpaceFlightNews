package com.landomen.spaceflightnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landomen.spaceflightnews.model.Article
import com.landomen.spaceflightnews.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.io.IOException

class ArticleListViewModel(private val apiService: ApiService) : ViewModel() {
    private val _state = MutableStateFlow<ArticleListViewState>(ArticleListViewState.Loading)
    val state: StateFlow<ArticleListViewState> = _state

    init {
        fetchArticles()
    }

    fun fetchArticles() {
        viewModelScope.launch {
            _state.value = ArticleListViewState.Loading
            try {
                val articles = apiService.getArticles()
                    .filter { it.imageUrl.isNotEmpty() }
                _state.value = ArticleListViewState.Success(articles)
            } catch (e: IOException) {
                _state.value = ArticleListViewState.Error(e.message)
            } catch (e: Exception) {
                _state.value = ArticleListViewState.Error(e.message)
            }
        }
    }
}



sealed interface ArticleListViewState {
    data object Loading : ArticleListViewState
    data class Success(val articles: List<Article> = emptyList()) : ArticleListViewState
    data class Error(val message: String? = null) : ArticleListViewState
}
