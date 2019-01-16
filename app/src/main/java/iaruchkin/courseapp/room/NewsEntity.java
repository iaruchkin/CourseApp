package iaruchkin.courseapp.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import iaruchkin.courseapp.data.NewsItem;

@Entity(tableName = "news")
public class NewsEntity {

    public NewsEntity() {
    }

    @Ignore
    public NewsEntity(@NonNull NewsItem newsItem) {
        this.mCategory = newsItem.getCategory();
        this.mImageUrl = newsItem.getImageUrl();
        this.mTitle = newsItem.getTitle();
        this.mPublishedDate = newsItem.getPublishDate().toString();
        this.mUrl = newsItem.getUrl();
//        this.mFullText = newsItem.getFullText();
        this.mPreviewText = newsItem.getPreviewText();
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;

    @NonNull
    @ColumnInfo(name = "category")
    private String mCategory;

    @NonNull
    @ColumnInfo(name = "subsection")
    private String mSubsection;

    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;

    @NonNull
    @ColumnInfo(name = "preview")
    private String mPreviewText;

    @NonNull
    @ColumnInfo(name = "url")
    private String mUrl;

    @NonNull
    @ColumnInfo(name = "publisheddate")
    private String mPublishedDate;

    @NonNull
    @ColumnInfo(name = "imageurl")
    private String mImageUrl;

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getCategory() {
        return mCategory;
    }

    @NonNull
    public String getSubsection() {
        return mSubsection;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getPreviewText() {
        return mPreviewText;
    }

    @NonNull
    public String getUrl() {
        return mUrl;
    }

    @NonNull
    public String getPublishedDate() {
        return mPublishedDate;
    }

    @NonNull
    public String getImageUrl() {
        return mImageUrl;
    }

    public void setId(@NonNull String id) {
        mId = id;
    }

    public void setCategory(@NonNull String section) {
        mCategory = section;
    }

    public void setSubsection(@NonNull String subsection) {
        mSubsection = subsection;
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
    }

    public void setPreviewText(@NonNull String previewText) {
        mPreviewText = previewText;
    }

    public void setUrl(@NonNull String url) {
        mUrl = url;
    }

    public void setPublishedDate(@NonNull String publishedDate) {
        mPublishedDate = publishedDate;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        mImageUrl = imageUrl;
    }
}