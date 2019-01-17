package iaruchkin.courseapp.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "news")
public class NewsEntity {

    public NewsEntity() {
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
    @ColumnInfo(name = "publishdate")
    private String mPublishDate;

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
    public String getPublishDate() {
        return mPublishDate;
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

    public void setPublishDate(@NonNull String publishDate) {
        mPublishDate = publishDate;
    }

    public void setImageUrl(@NonNull String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "NewsEntity{" +
                "mId='" + mId + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mSubsection='" + mSubsection + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mPreviewText='" + mPreviewText + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mPublishDate='" + mPublishDate + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                '}';
    }
}