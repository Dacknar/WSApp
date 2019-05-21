package com.software.dunk.wsapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("all")
public class News {
    @SerializedName("status")
    private final String status;

    @SerializedName("count")
    private final int count;

    @SerializedName("count_total")
    private final int countTotal;

    @SerializedName("pages")
    private final int pages;

    @SerializedName("posts")
    private final List<Posts> posts;

    public News(String status, int count, int countTotal, int pages, List<Posts> posts) {
        this.status = status;
        this.count = count;
        this.countTotal = countTotal;
        this.pages = pages;
        this.posts = posts;
    }

    public String getStatus() {
        return status;
    }

    public int getCount() {
        return count;
    }

    public int getCountTotal() {
        return countTotal;
    }

    public int getPages() {
        return pages;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public static class Posts {
        @SerializedName("id")
        private final int id;

        @SerializedName("url")
        private final String url;

        @SerializedName("title")
        private final String title;

        @SerializedName("excerpt")
        private final String excerpt;

        @SerializedName("date")
        private final String date;

        @SerializedName("thumbnail")
        private final String thumbnail;

        @SerializedName("thumbnail_size")
        private final String thumbnailSize;

        @SerializedName("thumbnail_images")
        private final List<Object> thumbnailImages;

        public Posts(int id, String url, String title, String excerpt, String date,
                     String thumbnail, String thumbnailSize, List<Object> thumbnailImages) {
            this.id = id;
            this.url = url;
            this.title = title;
            this.excerpt = excerpt;
            this.date = date;
            this.thumbnail = thumbnail;
            this.thumbnailSize = thumbnailSize;
            this.thumbnailImages = thumbnailImages;
        }

        public int getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        public String getTitle() {
            return title;
        }

        public String getExcerpt() {
            return excerpt;
        }

        public String getDate() {
            return date;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getThumbnailSize() {
            return thumbnailSize;
        }

        public List<Object> getThumbnailImages() {
            return thumbnailImages;
        }
    }
}
