package com.example.newsexample.view.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.newsexample.R;
import com.example.newsexample.data.model.Article;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public static String TO_TIME_FORMAT = "dd MMM - HH:mm";
    public Context context;
    public ItemClickListener itemClickListener;
    private List<Article> articlesList;

    public NewsAdapter(List<Article> articlesList, Context context) {
        this.articlesList = articlesList;
        this.context = context;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design, parent, false);
        return new NewsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = articlesList.get(position).getTitle();
        String desc = articlesList.get(position).getDescription();
        String dateStr = articlesList.get(position).getPublishedAt();
        String imgUrl = articlesList.get(position).getUrlToImage();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            calendar.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            System.out.println("Something went wrong.");
        }

        holder.tvTitle.setText(title);
        holder.tvDesc.setText(desc);
        SimpleDateFormat outputDate = new SimpleDateFormat(TO_TIME_FORMAT, Locale.getDefault());
        holder.tvDate.setText(outputDate.format(calendar.getTime()));
        Picasso.get().load(imgUrl).fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }


    public interface ItemClickListener {
        void onClick(Article clickedNews);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView tvTitle, tvDesc, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.ivNews);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            this.tvDate = (TextView) itemView.findViewById(R.id.tvDate);

            itemView.setOnClickListener(v -> {
                itemClickListener.onClick(articlesList.get(getAdapterPosition()));
            });

        }
    }
}