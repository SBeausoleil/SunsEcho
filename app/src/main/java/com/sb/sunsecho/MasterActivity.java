package com.sb.sunsecho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;

import com.sb.sunsecho.beans.Article;

public class MasterActivity extends AppCompatActivity implements ArticlesListFragment.ArticlePicked {
    private static String TAG = MasterActivity.class.getCanonicalName();

    public static String ARTICLES = "articles";

    private static String SELECTED = "selected";

    private ArticlesListFragment listFragment;
    private ArticleFragment articleFragment;
    private Article selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        Article[] articles;
        if (getIntent().getParcelableArrayExtra(ARTICLES) != null) {
            Parcelable[] arr = getIntent().getParcelableArrayExtra(ARTICLES);
            articles = new Article[arr.length];
            for (int i = 0; i < arr.length; i++)
                articles[i] = (Article) arr[i];
        } else {
            articles = new Article[0];
        }

        Log.i(TAG, "Article fragment: " + articleFragment);

        if (savedInstanceState != null && savedInstanceState.getParcelable(SELECTED) != null) {
            selected = savedInstanceState.getParcelable(SELECTED);
            Log.i(TAG, "selected set! (" + selected + ")");
        }

        listFragment = ArticlesListFragment.newInstance(articles);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.articles_list, listFragment);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.articleFragment = ArticleFragment.newInstance(selected);
            transaction.replace(R.id.article_fragment, articleFragment);
            Log.i(TAG, "Initialized article fragment");
        }
        transaction.commit();
    }

    @Override
    public void receiveSelectedArticle(Article article) {
        this.selected = article;
        articleFragment = ArticleFragment.newInstance(article);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                        R.id.articles_list : R.id.article_fragment,
                articleFragment);
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SELECTED, selected);
        Log.i(TAG, "Selected saved!");
    }
}
