package com.landomen.spaceflightnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.network.HttpException
import com.landomen.spaceflightnews.data.ArticlesRepository
import com.landomen.spaceflightnews.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.io.IOException

internal class ArticleListViewModel(private val repository: ArticlesRepository) : ViewModel() {
    private val _state = MutableStateFlow<ArticleListViewState>(ArticleListViewState.Loading)
    val state: StateFlow<ArticleListViewState> = _state

    init {
        fetchArticles()
    }

    fun fetchArticles() {
        viewModelScope.launch {
            _state.value = ArticleListViewState.Loading
            try {
                val articles = repository.getArticles()
                    .filter { it.imageUrl.isNotEmpty() }
                _state.value = ArticleListViewState.Success(articles)
            } catch (_: IOException) {
                _state.value = ArticleListViewState.Error(ErrorType.NoInternet)
            } catch (_: HttpException) {
                _state.value = ArticleListViewState.Error(ErrorType.ServerError)
            } catch (e: Exception) {
                _state.value = ArticleListViewState.Error(ErrorType.Unknown)
            }
        }
    }

}

sealed interface ArticleListViewState {
    data object Loading : ArticleListViewState
    data class Success(val articles: List<Article> = emptyList()) : ArticleListViewState
    data class Error(val errorType: ErrorType) : ArticleListViewState
}

sealed class ErrorType {
    data object NoInternet : ErrorType()
    data object ServerError : ErrorType()
    data object Unknown : ErrorType()
}