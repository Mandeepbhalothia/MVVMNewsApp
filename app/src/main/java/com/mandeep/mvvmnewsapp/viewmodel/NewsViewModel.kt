package com.mandeep.mvvmnewsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mandeep.mvvmnewsapp.db.ArticleDatabase
import com.mandeep.mvvmnewsapp.model.Article
import com.mandeep.mvvmnewsapp.model.NewsResponse
import com.mandeep.mvvmnewsapp.repositories.NewsRepository
import com.mandeep.mvvmnewsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val newsRepository: NewsRepository
    var breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPageNo = 1
    var breakingNewsResponse: NewsResponse? = null
    var searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPageNo = 1
    var searchNewsResponse: NewsResponse? = null

    init {
        val dao = ArticleDatabase.getDatabase(application).getArticleDao()
        newsRepository = NewsRepository(dao)
        getBreakingNews("in")
    }

    fun getBreakingNews(countryCode: String = "us") = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())

        val newsResponse = newsRepository.getBreakingNews(countryCode, breakingNewsPageNo)
        breakingNews.postValue(handleBreakingNewsResponse(newsResponse))

    }

    fun searchForBreakingNews(query: String, pageNo: Int = searchNewsPageNo) =
        viewModelScope.launch {
            searchNews.postValue(Resource.Loading())

            val searchNewsResponse = newsRepository.searchForNews(query, pageNo)
            searchNews.postValue(handleSearchNewsResponse(searchNewsResponse))
        }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPageNo++
                if(breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPageNo++
                if(searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun addArticle(article: Article) = viewModelScope.launch {
        newsRepository.addArticle(article)
    }

    fun getSavedArticle() = newsRepository.getSavedArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}