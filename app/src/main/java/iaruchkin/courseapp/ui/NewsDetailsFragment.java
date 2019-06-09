package iaruchkin.courseapp.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import iaruchkin.courseapp.R;
import io.reactivex.disposables.CompositeDisposable;

public class NewsDetailsFragment extends Fragment {
    static final String EXTRA_ITEM_URL = "extra:itemURL";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public static final String TAG = NewsDetailsFragment.class.getSimpleName();
    private MessageFragmentListener listener;

    WebView mWebView;


    public static NewsDetailsFragment newInstance(String itemURL){
        NewsDetailsFragment fragmentFullNews = new NewsDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_ITEM_URL, itemURL);
        fragmentFullNews.setArguments(bundle);
        return fragmentFullNews;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news_details_webview, container, false);
        mWebView = view.findViewById(R.id.web_view);
        setView(getArguments().getString(EXTRA_ITEM_URL));

        return view;
    }

    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MessageFragmentListener){
            listener = (MessageFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    private void setView(String itemURL){
        if (itemURL != null) {
        mWebView.loadUrl(itemURL);
        }
    }

    private void handleError(Throwable th) {
        Log.e(TAG, th.getMessage(), th);
    }
}
