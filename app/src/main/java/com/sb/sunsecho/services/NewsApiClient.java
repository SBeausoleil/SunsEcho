package com.sb.sunsecho.services;

import android.net.Uri;
import android.util.Log;

import com.sb.sunsecho.TooBroadQueryException;
import com.sb.sunsecho.beans.ApiQuery;
import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.beans.Category;
import com.sb.sunsecho.beans.GeneralQuery;
import com.sb.sunsecho.beans.QueryArguments;
import com.sb.sunsecho.beans.Source;
import com.sb.sunsecho.beans.SourceQuery;
import com.sb.sunsecho.beans.TopHeadlinesQuery;
import com.sb.sunsecho.utils.Sources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.ZonedDateTime;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.NonNull;


public class NewsApiClient {
    public static final String TAG = NewsApiClient.class.getCanonicalName();

    public static final Uri SOURCES = makeSourceUri();
    public static final Uri TOP_HEADLINES = makeTopHeadlinesUri();
    public static final Uri EVERYTHING = makeEverythingUri();

    private static NewsApiClient instance;

    public static NewsApiClient getInstance() {
        return instance;
    }

    private static Uri makeSourceUri() {
        // https://newsapi.org/v2/sources
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("newsapi.org").appendPath("v2").appendPath("sources");
        return builder.build();
    }

    private static Uri makeTopHeadlinesUri() {
        // https://newsapi.org/v2/top-headlines
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("newsapi.org").appendPath("v2").appendPath("top-headlines");
        return builder.build();
    }

    private static Uri makeEverythingUri() {
        // https://newsapi.org/v2/everything
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("newsapi.org").appendPath("v2").appendPath("everything");
        return builder.build();
    }

    private final String key;

    public NewsApiClient(String key) {
        this.key = key;
        instance = this;
    }

    public Source[] sources() {
        return sources(new SourceQuery());
    }

    public Source[] sources(@NonNull SourceQuery query) {
        try {
            URL url = new URL(query.toUrlArguments(SOURCES.buildUpon()).build().toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", key);
            JSONObject json = new JSONObject(fetch(connection));
            return parseSources(json);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public Article[] topHeadlines(TopHeadlinesQuery query, Sources sources) throws TooBroadQueryException {
        return articlesApiCall(TOP_HEADLINES, query, sources);
    }

    public Article[] general(GeneralQuery query, Sources sources) throws TooBroadQueryException {
        return articlesApiCall(EVERYTHING, query, sources);
    }

    private Article[] articlesApiCall(Uri uri, ApiQuery query, Sources sources) throws TooBroadQueryException {
        if (query.isAllArgumentsNull())
            throw new TooBroadQueryException("At least one of the search query parameters that is not the language, page size or the page must be set!");

        try {
            URL url = new URL(query.toUrlArguments(uri.buildUpon()).build().toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", key);
            JSONObject json = new JSONObject(fetch(connection));
            return parseArticles(json, sources);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private Article[] parseArticles(JSONObject json, Sources sources) throws JSONException {
        JSONArray array = json.getJSONArray("articles");
        Article[] articles = new Article[array.length()];
        for (int i = 0; i < articles.length; i++) {
            JSONObject current = array.getJSONObject(i);
            Source source = findSource(current.getJSONObject("source"), sources);
            Uri url = null;
            url = Uri.parse(current.getString("url"));
            URL image = null;
            try {
                image = new URL(current.getString("urlToImage"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            articles[i] = new Article(source,
                    current.getString("author"),
                    current.getString("title"),
                    current.getString("description"),
                    url,
                    image,
                    ZonedDateTime.parse(current.getString("publishedAt")),
                    current.getString("content"));
        }
        return articles;
    }

    private Source findSource(JSONObject sourceJson, Sources sources) throws JSONException {
        Source source = sources.getById(sourceJson.getString("id"));
        if (source != null)
            return source;
        return new Source(sourceJson.getString("id"), sourceJson.getString("name"));
    }

    private Source[] parseSources(JSONObject json) throws JSONException {
        JSONArray array = json.getJSONArray("sources");
        Source[] sources = new Source[array.length()];
        JSONObject current;
        for (int i = 0; i < sources.length; i++) {
            current = array.getJSONObject(i);
            URL url = null;
            try {
                url = new URL(current.getString("url"));
            } catch (MalformedURLException e) {
                Log.w(TAG, "Malformed URL: " + current.getString("url"), e);
            }
            sources[i] = new Source(
                    current.getString("id"),
                    current.getString("name"),
                    current.getString("description"),
                    url,
                    Category.valueOf(current.getString("category")),
                    current.getString("language"),
                    current.getString("country"));
        }
        return sources;
    }

    private String fetch(HttpsURLConnection connection) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = connection.getInputStream();
        int bytesRead = 0;
        byte[] buffer = new byte[1024];
        while ((bytesRead = in.read(buffer)) > 0)
            out.write(buffer, 0, bytesRead);

        out.close();
        return new String(out.toByteArray());
    }

    @Override
    public String toString() {
        return "NewsApiClient{" +
                "key='" + key + '\'' +
                '}';
    }
}
