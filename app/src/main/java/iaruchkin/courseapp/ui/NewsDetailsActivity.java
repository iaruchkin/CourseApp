package iaruchkin.courseapp.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.bumptech.glide.util.Preconditions;

import iaruchkin.courseapp.R;
import iaruchkin.courseapp.data.NewsItem;

public class NewsDetailsActivity extends AppCompatActivity {
    static final String EXTRA_NEWS_ITEM = "extra:newsItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        NewsItem newsItem = (NewsItem) getIntent().getSerializableExtra(EXTRA_NEWS_ITEM);
        WebView mWebView = findViewById(R.id.web_view);

        final ActionBar ab = getSupportActionBar();
        final ActionBar actionBar = Preconditions.checkNotNull(ab);

        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle(newsItem.getCategory());

        mWebView.loadUrl(newsItem.getUrl());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
