package iaruchkin.courseapp.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [NewsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        private var mSingleton: AppDatabase? = null

        private val DATABASE_NAME = "NewsRoomDb.db"

        //    public abstract NewsAsyncDao newsAsyncDao();

        fun getAppDatabase(context: Context): AppDatabase? {
            if (mSingleton == null) {
                synchronized(AppDatabase::class.java) {
                    if (mSingleton == null) {
                        mSingleton = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                DATABASE_NAME)
                                .build()
                    }
                }
            }
            return mSingleton
        }
    }

}
