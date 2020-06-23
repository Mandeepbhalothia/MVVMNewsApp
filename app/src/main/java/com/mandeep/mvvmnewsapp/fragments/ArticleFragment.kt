package com.mandeep.mvvmnewsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mandeep.mvvmnewsapp.NewsActivity
import com.mandeep.mvvmnewsapp.R
import com.mandeep.mvvmnewsapp.model.Article
import com.mandeep.mvvmnewsapp.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_artical.*

class ArticleFragment : Fragment() {

    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artical, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article: Article = args.article

        viewModel = (activity as NewsActivity).newsViewModel

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        fab.setOnClickListener {
            viewModel.addArticle(article)
            Snackbar.make(view,"Article Saved", Snackbar.LENGTH_SHORT).show()
        }

    }
}