package com.sb.sunsecho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;

import com.sb.sunsecho.beans.Article;

public class MasterActivity extends AppCompatActivity implements ArticlesListFragment.ArticlePicked {
    public static String ARTICLES = "articles";

    private Article[] articles;

    private ArticlesListFragment listFragment;
    private ArticleFragment articleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        if (getIntent().getParcelableArrayExtra(ARTICLES) != null) {
            Parcelable[] arr = getIntent().getParcelableArrayExtra(ARTICLES);
            articles = new Article[arr.length];
            for (int i = 0; i < arr.length; i++)
                articles[i] = (Article) arr[i];
        } else {
            articles = new Article[0];
        }

        listFragment = ArticlesListFragment.newInstance(articles);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.articles_list, listFragment);
        articleFragment = ArticleFragment.newInstance(null);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            transaction.replace(R.id.article_fragment, articleFragment);
        }
        transaction.commit();
    }

    @Override
    public void receiveSelectedArticle(Article article) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            articleFragment = ArticleFragment.newInstance(article);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.articles_list, articleFragment);
            transaction.commit();
        } else {
            articleFragment.setArticle(article);
        }
    }
}
