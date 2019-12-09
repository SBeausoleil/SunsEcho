package com.sb.sunsecho;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.utils.ImageFetcher;

import java.text.DateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {
    private static final String TAG = ArticleFragment.class.getCanonicalName();

    private static final String ARTICLE = "article";

    private Article article;

    private TextView title;
    private TextView source;
    private TextView author;
    private TextView publishedAt;
    private TextView description;
    private ImageView image;
    private TextView content;

    public ArticleFragment() {
    }

    public static ArticleFragment newInstance(Article article) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARTICLE, article);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        article = getArguments().getParcelable(ARTICLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_article, container, false);

        title = v.findViewById(R.id.article_title);
        source = v.findViewById(R.id.article_source);
        author = v.findViewById(R.id.article_author);
        publishedAt = v.findViewById(R.id.article_published_at);
        description = v.findViewById(R.id.article_description);
        image = v.findViewById(R.id.article_image);
        content = v.findViewById(R.id.article_content);

        refresh();

        return v;
    }

    public void setArticle(Article article) {
        this.article = article;
        refresh();
    }

    private void refresh() {
        if (article != null) {
            Log.i(TAG, title != null ? "Not null" : "NULL");
            title.setText(article.getTitle());
            if (article.getSource().getUrl() != null)
                source.setText(getContext().getResources().getString(R.string.article_source, article.getSource().getName(), article.getSource().getUrl()));
            else
                source.setText(article.getSource().getName());
            author.setText(article.getAuthor());
            publishedAt.setText(article.getPublishedAt().toString());
            description.setText(article.getDescription());
            new ImageFetcher((img) -> image.setImageBitmap(img)).execute(article.getImage());
            content.setText(article.getContent());
        } else {
            title.setText("");
            source.setText("");
            author.setText("");
            publishedAt.setText("");
            description.setText("");
            image.setVisibility(View.INVISIBLE);
            content.setText("");
        }
    }

}
