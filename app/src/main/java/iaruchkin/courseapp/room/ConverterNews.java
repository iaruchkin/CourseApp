package iaruchkin.courseapp.room;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import iaruchkin.courseapp.network.NewsDTO;

public class ConverterNews {

    private final static int LIST_IMAGE_SIZE = 1;
    public final static String KEY_NO_IMAGE = "no";

    public static List<NewsEntity> dtoToDao(List<NewsDTO> listDto, String newsCategory){
        List<NewsEntity> listDao = new ArrayList<>();
        for (NewsDTO dto : listDto){
            NewsEntity newsEntity = new NewsEntity();
            newsEntity.setId(dto.getUrl()+newsCategory);
            newsEntity.setUrl(dto.getUrl());
            newsEntity.setCategory(newsCategory);
//            newsEntity.setSubsection(dto.getSubsection());
            newsEntity.setTitle(dto.getTitle());
            newsEntity.setPublishedDate(dto.getPublishDate().toString());
//            newsEntity.setPreviewText(dto.getPreviewText());
            if (dto.getMultimedia().size() != 0){
                newsEntity.setImageUrl(dto.getMultimedia().get(LIST_IMAGE_SIZE).getUrl());
            } else {
                newsEntity.setImageUrl(KEY_NO_IMAGE);
            }
            listDao.add(newsEntity);
        }
        return listDao;
    }

    public static NewsEntity getNewsById(Context context, String id){
        AppDatabase db = AppDatabase.getAppDatabase(context);
        return db.newsDao().getNewsById(id);
    }

    public static List<NewsEntity> loadNewsFromDb(Context context, String category) {
        AppDatabase db = AppDatabase.getAppDatabase(context);
        return db.newsDao().getAll(category);
    }

    public static void saveAllNewsToDb(Context context, List<NewsEntity> list){
        AppDatabase db = AppDatabase.getAppDatabase(context);
        db.newsDao().deleteAll();

        NewsEntity news[] = list.toArray(new NewsEntity[list.size()]);
        db.newsDao().insertAll(news);
        Log.e("Room DB", "data saved");
    }

    public static void editNewsToDb(Context context, NewsEntity newsEntity){
        AppDatabase db = AppDatabase.getAppDatabase(context);
        db.newsDao().edit(newsEntity);
    }

    public static void deleteNewsFromDb(Context context, NewsEntity newsEntity){
        AppDatabase db = AppDatabase.getAppDatabase(context);
        db.newsDao().delete(newsEntity);
    }
}