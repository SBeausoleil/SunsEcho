package com.sb.sunsecho;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.sb.sunsecho.beans.ApiQuery;
import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.utils.Sources;

public class MasterActivity extends AppCompatActivity implements ArticlesListFragment.ArticlePicked, ArticlesReceiver {
    private static String TAG = MasterActivity.class.getCanonicalName();

    public static final String SOURCES = "sources";
    public static final String ARTICLES = "articles";
    public static final String QUERY = "query";
    private static final String SELECTED = "selected";

    private Sources sources;
    //private ApiQuery query;
    private ArticlesListFragment listFragment;
    private ArticleFragment articleFragment;
    private Article selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        Article[] articles = new Article[0];
        if (getIntent() != null) {
            if (getIntent().getParcelableArrayExtra(ARTICLES) != null) {
                Parcelable[] arr = getIntent().getParcelableArrayExtra(ARTICLES);
                articles = new Article[arr.length];
                for (int i = 0; i < arr.length; i++)
                    articles[i] = (Article) arr[i];
            }
            if (getIntent().getParcelableArrayExtra(SOURCES) != null) {
                sources = Sources.makeSources(getIntent().getParcelableArrayExtra(SOURCES));
            }
            /*if (getIntent().getParcelableExtra(QUERY) != null) {
                query = getIntent().getParcelableExtra(QUERY);
            }*/
        }

        if (savedInstanceState != null && savedInstanceState.getParcelable(SELECTED) != null) {
            selected = savedInstanceState.getParcelable(SELECTED);
        }

        listFragment = ArticlesListFragment.newInstance(articles);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.articles_list, listFragment);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.articleFragment = ArticleFragment.newInstance(selected);
            transaction.replace(R.id.article_fragment, articleFragment);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu_icon:
                Intent searchActivity = new Intent(this, SearchActivity.class);
                //startSearchActivity.putExtra(SearchActivity.QUERY, query);
                searchActivity.putExtra(SearchActivity.SOURCES, sources.toArray());
                startActivityForResult(searchActivity, SearchActivity.REQUEST_CODE);
        }
        return true;
    }

    @Override
    public void receive(Article[] articles) {
        listFragment = ArticlesListFragment.newInstance(articles);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.articles_list, listFragment);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.articleFragment = ArticleFragment.newInstance(null);
            transaction.replace(R.id.article_fragment, articleFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case SearchActivity.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Parcelable[] arr = data.getParcelableArrayExtra(SearchActivity.RESULT);
                    Article[] articles = new Article[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        articles[i] = (Article) arr[i];
                    }
                    receive(articles);
                } else throw new RuntimeException("Search activity returned with result \"not ok\".");
                break;
        }
    }
}
