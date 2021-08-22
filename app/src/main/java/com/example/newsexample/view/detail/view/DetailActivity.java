package com.example.newsexample.view.detail.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsexample.Converter;
import com.example.newsexample.R;
import com.example.newsexample.data.model.Article;
import com.example.newsexample.view.detail.viewmodel.DetailViewModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static String TO_TIME_FORMAT = "dd MMM - HH:mm";
    public ImageView imageView;
    public TextView tvTitle, tvDesc, tvDate, tvBackBtn;
    private DetailViewModel detailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        detailViewModel = new DetailViewModel(this);

        Bundle bundle = getIntent().getExtras();
        String articleStr = bundle.getString("ArticleStr");

        Article articleObj = Converter.stringToObject(articleStr, Article.class);

        initViews();
        setViews(articleObj);
        tvBackBtn.setOnClickListener(v -> onBackPressed());
    }

    public void initViews() {
        imageView = findViewById(R.id.ivNews);
        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        tvDate = findViewById(R.id.tvDate);
        tvBackBtn = findViewById(R.id.tvBackBtn);
    }

    public void setViews(Article article) {
        tvTitle.setText(article.getTitle());
        tvDesc.setText(article.getContent());

        Calendar calendar = detailViewModel.dateFormatter(article.getPublishedAt());
        SimpleDateFormat outputDate = new SimpleDateFormat(TO_TIME_FORMAT, Locale.getDefault());
        tvDate.setText(outputDate.format(calendar.getTime()));

        Picasso.get().load(article.getUrlToImage()).fit().centerCrop().into(imageView);
    }


}
