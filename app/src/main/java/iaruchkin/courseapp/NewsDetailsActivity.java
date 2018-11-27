package iaruchkin.courseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import iaruchkin.courseapp.data.DataUtils;
import iaruchkin.courseapp.data.NewsItem;

public class NewsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        final int position = getIntent().getIntExtra("position",0);
        NewsItem newsItem = DataUtils.generateNews().get(position);

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
