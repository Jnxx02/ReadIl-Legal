package com.example.readil_legal.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Chapter implements Parcelable {
    private String id;
    private Attributes attributes;

    public Chapter(String id, Attributes attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    protected Chapter(Parcel in) {
        id = in.readString();
        attributes = in.readParcelable(Attributes.class.getClassLoader());
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeParcelable(attributes, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public static class Attributes implements Parcelable {
        private String title;
        private String chapter;
        private String translatedLanguage;
        private List<String> data;

        public Attributes(String title, String chapter, String translatedLanguage, List<String> data) {
            this.title = title;
            this.chapter = chapter;
            this.translatedLanguage = translatedLanguage;
            this.data = data;
        }

        protected Attributes(Parcel in) {
            title = in.readString();
            chapter = in.readString();
            translatedLanguage = in.readString();
            data = in.createStringArrayList();
        }

        public static final Creator<Attributes> CREATOR = new Creator<Attributes>() {
            @Override
            public Attributes createFromParcel(Parcel in) {
                return new Attributes(in);
            }

            @Override
            public Attributes[] newArray(int size) {
                return new Attributes[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(title);
            dest.writeString(chapter);
            dest.writeString(translatedLanguage);
            dest.writeStringList(data);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getTitle() {
            return title;
        }

        public String getChapter() {
            return chapter;
        }

        public String getTranslatedLanguage() {
            return translatedLanguage;
        }

        public List<String> getData() {
            return data;
        }
    }
}
