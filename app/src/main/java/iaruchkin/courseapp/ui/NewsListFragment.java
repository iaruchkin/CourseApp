package iaruchkin.courseapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.List;

import iaruchkin.courseapp.data.NewsCategory;
import iaruchkin.courseapp.network.NetworkSilngleton;
import iaruchkin.courseapp.network.TopStoriesResponse;
import iaruchkin.courseapp.room.ConverterNews;
import iaruchkin.courseapp.room.NewsEntity;
import iaruchkin.courseapp.ui.adapter.CategoriesSpinnerAdapter;
import iaruchkin.courseapp.ui.adapter.NewsItemAdapter;
import iaruchkin.courseapp.R;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class NewsListFragment extends Fragment implements NewsItemAdapter.NewsAdapterOnClickHandler {

    public static final String TAG = NewsListFragment.class.getSimpleName();
    private static final int LAYOUT = R.layout.activity_news_list;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        Log.e(TAG, "OnCreateView executed on thread:" + Thread.currentThread().getName());

        View view = inflater.inflate(LAYOUT, container, false);

        setupUi(view);
        setupUx();

        return view;
    }

    private void setupUi(View view) {
        findViews(view);
        setupToolbar(getContext());
        setupSpinner();
        setupOrientation(mRecyclerView);
        setupRecyclerViewAdapter();
    }

    private void setupToolbar(Context context) {
        ((AppCompatActivity)context).setSupportActionBar(toolbar);
        ((AppCompatActivity)context).getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupRecyclerViewAdapter(){
        mAdapter = new NewsItemAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setupOrientation(RecyclerView recyclerView) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            final int columnsCount = getResources().getInteger(R.integer.landscape_news_columns_count);
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), columnsCount));
        }
    }

    private void setupSpinner() {
        final NewsCategory[] categories = NewsCategory.values();
        categoriesAdapter = CategoriesSpinnerAdapter.createDefault(getContext(), categories);
        spinnerCategories.setAdapter(categoriesAdapter);
    }

    private void setupUx() {
        mUpdate.setOnClickListener(v -> loadFromDb(getNewsCategory()));

        errorAction.setOnClickListener(v -> loadFromDb(getNewsCategory()));

        categoriesAdapter.setOnCategorySelectedListener(category -> loadFromDb(getNewsCategory()), spinnerCategories);
//        categoriesAdapter.setOnCategorySelectedListener(NewsCategory::serverValue, spinnerCategories);

    }

    private void loadFromDb(String category){
        Disposable loadFromDb = Single.fromCallable(() -> ConverterNews.loadNewsFromDb(getContext(), category))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateNews, this::handleError);
        compositeDisposable.add(loadFromDb);
    }

    private void updateNews(@Nullable List<NewsEntity> news) {
        if (news.size()==0){
            loadFromNet(getNewsCategory());
        }else {
            if (mAdapter != null) {
                mAdapter.replaceItems(news);
            }
            mError.setVisibility(View.GONE);
            mLoadingIndicator.setVisibility(View.GONE);
            Log.e(TAG, "updateNews executed on thread:" + Thread.currentThread().getName());
        }
    }

    private void loadFromNet(@NonNull String category){
        mLoadingIndicator.setVisibility(View.VISIBLE);
        final Disposable disposable = NetworkSilngleton.getInstance()
                .topStories()
                .get(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDB, this::handleError);
        compositeDisposable.add(disposable);
    }

    public void updateDB(TopStoriesResponse response) {
        mLoadingIndicator.setVisibility(View.GONE);
        Disposable saveNewsToDb = Single.fromCallable(response::getNews)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        newsEntities -> {
                            ConverterNews.saveAllNewsToDb(getContext(), ConverterNews.dtoToDao(newsEntities, getNewsCategory()));
                        });
        compositeDisposable.add(saveNewsToDb);

    }

    private String getNewsCategory() {
        return categoriesAdapter.getSelectedCategory().serverValue();
    }

    private void handleError(Throwable th) {
        Log.w(TAG, th.getMessage(), th);
        mLoadingIndicator.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);

        Log.e(TAG, "handleError executed on thread:" + Thread.currentThread().getName());

    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart");
        super.onStart();
        loadFromDb(getNewsCategory());
    }

    @Override
    public void onStop() {
        super.onStop();
        mLoadingIndicator.setVisibility(View.GONE);
        compositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        mRecyclerView = null;
    }

    @Override
    public void onClick(NewsEntity newsItem) {
//        Class destinationClass = NewsDetailsFragment.class;
//        Intent intentToStartDetailActivity = new Intent(getContext(), destinationClass);
//        intentToStartDetailActivity.putExtra(EXTRA_NEWS_ID,newsItem.getId());
//        startActivity(intentToStartDetailActivity);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.about_menu, menu);
    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_switch:
//                startActivity(new Intent(getContext(), AboutActivity.class));
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_about:
//                mListener.onNewsItemClicked(MainActivity.TAG_ABOUT, null);
//                break;
//            default: throw new IllegalArgumentException(getString(R.string.error_no_id)+": "+item.getItemId());
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void findViews(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        mRecyclerView = view.findViewById(R.id.idRecyclerView);
        mLoadingIndicator = view.findViewById(R.id.pb_loading_indicator);
        mError = view.findViewById(R.id.error_layout);
        errorAction = view.findViewById(R.id.action_button);
        spinnerCategories = view.findViewById(R.id.spinner_categories);
        mUpdate = view.findViewById(R.id.floatingActionButton);
    }
}