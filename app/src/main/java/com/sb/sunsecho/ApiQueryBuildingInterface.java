package com.sb.sunsecho;

import com.sb.sunsecho.beans.ApiQuery;
import com.sb.sunsecho.beans.Article;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ApiQueryBuildingInterface<T extends ApiQuery> {

    ApiQuery.Builder builder();
    Consumer<T> articlesAsyncSupplier(ArticlesReceiver receiver);
}
