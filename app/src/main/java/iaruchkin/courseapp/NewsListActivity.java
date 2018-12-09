package iaruchkin.courseapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.net.URL;
import java.util.List;

import iaruchkin.courseapp.data.DataUtils;
import iaruchkin.courseapp.data.NewsItem;

import static iaruchkin.courseapp.NewsDetailsActivity.EXTRA_NEWS_ITEM;

public class NewsListActivity extends AppCompatActivity implements NewsItemAdapter.NewsAdapterOnClickHandler {

    public static final String TAG = NewsListActivity.class.getSimpleName();

    private NewsItemAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mRecyclerView = findViewById(R.id.idRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NewsItemAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        else{
            final int columnsCount = getResources().getInteger(R.integer.landscape_news_columns_count);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, columnsCount));
        }

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        Log.i(TAG, Thread.currentThread().getName());
        GenerateNews();
    }

    private void GenerateNews() {

        new GenerateNewsTask().execute();
    }

    @Override
    public void onClick(NewsItem newsItem) {
        Context context = this;
        Class destinationClass = NewsDetailsActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(EXTRA_NEWS_ITEM,newsItem);
        startActivity(intentToStartDetailActivity);
    }

    public class GenerateNewsTask extends AsyncTask<URL, Void, List<NewsItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRecyclerView.setVisibility(View.INVISIBLE);
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<NewsItem> doInBackground(URL ... params) {
            Log.i(TAG, "doInBackground");
            Log.i(TAG, Thread.currentThread().getName());
            List<NewsItem> generateNewsResult = DataUtils.generateNews();
            return generateNewsResult;
        }

        @Override
        protected void onPostExecute(List<NewsItem> newsItemList) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (newsItemList != null) {
                mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter.setNewsData(newsItemList);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_switch:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
