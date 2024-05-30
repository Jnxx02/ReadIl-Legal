package com.example.final_mobile.network;

import com.example.final_mobile.model.Chapter;
import com.example.final_mobile.model.Komik;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    // Mendapatkan daftar manga
    @GET("manga")
    Call<List<Komik>> getMangaList(@Query("limit") int limit, @Query("offset") int offset);

    // Mendapatkan detail manga berdasarkan ID
    @GET("manga/{id}")
    Call<Komik> getMangaDetail(@Path("id") String mangaId);

    // Mendapatkan daftar chapter berdasarkan manga ID
    @GET("chapter")
    Call<List<Chapter>> getChapterList(@Query("manga") String mangaId, @Query("limit") int limit, @Query("offset") int offset);

}
