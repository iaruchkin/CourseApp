package iaruchkin.courseapp.presentation.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import iaruchkin.courseapp.App;
import iaruchkin.courseapp.common.BasePresenter;
import iaruchkin.courseapp.common.State;
import iaruchkin.courseapp.network.NetworkSilngleton;
import iaruchkin.courseapp.network.TopStoriesResponse;
import iaruchkin.courseapp.presentation.view.NewsListView;
import iaruchkin.courseapp.room.ConverterNews;
import iaruchkin.courseapp.room.NewsEntity;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static iaruchkin.courseapp.ui.MainActivity.NEWS_LIST_TAG;

@InjectViewState
public class NewsListPresenter extends BasePresenter<NewsListView> {
    private Context context = App.INSTANCE.getApplicationContext();
//    private Resources resources = App.INSTANCE.getResources();
    private NetworkSilngleton restApi;
    private String category = "home";
    public NewsListPresenter(@NonNull NetworkSilngleton instance) {
        this.restApi = instance;
    }

    @Override
    protected void onFirstViewAttach() {

    }


    public void loadNews(String category){
        this.category = category;
        loadFromDb(category);
    }

    private void loadFromDb(String category){
        getViewState().showState(State.Loading);
        Disposable loadFromDb = Single.fromCallable(() -> ConverterNews
                .loadNewsFromDb(context, category))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateNews, this::handleError);
        disposeOnDestroy(loadFromDb);
    }

    private void updateNews(@Nullable List<NewsEntity> news) {
        if (news.size()==0){
            loadFromNet(category);
            Log.e(NEWS_LIST_TAG, "there is no news in category : " + category);
        }else {
            getViewState().showNews(news);
            getViewState().showState(State.HasData);
            Log.e(NEWS_LIST_TAG, "loaded from DB: " + news.get(0).getCategory() + " / " + news.get(0).getTitle());
            Log.e(NEWS_LIST_TAG, "updateNews executed on thread: " + Thread.currentThread().getName());
        }
    }

    private void loadFromNet(@NonNull String category){
        getViewState().showState(State.Loading);
        final Disposable disposable = NetworkSilngleton.getInstance()
                .topStories()
                .get(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateDB, this::handleError);
        disposeOnDestroy(disposable);
    }

    private void updateDB(TopStoriesResponse response) {
        if (response.getNews().size() == 0) {
            getViewState().showState(State.HasNoData);
        } else {
            Disposable saveNewsToDb = Single.fromCallable(response::getNews)
                    .subscribeOn(Schedulers.io())
                    .map(newsDTO -> {
                        ConverterNews.saveAllNewsToDb(context, ConverterNews
                                .dtoToDao(newsDTO, category),category);
                        return ConverterNews.loadNewsFromDb(context, category);
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            newsEntities -> {
                                getViewState().showNews(newsEntities);
                                Log.e(NEWS_LIST_TAG, "loaded from NET to DB: " + newsEntities.get(0).getCategory() + " / " + newsEntities.get(0).getTitle());
                            });
            disposeOnDestroy(saveNewsToDb);
            getViewState().showState(State.HasData);
        }
    }

    private void handleError(Throwable th) {
        getViewState().showState(State.NetworkError);
        Log.w(NEWS_LIST_TAG, th.getMessage(), th);
        Log.e(NEWS_LIST_TAG, "handleError executed on thread: " + Thread.currentThread().getName());
    }
}
