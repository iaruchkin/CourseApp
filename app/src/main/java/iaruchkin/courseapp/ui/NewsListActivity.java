package iaruchkin.courseapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

import iaruchkin.courseapp.data.NewsCategory;
import iaruchkin.courseapp.network.NetworkSilngleton;
import iaruchkin.courseapp.ui.adapter.CategoryDialog;
import iaruchkin.courseapp.ui.adapter.Mapper;
import iaruchkin.courseapp.ui.adapter.NewsItemAdapter;
import iaruchkin.courseapp.R;
import iaruchkin.courseapp.data.NewsItem;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static iaruchkin.courseapp.ui.NewsDetailsActivity.EXTRA_NEWS_ITEM;

public class NewsListActivity extends AppCompatActivity implements NewsItemAdapter.NewsAdapterOnClickHandler {

    public static final String TAG = NewsListActivity.class.getSimpleName();

    @Nullable
    private NewsItemAdapter mAdapter;
    @Nullable
    private RecyclerView mRecyclerView;
    @Nullable
    private ProgressBar mLoadingIndicator;
    @Nullable
    private View mError;
    @Nullable
    private Button errorAction;
    private Toolbar toolbar;
    private Button mList;
    private CategoryDialog categoryDialog;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mList = findViewById(R.id.button_list);
        toolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.idRecyclerView);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mError = findViewById(R.id.error_layout);
        errorAction = findViewById(R.id.action_button);

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

        errorAction.setOnClickListener(view -> loadItems("home"));

        Log.i(TAG, "OnCreate executed on thread:" + Thread.currentThread().getName());

        categoryDialog = new CategoryDialog();
//        mList.setOnClickListener(view -> categoryDialog.show(getFragmentManager(), "CategoryDialog"));
        mList.setOnClickListener(view -> showDialog(DIALOG));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void loadItems(@NonNull String category){
        mLoadingIndicator.setVisibility(View.VISIBLE);
        final Disposable disposable = NetworkSilngleton.getInstance()
                .topStories()
                .get(category)
                .map(response -> Mapper.map(response.getNews()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateNews, this::handleError);
        compositeDisposable.add(disposable);
    }

    private void updateNews(@Nullable List<NewsItem> news) {

        if (mAdapter != null) {
            mAdapter.replaceItems(news);
        }
        mError.setVisibility(View.GONE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    private void handleError(Throwable th) {
            Log.e(TAG, th.getMessage(), th);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        loadItems(categoryDialog.getSelectedCategory().serverValue());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        compositeDisposable.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        mRecyclerView = null;
    }

    @Override
    public void onClick(NewsItem newsItem) {
        Context context = this;
        Class destinationClass = NewsDetailsActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(EXTRA_NEWS_ITEM,newsItem);
        startActivity(intentToStartDetailActivity);
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
//////////////////////////////////////
    final int DIALOG = 1;
    private NewsCategory selectedCategory = NewsCategory.HOME;
    String items[] = NewsCategory.names();

    public Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this)
                .setTitle("Choose category")
                .setItems(items, (dialog, item) -> {
                    selectedCategory = NewsCategory.valueOf(items[item]);
                    Log.d("Alert Dialog",selectedCategory.serverValue());
                    loadItems(selectedCategory.serverValue());
                });
        return adb.create();
    }

}