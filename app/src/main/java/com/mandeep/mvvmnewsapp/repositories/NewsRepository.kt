package com.mandeep.mvvmnewsapp.repositories

import com.mandeep.mvvmnewsapp.api.RetrofitInstance
import com.mandeep.mvvmnewsapp.db.ArticleDao
import com.mandeep.mvvmnewsapp.model.Article

class NewsRepository(private val dao: ArticleDao) {

    suspend fun getBreakingNews(countryCode: String, pageNo: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNo)

    suspend fun searchForNews(query: String, pageNo: Int) =
        RetrofitInstance.api.searchForNews(query, pageNo)

    suspend fun addArticle(article: Article) = dao.upsert(article)

    fun getSavedArticles() = dao.getAllArticles()

    suspend fun deleteArticle(article: Article) = dao.deleteArticle(article)

}