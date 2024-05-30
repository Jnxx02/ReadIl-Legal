package com.example.readil_legal.network;

import com.example.readil_legal.model.CoverResponse;
import com.example.readil_legal.model.Manga;
import com.example.readil_legal.model.MangaDetailResponse;
import com.example.readil_legal.model.MangaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("manga")
    Call<MangaResponse> getMangaList(
            @Query("limit") int limit,
            @Query("offset") int offset
    );;

    @GET("manga/{id}")
    Call<MangaDetailResponse> getMangaDetails(@Path("id") String id);

    @GET("cover/{id}")
    Call<CoverResponse> getMangaCover(@Path("id") String id);
}
