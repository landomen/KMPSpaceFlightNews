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
    private val _state = MutableStateFlow(ArticleListViewState())
    val state: StateFlow<ArticleListViewState> = _state

    init {
        fetchArticles()
    }

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(error = null) // Clear previous error
                val articles = apiService.getArticles()
                _state.value = _state.value.copy(
                    articles = articles.filter { it.imageUrl.isNotEmpty() }
                )
            } catch (e: IOException) {
                _state.value = _state.value.copy(error = "No internet connection.")
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Something went wrong.")
            }
        }
    }
}



data class ArticleListViewState(
    val articles: List<Article> = emptyList(),
    val error: String? = null
)