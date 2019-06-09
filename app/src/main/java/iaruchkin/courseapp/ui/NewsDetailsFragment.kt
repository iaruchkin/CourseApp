package iaruchkin.courseapp.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

import iaruchkin.courseapp.R
import io.reactivex.disposables.CompositeDisposable

class NewsDetailsFragment : Fragment() {
    private val compositeDisposable = CompositeDisposable()
    private var listener: MessageFragmentListener? = null

    internal var mWebView: WebView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_news_details_webview, container, false)
        mWebView = view.findViewById(R.id.web_view)
        setView(arguments!!.getString(EXTRA_ITEM_URL))

        return view
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MessageFragmentListener) {
            listener = context
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    private fun setView(itemURL: String?) {
        if (itemURL != null) {
            mWebView?.loadUrl(itemURL)
        }
    }

    private fun handleError(th: Throwable) {
        Log.e(TAG, th.message, th)
    }

    companion object {
        internal val EXTRA_ITEM_URL = "extra:itemURL"
        val TAG = NewsDetailsFragment::class.java.simpleName


        fun newInstance(itemURL: String): NewsDetailsFragment {
            val fragmentFullNews = NewsDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(EXTRA_ITEM_URL, itemURL)
            fragmentFullNews.arguments = bundle
            return fragmentFullNews
        }
    }
}
