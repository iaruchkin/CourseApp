package iaruchkin.courseapp.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase mSingleton;

    private static final String DATABASE_NAME = "NewsRoomDb.db";

    public abstract NewsDao newsDao();

//    public abstract NewsAsyncDao newsAsyncDao();

    public static AppDatabase getAppDatabase(Context context){
        if (mSingleton == null){
            synchronized (AppDatabase.class){
                if (mSingleton == null){
                    mSingleton = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .build();
                }
            }
        }
        return mSingleton;
    }

}
