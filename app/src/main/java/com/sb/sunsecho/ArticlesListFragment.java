package com.sb.sunsecho;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sb.sunsecho.beans.Article;

public class ArticlesListFragment extends Fragment {
    private static final String ARTICLES = "articles";

    private Article[] articles;

    private RecyclerView list;

    public ArticlesListFragment() {
    }

    public static ArticlesListFragment newInstance(Article[] articles) {
        ArticlesListFragment fragment = new ArticlesListFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(ARTICLES, articles);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Parcelable[] arr = getArguments().getParcelableArray(ARTICLES);
            this.articles = new Article[arr.length];
            for (int i = 0; i < arr.length; i++) {
                this.articles[i] = (Article) arr[i];
            }
        } else {
            this.articles = new Article[0];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_articles_list, container, false);
        list = v.findViewById(R.id.articles_list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        refresh();
        return v;
    }

    private void refresh() {
        if (articles == null)
            articles = new Article[0];
        list.setAdapter(new ArticlesListAdapter(articles, (view -> {
            int position = list.getChildLayoutPosition(view);
            Article article = articles[position];
            ((ArticlePicked) getContext()).receiveSelectedArticle(article);
        })));
    }

    @FunctionalInterface
    public interface ArticlePicked {
        void receiveSelectedArticle(Article article);
    }
}
