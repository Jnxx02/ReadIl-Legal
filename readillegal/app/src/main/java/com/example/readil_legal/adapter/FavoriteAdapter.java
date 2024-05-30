package com.example.readil_legal.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readil_legal.R;
import com.example.readil_legal.fragment.MangaDetailFragment;
import com.example.readil_legal.model.CoverResponse;
import com.example.readil_legal.model.Manga;
import com.example.readil_legal.network.ApiService;
import com.example.readil_legal.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<Manga> mangaList;
    private Context context;
    private final ApiService apiService;

    public FavoriteAdapter(List<Manga> mangaList, Context context) {
        this.context = context;
        this.mangaList = mangaList;
        this.apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comic_carousel, parent,
                false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Manga manga = mangaList.get(position);
        holder.title.setText(manga.getAttributes().getTitle().getEn());
        holder.tags.setText(manga.getAttributes().getTags().stream()
                .map(tag -> tag.getAttributes().getName().getEn())
                .collect(Collectors.joining(", ")));

        String coverId = manga.getCoverId();
        String mangaId = manga.getId();

        if (coverId != null) {
            apiService.getMangaCover(coverId).enqueue(new Callback<CoverResponse>() {
                @Override
                public void onResponse(@NonNull Call<CoverResponse> call, @NonNull Response<CoverResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String coverUrl =
                                "https://uploads.mangadex.org/covers/" + mangaId + "/" + response.body()
                                        .getData().getAttributes().getFileName();
                        Picasso.get().load(coverUrl).into(holder.coverImage);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CoverResponse> call, @NonNull Throwable t) {
                    Log.e("API_FAILURE", "Failed to fetch manga list", t);
                }
            });
        }

        holder.relativeLayoutComicList.setOnClickListener(v -> {
            if (mangaId != null && !mangaId.isEmpty()) {
                Bundle bundle = new Bundle();
                bundle.putString("manga_id", mangaId);

                MangaDetailFragment mangaDetailFragment = new MangaDetailFragment();
                mangaDetailFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((AppCompatActivity) context)
                        .getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, mangaDetailFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Log.e("MangaAdapter", "Manga ID is null or empty");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, tags;
        private final ImageView coverImage;
        private final RelativeLayout relativeLayoutComicList;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_favTitle);
            tags = itemView.findViewById(R.id.tv_favTags);
            coverImage = itemView.findViewById(R.id.iv_favCover);
            relativeLayoutComicList = itemView.findViewById(R.id.rl_favComic);
        }
    }
}