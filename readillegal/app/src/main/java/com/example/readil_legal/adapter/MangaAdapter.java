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
import com.example.readil_legal.model.MangaResponse;
import com.example.readil_legal.network.ApiService;
import com.example.readil_legal.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {
    private List<Manga> mangaList;
    private final Context context;
    private final ApiService apiService;

    public MangaAdapter(List<Manga> mangaList, Context context) {
        this.mangaList = mangaList;
        this.context = context;
        this.apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_list, parent, false);
        return new MangaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        Manga manga = mangaList.get(position);
        holder.title.setText(manga.getAttributes().getTitle().getEn());
        holder.status.setText(manga.getType());
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
                        String coverUrl = "https://uploads.mangadex.org/covers/" + mangaId + "/" + response.body().getData().getAttributes().getFileName();
                        Picasso.get().load(coverUrl).into(holder.coverImage);
                        Log.d("MangaAdapterss", "Manga Title: " + manga.getAttributes().getTitle().getEn());
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
                Log.d("MangaAdapter", "Manga ID: " + mangaId);
                Bundle bundle = new Bundle();
                bundle.putString("manga_id", mangaId);

                MangaDetailFragment mangaDetailFragment = new MangaDetailFragment();
                mangaDetailFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
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

    public static class MangaViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, status, tags;
        private final ImageView coverImage;
        private final RelativeLayout relativeLayoutComicList;

        public MangaViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_comicTitle);
            status = itemView.findViewById(R.id.tv_comicType);
            tags = itemView.findViewById(R.id.tv_comicTags);
            coverImage = itemView.findViewById(R.id.iv_comicCover);
            relativeLayoutComicList = itemView.findViewById(R.id.rl_comic);
        }
    }
}
