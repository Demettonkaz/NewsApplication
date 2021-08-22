package com.example.newsexample.view.main.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsexample.Converter;
import com.example.newsexample.R;
import com.example.newsexample.data.model.Article;
import com.example.newsexample.view.detail.view.DetailActivity;
import com.example.newsexample.view.main.adapter.NewsAdapter;
import com.example.newsexample.view.main.viewmodel.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsAdapter.ItemClickListener {

    RecyclerView recyclerView;
    NewsAdapter adapter;
    EditText etFromDate, etToDate;
    Button btnShow;
    String date, fromDate, toDate;
    Calendar calendar;
    ProgressBar progressBar;
    private int mYear, mMonth, mDay;
    private SimpleDateFormat dateFormat;

    private MainViewModel mainViewModel;
    private List<Article> articleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new MainViewModel(this);

        initViews();
        initViewsAction();
    }

    public void initViews() {
        recyclerView = findViewById(R.id.rvNews);
        etFromDate = findViewById(R.id.etFromDate);
        etToDate = findViewById(R.id.etToDate);
        btnShow = findViewById(R.id.btnShow);
        progressBar = findViewById(R.id.progressBar);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        toDate = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -10);
        fromDate = dateFormat.format(new Date(calendar.getTimeInMillis()));

        mainViewModel.fetchNews(fromDate, toDate);

        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            articleList = mainViewModel.getNewsList();
            setAdapter(articleList);
        }, 1500);
    }

    public void initViewsAction() {
        etFromDate.setOnClickListener(v -> selectDate(etFromDate));
        etToDate.setOnClickListener(v -> selectDate(etToDate));

        btnShow.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            mainViewModel.clearList();
            mainViewModel.fetchNews(etFromDate.getText().toString(), etToDate.getText().toString());
            new Handler().postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                articleList = mainViewModel.getNewsList();
                setAdapter(articleList);
            }, 1500);

        });
    }

    @Override
    public void onClick(Article clickedNews) {
        String sendObjStr = Converter.objectToString(clickedNews);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("ArticleStr", sendObjStr);
        startActivity(intent);
    }

    public void selectDate(EditText editText) {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog picker = new DatePickerDialog(MainActivity.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    editText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    date = (dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                }, mYear, mMonth, mDay);
        picker.show();
    }

    public void setAdapter(List<Article> articlesList) {
        if (articlesList != null) {
            adapter = new NewsAdapter(articlesList, MainActivity.this);
            adapter.setItemClickListener(MainActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(adapter);
        }
    }
}
