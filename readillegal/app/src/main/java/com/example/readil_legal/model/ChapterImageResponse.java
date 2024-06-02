package com.example.readil_legal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChapterImageResponse {
    @SerializedName("baseUrl")
    private String baseUrl;

    @SerializedName("chapter")
    private ChapterData chapter;

    public String getBaseUrl() {
        return baseUrl;
    }

    public ChapterData getChapter() {
        return chapter;
    }

    public static class ChapterData {
        @SerializedName("data")
        private List<String> data;

        @SerializedName("hash")
        private String hash;

        public List<String> getData() {
            return data;
        }

        public String getHash() {
            return hash;
        }
    }
}