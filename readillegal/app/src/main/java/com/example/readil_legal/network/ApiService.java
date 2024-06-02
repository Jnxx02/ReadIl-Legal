package com.example.readil_legal.network;

import com.example.readil_legal.model.ChapterDetailResponse;
import com.example.readil_legal.model.CoverResponse;
import com.example.readil_legal.model.MangaDetailResponse;
import com.example.readil_legal.model.MangaResponse;
import com.example.readil_legal.model.ChapterImageResponse;

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

    @GET("manga/{id}/feed")
    Call<ChapterDetailResponse> getChapterDetails(@Path("id") String id);

    @GET("at-home/server/{chapter_id}")
    Call<ChapterImageResponse> getChapterImages(@Path("chapter_id") String chapterId);

    @GET("cover/{id}")
    Call<CoverResponse> getMangaCover(@Path("id") String id);
}
