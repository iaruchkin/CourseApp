package iaruchkin.courseapp.room;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import iaruchkin.courseapp.network.NewsDTO;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class NewsRepository {

    private final Context mContext;

    public NewsRepository(Context mContext) {
        this.mContext = mContext;
    }

    private final static int LIST_IMAGE_SIZE = 1;
    public final static String KEY_NO_IMAGE = "no";

    public static List<NewsEntity> dtoToDao(List<NewsDTO> listDto, String newsCategory) {
        List<NewsEntity> listDao = new ArrayList<>();
        for (NewsDTO dto : listDto) {
            NewsEntity newsEntity = new NewsEntity();
            newsEntity.setId(dto.getUrl() + newsCategory);
            newsEntity.setUrl(dto.getUrl());
            newsEntity.setCategory(newsCategory);
//            newsEntity.setSubsection(dto.getSubsection());
            newsEntity.setTitle(dto.getTitle());
            newsEntity.setPublishedDate(dto.getPublishDate().toString());
//            newsEntity.setPreviewText(dto.getPreviewText());
            if (dto.getMultimedia().size() != 0) {
                newsEntity.setImageUrl(dto.getMultimedia().get(LIST_IMAGE_SIZE).getUrl());
            } else {
                newsEntity.setImageUrl(KEY_NO_IMAGE);
            }
            listDao.add(newsEntity);
        }
        return listDao;
    }

    Completable saveNews(final List<NewsEntity> newsEntitiesList) {
        return Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                AppDatabase db = AppDatabase.getAppDatabase(mContext);

                db.newsDao().deleteAll();

                NewsEntity[] news = newsEntitiesList.toArray(new NewsEntity[newsEntitiesList.size()]);

                db.newsDao().insertAll(news);

                return null;
            }
        });
    }

    Single<List<NewsEntity>> loadNews(String category) {

        return Single.fromCallable(new Callable<List<NewsEntity>>() {
            @Override
            public List<NewsEntity> call() throws Exception {
                AppDatabase db = AppDatabase.getAppDatabase(mContext);

                return db.newsDao().getAll(category);
            }
        });
    }

    Single<List<NewsEntity>> loadAll() {

        return Single.fromCallable(new Callable<List<NewsEntity>>() {
            @Override
            public List<NewsEntity> call() throws Exception {
                AppDatabase db = AppDatabase.getAppDatabase(mContext);

                return db.newsDao().getAll();
            }
        });
    }


    Single<NewsEntity> loadNewsById(String id) {

        return Single.fromCallable(new Callable<NewsEntity>() {
            @Override
            public NewsEntity call() throws Exception {
                AppDatabase db = AppDatabase.getAppDatabase(mContext);

                return db.newsDao().getNewsById(id);
            }
        });
    }

//    Observable<List<NewsEntity>> getDataObservable() {
//        AppDatabase db = AppDatabase.getAppDatabase(mContext);
//
//        return db.newsAsyncDao().getAll();
//    }

}
