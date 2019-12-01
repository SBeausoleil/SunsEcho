package com.sb.sunsecho.services;

import android.net.Uri;
import android.util.Log;

import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.beans.Category;
import com.sb.sunsecho.beans.Source;
import com.sb.sunsecho.beans.TopHeadlinesQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class NewsApiClient {
    public static final String TAG = NewsApiClient.class.getCanonicalName();

    public static final Uri SOURCES = makeSourceUri();

    private static Uri makeSourceUri() {
        // https://newsapi.org/v2/sources
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("newsapi.org").appendPath("v2").appendPath("sources");
        return builder.build();
    }

    private final String key;

    public NewsApiClient(String key) {
        this.key = key;
    }

    public Source[] sources() {
        try {
            URL url = new URL(SOURCES.toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", key);
            JSONObject json = new JSONObject(fetch(connection));
            return parse(json);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private Source[] parse(JSONObject json) throws JSONException {
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
            } finally {
                sources[i] = new Source(
                        current.getString("id"),
                        current.getString("name"),
                        current.getString("description"),
                        url,
                        Category.valueOf(current.getString("category")),
                        current.getString("language"),
                        current.getString("country"));
            }
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

    private Article[] topHeadlines(TopHeadlinesQuery query) {
        return null; // TODO
    }
}
