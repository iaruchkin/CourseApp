package iaruchkin.courseapp.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface NewsDao {

    @Query("SELECT * FROM news")
    List<NewsEntity> getAll();

    @Query("SELECT * FROM news WHERE category = :category")
    List<NewsEntity> getAll(String category);

    @Query("SELECT * FROM news WHERE id = :id")
    NewsEntity getNewsById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NewsEntity... newsEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsEntity newsEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void edit(NewsEntity newsEntity);

    @Delete
    void delete(NewsEntity newsEntity);

    @Query("DELETE FROM news")
    void deleteAll();

}
