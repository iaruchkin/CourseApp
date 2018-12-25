package iaruchkin.courseapp.network;

import com.google.gson.annotations.SerializedName;

public final class MultimediaDTO {

    @SerializedName("type")
    private String type;

    @SerializedName("url")
    private String url;


    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
