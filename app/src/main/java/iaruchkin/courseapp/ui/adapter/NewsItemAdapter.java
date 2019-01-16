package iaruchkin.courseapp.ui.adapter;

        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;

        import java.util.ArrayList;
        import java.util.List;

        import iaruchkin.courseapp.R;
        import iaruchkin.courseapp.data.NewsItem;
        import iaruchkin.courseapp.room.NewsEntity;


public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsViewHolder>{
    private final List<NewsItem> newsItemList = new ArrayList<>();
    private final NewsAdapterOnClickHandler mClickHandler;

    public interface NewsAdapterOnClickHandler {
        void onClick(NewsItem newsItem);
    }

    public NewsItemAdapter(NewsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newsListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(newsListView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        NewsItem newsItem = newsItemList.get(position);
        holder.bind(newsItem);
    }

    @Override
    public int getItemCount() {
        if (null == newsItemList) return 0;
        return newsItemList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imageView;
        private final TextView categoryTextView;
        private final TextView titleTextView;
        private final TextView previewTextView;
        private final TextView dateTextView;

        public void bind(NewsItem newsItem) {
            Glide.with(itemView.getContext()).load(newsItem.getImageUrl()).into(imageView);
            categoryTextView.setText(newsItem.getCategory());
            titleTextView.setText(newsItem.getTitle());
            previewTextView.setText(newsItem.getPreviewText());
            dateTextView.setText(newsItem.getPublishDate().toString());
        }

        public NewsViewHolder(View view) {
            super(view);

            imageView=view.findViewById(R.id.img_preview);
            categoryTextView=view.findViewById(R.id.item_category);
            titleTextView = view.findViewById(R.id.item_title);
            previewTextView = view.findViewById(R.id.item_preview);
            dateTextView = view.findViewById(R.id.item_date);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            NewsItem newsItem = newsItemList.get(adapterPosition);
            mClickHandler.onClick(newsItem);
        }
    }

        public void replaceItems(List<NewsItem> newsData) {
            newsItemList.clear();
            newsItemList.addAll(newsData);
            notifyDataSetChanged();
        }
}