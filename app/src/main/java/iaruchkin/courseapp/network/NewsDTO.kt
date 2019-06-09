package iaruchkin.courseapp.network

import com.google.gson.annotations.SerializedName

import java.util.Date

data class NewsDTO (

    @SerializedName("subsection")
    val subsection: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("abstract")
    val abstractDescription: String,

    @SerializedName("published_date")
    val publishDate: Date,

    @SerializedName("multimedia")
    val multimedia: List<MultimediaDTO>,

    @SerializedName("url")
    val url: String
)
