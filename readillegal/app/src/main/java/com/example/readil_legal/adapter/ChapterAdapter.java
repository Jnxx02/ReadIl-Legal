package com.example.readil_legal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readil_legal.R;
import com.example.readil_legal.model.Chapter;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {
    private final List<Chapter> chapterList;
    private Context context;
    private final OnChapterClickListener onChapterClickListener;

    public ChapterAdapter(Context context, List<Chapter> chapterList, OnChapterClickListener onChapterClickListener) {
        this.context = context;
        this.chapterList = chapterList;
        this.onChapterClickListener = onChapterClickListener;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chapter_button, parent,
                false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        holder.buttonChapter.setText("Chapter " + chapter.getAttributes().getChapter());
        holder.buttonChapter.setOnClickListener(v -> onChapterClickListener.onChapterClick(chapter, position, chapterList));
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {
        Button buttonChapter;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonChapter = itemView.findViewById(R.id.buttonChapter);
        }
    }

    public interface OnChapterClickListener {
        void onChapterClick(Chapter chapter, int position, List<Chapter> chapterList);
    }
}
