package com.example.readil_legal.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.readil_legal.R;
import com.example.readil_legal.adapter.MangaAdapter;
import com.example.readil_legal.model.Manga;
import com.example.readil_legal.model.MangaResponse;
import com.example.readil_legal.network.ApiService;
import com.example.readil_legal.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private MangaAdapter mangaAdapter;
    private List<Manga> mangaList = new ArrayList<>();
    private ApiService apiService;
    private static final int ITEMS_PER_PAGE = 10;
    private int currentPage = 1;
    private TextView tvCurrentPage;
    private ExecutorService executorService;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnNextPage = view.findViewById(R.id.btn_next_page);
        Button btnPreviousPage = view.findViewById(R.id.btn_previous_page);
        tvCurrentPage = view.findViewById(R.id.tv_current_page);

        RecyclerView recyclerView = view.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mangaAdapter = new MangaAdapter(mangaList, getContext());
        recyclerView.setAdapter(mangaAdapter);

        apiService = RetrofitClient.getClient().create(ApiService.class);
        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        updatePageText();

        btnNextPage.setOnClickListener(v -> {
            currentPage++;
            updatePageText();
            executorService.execute(this::fetchMangaData);
        });

        btnPreviousPage.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                updatePageText();
                executorService.execute(this::fetchMangaData);
            }
        });

        executorService.execute(this::fetchMangaData);
    }

    private void fetchMangaData() {
        ErrorFragment errorFragment = new ErrorFragment();

        int offset = (currentPage - 1) * ITEMS_PER_PAGE;

        Call<MangaResponse> call = apiService.getMangaList(ITEMS_PER_PAGE, offset);
        call.enqueue(new Callback<MangaResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MangaResponse> call, @NonNull Response<MangaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mangaList.clear();
                    mangaList.addAll(response.body().getData());
                    handler.post(() -> {
                        mangaAdapter.notifyDataSetChanged();
                    });
                } else {
                    Log.e("API_ERROR", "Response is not successful");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MangaResponse> call, @NonNull Throwable t) {
                Log.e("API_FAILURE", "Failed to fetch manga list", t);

                handler.post(() -> getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, errorFragment)
                        .commit());
            }
        });
    }

    private void updatePageText() {
        handler.post(() -> tvCurrentPage.setText("Page " + currentPage));
    }
}