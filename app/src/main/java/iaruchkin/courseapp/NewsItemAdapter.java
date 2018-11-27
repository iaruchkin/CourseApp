package iaruchkin.courseapp;

        import android.content.Context;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;

        import java.util.List;

        import iaruchkin.courseapp.data.NewsItem;


public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsViewHolder>{
    private final List<NewsItem> newsItemList;
    private Context context;

    public NewsItemAdapter(List<NewsItem> newsItemList, Context context) {
        this.newsItemList = newsItemList;
        this.context = context;
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
        return newsItemList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView categoryTextView;
        private final TextView titleTextView;
        private final TextView previewTextView;
        private final TextView dateTextView;

        public void bind(NewsItem newsItem) {
            Glide.with(context).load(newsItem.getImageUrl()).into(imageView);
            categoryTextView.setText(newsItem.getCategory().getName());
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

            context = itemView.getContext();
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NewsDetailsActivity.class);
                    intent.putExtra("position",getAdapterPosition());
                    context.startActivity(intent);
                }
            });

        }
    }
}