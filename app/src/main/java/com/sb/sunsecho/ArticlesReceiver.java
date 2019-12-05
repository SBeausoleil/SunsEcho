package com.sb.sunsecho;

import com.sb.sunsecho.beans.Article;

@FunctionalInterface
public interface ArticlesReceiver {
    void receive(Article[] articles);
}
