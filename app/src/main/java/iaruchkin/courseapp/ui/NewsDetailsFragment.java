package iaruchkin.courseapp.ui;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.bumptech.glide.util.Preconditions;

import iaruchkin.courseapp.R;
import iaruchkin.courseapp.room.ConverterNews;
import iaruchkin.courseapp.room.NewsEntity;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailsFragment extends Fragment {
    static final String EXTRA_NEWS_ID = "extra:newsID";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public static final String TAG = NewsDetailsFragment.class.getSimpleName();

    WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news_details_webview);

        View view = inflater.inflate(R.layout.activity_news_details_webview, container, false);

//        String newsId = getIntent().getStringExtra(EXTRA_NEWS_ID);
//        String newsId = getArguments().getString(EXTRA_NEWS_ID);
        mWebView = view.findViewById(R.id.web_view);

//        final ActionBar ab = getSupportActionBar();
//        final ActionBar actionBar = Preconditions.checkNotNull(ab);
//
//        actionBar.setDisplayHomeAsUpEnabled(true);

//        loadById(newsId);
        return view;
    }

    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }



//    private void loadById(String id){
//        Disposable loadById = Single.fromCallable(() -> ConverterNews.getNewsById(this, id))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::setView,this::handleError);
//        compositeDisposable.add(loadById);
//    }

    private void setView(NewsEntity newsItem){

//        setTitle(newsItem.getCategory());

        mWebView.loadUrl(newsItem.getUrl());
    }

    private void handleError(Throwable th) {
        Log.e(TAG, th.getMessage(), th);
    }
}
