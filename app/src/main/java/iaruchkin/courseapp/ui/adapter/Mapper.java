package iaruchkin.courseapp.ui.adapter;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import iaruchkin.courseapp.data.NewsItem;
import iaruchkin.courseapp.network.MultimediaDTO;
import iaruchkin.courseapp.network.NewsDTO;

public class Mapper {

    public static List<NewsItem> map(List<NewsDTO> newsDTO) {
        List<NewsItem> items = new ArrayList<>();

        for (NewsDTO dto : newsDTO) {
            final NewsItem newsItem = mapItem(dto);
            items.add(newsItem);
        }

        return Collections.unmodifiableList(items);
    }

    static NewsItem mapItem(NewsDTO dto){

        final List<MultimediaDTO> multimedia = dto.getMultimedia();

        String title = dto.getTitle();
        final String imageUrl = mapImage(multimedia);
        String category = dto.getSubsection();
        Date publishDate = dto.getPublishDate();
        String preview = dto.getAbstractDescription();
        String url = dto.getUrl();

        //конвертим dto to item
        return NewsItem.create(title,
                imageUrl,
                category,
                publishDate,
                preview,
                url);
    }

    @Nullable
    private static String mapImage(@Nullable List<MultimediaDTO> multimedias) {

        if (multimedias == null || multimedias.isEmpty()) {
            return null;
        }

        final int imageImMaximumQutilityIndex = multimedias.size() - 1;
        final MultimediaDTO multimedia = multimedias.get(imageImMaximumQutilityIndex);

        if (!multimedia.getType().equals("image")) {
            return null;
        }

        return multimedia.getUrl();
    }

}
