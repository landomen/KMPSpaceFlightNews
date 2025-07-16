package com.landomen.spaceflightnews.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landomen.spaceflightnews.data.ArticlesRepository
import com.landomen.spaceflightnews.model.Article
import com.landomen.spaceflightnews.share.ShareService
import com.landomen.spaceflightnews.ui.ErrorType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class ArticleDetailsViewModel(
    private val repository: ArticlesRepository,
    private val shareService: ShareService,
) : ViewModel() {

    private val _state = MutableStateFlow<ArticleDetailsViewState>(ArticleDetailsViewState.Loading)
    val state: StateFlow<ArticleDetailsViewState> = _state

    fun onFetch(id: Long) {
        viewModelScope.launch {
            _state.value = ArticleDetailsViewState.Loading

            try {
                val article = repository.getArticleById(id)
                if (article != null) {
                    _state.value = ArticleDetailsViewState.Success(article)
                } else {
                    _state.value = ArticleDetailsViewState.Error(ErrorType.Unknown)
                }
            } catch (_: Exception) {
                _state.value = ArticleDetailsViewState.Error(ErrorType.Unknown)
            }
        }
    }

    fun onShareClick(article: Article) {
        shareService.share(
            title = article.title,
            url = article.url
        )
    }

    sealed interface ArticleDetailsViewState {
        data object Loading : ArticleDetailsViewState
        data class Success(val article: Article) : ArticleDetailsViewState
        data class Error(val errorType: ErrorType) : ArticleDetailsViewState
    }
}