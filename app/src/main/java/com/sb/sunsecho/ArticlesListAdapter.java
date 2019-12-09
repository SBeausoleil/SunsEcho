package com.sb.sunsecho;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sb.sunsecho.beans.Article;

import androidx.recyclerview.widget.RecyclerView;

public class ArticlesListAdapter extends RecyclerView.Adapter<ArticlesListAdapter.Item> {
    private Article[] articles;
    private View.OnClickListener listener;

    public ArticlesListAdapter(Article[] articles, View.OnClickListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @Override
    public ArticlesListAdapter.Item onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_item, parent, false);
        v.setOnClickListener(listener);
        return new Item(v);
    }

    @Override
    public void onBindViewHolder(Item holder, int position) {
        Article article = articles[position];
        holder.title.setText(article.getTitle());
        holder.source.setText(article.getSource().getName());
        holder.publishedAt.setText(article.getPublishedAt().toString());
    }

    @Override
    public int getItemCount() {
        return articles.length;
    }

    public static class Item extends RecyclerView.ViewHolder {
        TextView title;
        TextView source;
        TextView publishedAt;

        Item(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            source = v.findViewById(R.id.source);
            publishedAt = v.findViewById(R.id.published_at);
        }
    }
}
