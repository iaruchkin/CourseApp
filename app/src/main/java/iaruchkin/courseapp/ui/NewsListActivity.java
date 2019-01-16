package iaruchkin.courseapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import iaruchkin.courseapp.data.NewsCategory;
import iaruchkin.courseapp.network.NetworkSilngleton;
import iaruchkin.courseapp.network.NewsDTO;
import iaruchkin.courseapp.network.TopStoriesResponse;
import iaruchkin.courseapp.room.AppDatabase;
import iaruchkin.courseapp.room.ConverterNews;
import iaruchkin.courseapp.room.NewsDao;
import iaruchkin.courseapp.room.NewsEntity;
import iaruchkin.courseapp.room.NewsRepository;
import iaruchkin.courseapp.ui.adapter.CategoriesSpinnerAdapter;
import iaruchkin.courseapp.ui.adapter.Mapper;
import iaruchkin.courseapp.ui.adapter.NewsItemAdapter;
import iaruchkin.courseapp.R;
import iaruchkin.courseapp.data.NewsItem;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
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
    @Nullable
    private FloatingActionButton mUpdate;
    @Nullable
    private Toolbar toolbar;
    @Nullable
    private Spinner spinnerCategories;
    private CategoriesSpinnerAdapter categoriesAdapter;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private NewsRepository newsRepository;
    private static List<NewsEntity> mNewsItems = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        newsRepository = new NewsRepository(this.getApplicationContext());

        toolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.idRecyclerView);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mError = findViewById(R.id.error_layout);
        errorAction = findViewById(R.id.action_button);
        spinnerCategories = findViewById(R.id.spinner_categories);
        mUpdate = findViewById(R.id.floatingActionButton);

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

        setupSpinner();

        mUpdate.setOnClickListener(view -> loadFromNet(getNewsCategory()));

        errorAction.setOnClickListener(view -> loadFromNet(categoriesAdapter.getSelectedCategory().serverValue()));

        Log.i(TAG, "OnCreate executed on thread:" + Thread.currentThread().getName());

        categoriesAdapter.setOnCategorySelectedListener(category -> loadFromNet(category.serverValue()), spinnerCategories);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
//тут грузим из сети в БД TODO
    private void loadFromNet(@NonNull String category){
        mLoadingIndicator.setVisibility(View.VISIBLE);
        final Disposable disposable = NetworkSilngleton.getInstance()
                .topStories()
                .get(category)
//                .map(response -> Mapper.map(response.getNews()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDB, this::handleError);
        compositeDisposable.add(disposable);
    }

    public void updateDB(TopStoriesResponse response) {

//        Disposable disposable = NewsRepository.saveNews()
        Disposable saveNewsToDb = Single.fromCallable(response::getNews)
                .subscribeOn(Schedulers.io())
//                .map(listResultDto ->{
//                    ConverterNews.dtoToDao(listResultDto, getNewsCategory());
//                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        newsEntities -> {
                            ConverterNews.saveAllNewsToDb(this, ConverterNews.dtoToDao(newsEntities, getNewsCategory()));
                        });
        compositeDisposable.add(saveNewsToDb);
    }

//    private void loadToDb() {
//        progressBarProgress = 0;
//        progressBar.setProgress(progressBarProgress);
//        compositeDisposable.add(
//                TopStoriesApi.getInstance()
//                        .topStories().get(NewsTypes.valueOf(categoryName))
//                        .map(NewsItemHelper::parseToDaoArray)
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(
//                                newsEntities -> {
//                                    newsDao.deleteAll();
//                                    progressStep = 100/newsEntities.length;
//                                    newsDao.insertAll(newsEntities);
//                                },
//                                this::logItemError
//                        ));
//        Log.i(TAG, "Writing items to Database");
//    }

    //тут грузим из БД в ресайклер TODO
//    private void loadFromDb(String section){
//        Disposable loadFromDb = Single.fromCallable(() -> ConverterNews.loadNewsFromDb(this, section))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::checkResponseDb, this::handleError);
//        mCompositeDisposable.add(loadFromDb);
//    }

    private void updateNews(@Nullable List<NewsItem> news) {

        if (mAdapter != null) {
            mAdapter.replaceItems(news);
        }
        mError.setVisibility(View.GONE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    private String getNewsCategory() {
        return categoriesAdapter.getSelectedCategory().serverValue();
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
//        loadFromNet(categoriesAdapter.getSelectedCategory().serverValue());
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

    private void setupSpinner() {
        final NewsCategory[] categories = NewsCategory.values();
        categoriesAdapter = CategoriesSpinnerAdapter.createDefault(this, categories);
        spinnerCategories.setAdapter(categoriesAdapter);
    }

}