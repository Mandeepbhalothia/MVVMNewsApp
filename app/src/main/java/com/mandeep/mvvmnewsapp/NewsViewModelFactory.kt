package com.mandeep.mvvmnewsapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mandeep.mvvmnewsapp.viewmodel.NewsViewModel

class NewsViewModelFactory(
    private var application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(application) as T
    }
}