package com.example.readil_legal.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.readil_legal.R;
import com.example.readil_legal.adapter.FavoriteAdapter;
import com.example.readil_legal.model.Manga;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences("favorites_list", Context.MODE_PRIVATE);
        Gson gson = new Gson();

        // Retrieve favorites from SharedPreferences
        String jsonFavorites = sharedPreferences.getString("favorites_list", "");
        Type type = new TypeToken<List<Manga>>() {}.getType();
        List<Manga> favoriteMangaList = gson.fromJson(jsonFavorites, type);

        if (favoriteMangaList == null) {
            favoriteMangaList = new ArrayList<>();
        }

        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(favoriteMangaList,
                getContext());
        recyclerView.setAdapter(favoriteAdapter);
    }
}