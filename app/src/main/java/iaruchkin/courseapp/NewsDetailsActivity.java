package iaruchkin.courseapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import iaruchkin.courseapp.data.DataUtils;
import iaruchkin.courseapp.data.NewsItem;

public class NewsDetailsActivity extends AppCompatActivity {
    static final String EXTRA_NEWS_ITEM = "extra:newsItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        NewsItem newsItem = (NewsItem) getIntent().getSerializableExtra(EXTRA_NEWS_ITEM);

        setTitle(newsItem.getCategory().getName());

        ImageView mImgFull = findViewById(R.id.img_full);
        TextView mTitle = findViewById(R.id.item_title);
        TextView mDate = findViewById(R.id.item_date);
        TextView mFullText = findViewById(R.id.item_text);

        Glide.with(this).load(newsItem.getImageUrl()).into(mImgFull);
        mTitle.setText(newsItem.getTitle());
        mDate.setText(newsItem.getPublishDate().toString());
        mFullText.setText(newsItem.getFullText());
    }
}
