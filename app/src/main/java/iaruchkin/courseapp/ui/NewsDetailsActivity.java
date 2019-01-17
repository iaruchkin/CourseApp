package iaruchkin.courseapp.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class NewsDetailsActivity extends AppCompatActivity {
    static final String EXTRA_NEWS_ID = "extra:newsID";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        String newsId = getIntent().getStringExtra(EXTRA_NEWS_ID);
        mWebView = findViewById(R.id.web_view);

        final ActionBar ab = getSupportActionBar();
        final ActionBar actionBar = Preconditions.checkNotNull(ab);

        actionBar.setDisplayHomeAsUpEnabled(true);

        loadById(newsId);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadById(String id){
        Disposable loadById = Single.fromCallable(() -> ConverterNews.getNewsById(this, id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setView);
        compositeDisposable.add(loadById);
    }

    private void setView(NewsEntity newsItem){

        setTitle(newsItem.getCategory());

        mWebView.loadUrl(newsItem.getUrl());
    }

}
