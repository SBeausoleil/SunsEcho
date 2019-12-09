package com.sb.sunsecho.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;

public class Article implements Parcelable {

    private Source source;
    private String author;
    private String title;
    private String description;
    private URL url;
    private URL image;
    private ZonedDateTime publishedAt;
    private String content;

    public Article(Source source, String author, String title, String description, URL url, URL image, ZonedDateTime publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.image = image;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    protected Article(Parcel in) {
        source = in.readParcelable(Source.class.getClassLoader());
        author = in.readString();
        title = in.readString();
        description = in.readString();
        try {
            String urlString = in.readString();
            if (urlString != null)
                url = new URL(urlString);
            String imgString = in.readString();
            if (imgString != null)
                image = new URL(imgString);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        publishedAt = ZonedDateTime.parse(in.readString());
        content = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(source, flags);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url != null ? url.toString() : null);
        dest.writeString(image != null ? image.toString() : null);
        dest.writeString(publishedAt.toString());
        dest.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }

    public ZonedDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(ZonedDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "source=" + source +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url=" + url +
                ", image=" + image +
                ", publishedAt=" + publishedAt +
                ", content='" + content + '\'' +
                '}';
    }
}
