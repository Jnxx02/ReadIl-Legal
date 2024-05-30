package com.example.readil_legal.model;

import java.util.ArrayList;
import java.util.List;

public class Manga {
    private String id;
    private String type;
    private Attributes attributes;
    private List<Relationship> relationships = new ArrayList<>();

    // Getter and setter methods for 'id'
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and setter methods for 'type'
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getter and setter methods for 'attributes'
    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    // Getter and setter methods for 'relationships'
    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        if (relationships != null) {
            this.relationships = relationships;
        }
    }

    // Method to get cover ID from relationships
    public String getCoverId() {
        if (relationships != null) {
            for (Relationship relationship : relationships) {
                if ("cover_art".equals(relationship.getType())) {
                    return relationship.getId();
                }
            }
        }
        return null;
    }

    // Attributes class for Manga
    public static class Attributes {
        private Title title;
        private Description description;
        private int year;
        private List<Tag> tags = new ArrayList<>();
        private String status;
        private String lastChapter;

        // Getter and setter methods for 'title'
        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        // Getter and setter methods for 'description'
        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        // Getter and setter methods for 'year'
        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        // Getter and setter methods for 'tags'
        public List<Tag> getTags() {
            return tags;
        }

        public void setTags(List<Tag> tags) {
            if (tags != null) {
                this.tags = tags;
            }
        }

        // Getter and setter methods for 'status'
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        // Getter and setter methods for 'lastChapter'
        public String getLastChapter() {
            return lastChapter;
        }

        public void setLastChapter(String lastChapter) {
            this.lastChapter = lastChapter;
        }
    }

    // Title class for Manga
    public static class Title {
        private String en;

        // Getter and setter methods for 'en'
        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }
    }

    // Description class for Manga
    public static class Description {
        private String en;

        // Getter and setter methods for 'en'
        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }
    }

    // Tag class for Manga
    public static class Tag {
        private String id;
        private TagAttributes attributes;

        // Getter and setter methods for 'id'
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        // Getter and setter methods for 'attributes'
        public TagAttributes getAttributes() {
            return attributes;
        }

        public void setAttributes(TagAttributes attributes) {
            this.attributes = attributes;
        }

        // TagAttributes class for Tag
        public static class TagAttributes {
            private Name name;

            // Getter and setter methods for 'name'
            public Name getName() {
                return name;
            }

            public void setName(Name name) {
                this.name = name;
            }

            // Name class for TagAttributes
            public static class Name {
                private String en;

                // Getter and setter methods for 'en'
                public String getEn() {
                    return en;
                }

                public void setEn(String en) {
                    this.en = en;
                }
            }
        }
    }

    // Relationship class for Manga
    public static class Relationship {
        private String id;
        private String type;

        // Getter and setter methods for 'id'
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        // Getter and setter methods for 'type'
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
