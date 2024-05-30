package com.example.readil_legal.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.readil_legal.R;
import com.example.readil_legal.model.MangaResponse;
import com.example.readil_legal.network.ApiService;
import com.example.readil_legal.network.RetrofitClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ErrorFragment extends Fragment {
    private Button btnRetry;
    private ApiService service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnRetry = view.findViewById(R.id.btn_retry);

        Handler handler = new Handler(Looper.getMainLooper());
        ExecutorService executor = Executors.newSingleThreadExecutor();

        service = RetrofitClient.getClient().create(ApiService.class);

        btnRetry.setOnClickListener(v -> executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    Call<MangaResponse> call = service.getMangaList(10, 100);
                    call.enqueue(new Callback<MangaResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<MangaResponse> call, @NonNull Response<MangaResponse> response) {
                            if (response.isSuccessful()) {
                                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<MangaResponse> call, @NonNull Throwable t) {
                        }
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }));

    }
}