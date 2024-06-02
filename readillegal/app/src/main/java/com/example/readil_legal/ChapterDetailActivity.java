package com.example.readil_legal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readil_legal.model.Chapter;
import com.example.readil_legal.model.ChapterImageResponse;
import com.example.readil_legal.network.ApiService;
import com.example.readil_legal.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterDetailActivity extends AppCompatActivity {
    private LinearLayout imageContainer;
    private ApiService apiService;
    private List<Chapter> chapters;
    private int currentChapterIndex;
    private TextView tvCurrentChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_detail);

        imageContainer = findViewById(R.id.imageContainer);
        tvCurrentChapter = findViewById(R.id.tvCurrentChapter);
        apiService = RetrofitClient.getClient().create(ApiService.class);

        currentChapterIndex = getIntent().getIntExtra("chapterIndex", -1);
        chapters = getIntent().getParcelableArrayListExtra("chapters");

        if (currentChapterIndex != -1 && chapters != null && !chapters.isEmpty()) {
            loadChapter(currentChapterIndex);
        }

        Button btnBackToDetail = findViewById(R.id.btnBackToDetail);
        btnBackToDetail.setOnClickListener(view -> finish());

        Button btnNextChapter = findViewById(R.id.btnNextChapter);
        btnNextChapter.setOnClickListener(view -> {
            if (currentChapterIndex < chapters.size() - 1) {
                currentChapterIndex++;
                loadChapter(currentChapterIndex);
            } else {
                Toast.makeText(ChapterDetailActivity.this, "No next chapter available", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnPreviousChapter = findViewById(R.id.btnPreviousChapter);
        btnPreviousChapter.setOnClickListener(view -> {
            if (currentChapterIndex > 0) {
                currentChapterIndex--;
                loadChapter(currentChapterIndex);
            } else {
                Toast.makeText(ChapterDetailActivity.this, "No previous chapter available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadChapter(int chapterIndex) {
        Chapter currentChapter = chapters.get(chapterIndex);
        tvCurrentChapter.setText("Chapter " + currentChapter.getAttributes().getChapter());
        fetchChapterImages(currentChapter.getId());
    }

    private void fetchChapterImages(String chapterId) {
        imageContainer.removeAllViews();  // Clear existing images
        Call<ChapterImageResponse> call = apiService.getChapterImages(chapterId);
        call.enqueue(new Callback<ChapterImageResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChapterImageResponse> call,
                                   @NonNull Response<ChapterImageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String baseUrl = response.body().getBaseUrl();
                    ChapterImageResponse.ChapterData chapterData = response.body().getChapter();
                    List<String> images = chapterData.getData();
                    String hash = chapterData.getHash();
                    displayImages(baseUrl, hash, images);
                } else {
                    Toast.makeText(ChapterDetailActivity.this,
                            "Failed to fetch images", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChapterImageResponse> call, @NonNull Throwable t) {
                Toast.makeText(ChapterDetailActivity.this, "Error: "
                        + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayImages(String baseUrl, String hash, List<String> images) {
        for (String image : images) {
            String imageUrl = baseUrl + "/data/" + hash + "/" + image;
            ImageView imageView = new ImageView(this);
            Picasso.get().load(imageUrl).into(imageView);
            imageContainer.addView(imageView);
        }
    }
}