package com.example.readil_legal.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.readil_legal.R;
import com.example.readil_legal.model.CoverResponse;
import com.example.readil_legal.model.Manga;
import com.example.readil_legal.model.MangaDetailResponse;
import com.example.readil_legal.network.ApiService;
import com.example.readil_legal.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MangaDetailFragment extends Fragment {
    private ImageView coverImage;
    private TextView title, description, year, tags, status, lastUploaded;
    private ImageButton btnFavorite;
    private View loadingView;
    private ApiService apiService;
    private Handler handler;
    private String mangaId;
    private Manga manga;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manga_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        coverImage = view.findViewById(R.id.iv_detailCover);
        title = view.findViewById(R.id.tv_detailTitle);
        description = view.findViewById(R.id.tv_detailDescription);
        year = view.findViewById(R.id.tv_detailYear);
        tags = view.findViewById(R.id.tv_detailTags);
        status = view.findViewById(R.id.tv_detailStatus);
        lastUploaded = view.findViewById(R.id.tv_detailLastUploaded);
        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnFavorite = view.findViewById(R.id.btn_favorite);

        sharedPreferences = getActivity()
                .getSharedPreferences("favorites", Context.MODE_PRIVATE);
        gson = new Gson();
        ErrorFragment errorNoConnetionFragment = new ErrorFragment();

        apiService = RetrofitClient.getClient().create(ApiService.class);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        btnBack.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        btnFavorite.setOnClickListener(v -> {
            toggleFavoriteStatus();
        });

        if (getArguments() != null) {
            mangaId = getArguments().getString("manga_id");

            if (mangaId != null && !mangaId.isEmpty()) {
                executorService.execute(() -> {
                    Call<MangaDetailResponse> call = apiService.getMangaDetails(mangaId);
                    call.enqueue(new Callback<MangaDetailResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<MangaDetailResponse> call, @NonNull Response<MangaDetailResponse> response) {
                            if (response.isSuccessful()) {
                                MangaDetailResponse mangaDetailResponse = response.body();

                                if (mangaDetailResponse != null) {
                                    manga = mangaDetailResponse.getData();

                                    handler.post(() -> {
                                        if (manga != null) {
                                            loadingView.setVisibility(View.GONE);
                                            fetchCoverImage(mangaId, manga.getCoverId());

                                            title.setText(manga.getAttributes().getTitle().getEn());
                                            description.setText(manga.getAttributes().getDescription().getEn());
                                            year.setText(String.valueOf(manga.getAttributes().getYear()));
                                            tags.setText(manga.getAttributes().getTags().stream()
                                                    .map(tag -> tag.getAttributes().getName().getEn())
                                                    .collect(Collectors.joining(", ")));
                                            status.setText(manga.getAttributes().getStatus());
                                            lastUploaded.setText(manga.getAttributes().getLastChapter());

                                            updateFavoriteButton();
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MangaDetailResponse> call, @NonNull Throwable t) {
                            Log.e("MangaDetailFragment", "on failure: " + t.getMessage());

                            handler.post(() -> getParentFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, errorNoConnetionFragment)
                                    .commit());
                        }
                    });
                });
            } else {
                Log.e("MangaDetailFragment", "Manga ID is null or empty");
            }
        }
    }

    private void fetchCoverImage(String mangaId, String coverId) {
        Call<CoverResponse> call = apiService.getMangaCover(coverId);
        call.enqueue(new Callback<CoverResponse>() {
            @Override
            public void onResponse(@NonNull Call<CoverResponse> call, @NonNull Response<CoverResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String coverUrl =
                            "https://uploads.mangadex.org/covers/" + mangaId + "/" + response.body()
                                    .getData().getAttributes().getFileName();
                    Picasso.get().load(coverUrl).into(coverImage);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch cover image", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CoverResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Failed to fetch cover image: " +
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleFavoriteStatus() {
        String jsonFavorites = sharedPreferences.getString("favorites_list", "");
        Type type = new TypeToken<List<Manga>>() {}.getType();
        List<Manga> favoritesList = gson.fromJson(jsonFavorites, type);

        if (favoritesList == null) {
            favoritesList = new ArrayList<>();
        }

        boolean isFavorite = false;
        for (Manga favoriteManga : favoritesList) {
            if (favoriteManga.getId().equals(manga.getId())) {
                isFavorite = true;
                break;
            }
        }

        if (isFavorite) {
            favoritesList.removeIf(favoriteManga -> favoriteManga.getId().equals(manga.getId()));
            Toast.makeText(getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
        } else {
            favoritesList.add(manga);
            Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        jsonFavorites = gson.toJson(favoritesList);
        editor.putString("favorites_list", jsonFavorites);
        editor.apply();

        updateFavoriteButton();
    }

    private void updateFavoriteButton() {
        String jsonFavorites = sharedPreferences.getString("favorites_list", "");
        Type type = new TypeToken<List<Manga>>() {}.getType();
        List<Manga> favoritesList = gson.fromJson(jsonFavorites, type);

        if (favoritesList == null) {
            favoritesList = new ArrayList<>();
        }

        boolean isFavorite = false;
        for (Manga favoriteManga : favoritesList) {
            if (favoriteManga.getId().equals(manga.getId())) {
                isFavorite = true;
                break;
            }
        }

        if (isFavorite) {
            btnFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            btnFavorite.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
}
