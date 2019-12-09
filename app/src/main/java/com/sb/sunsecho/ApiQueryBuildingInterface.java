package com.sb.sunsecho;

import com.sb.sunsecho.beans.ApiQuery;

import java.util.function.Consumer;

public interface ApiQueryBuildingInterface<T extends ApiQuery> {

    ApiQuery.Builder builder();
    Consumer<T> articlesAsyncSupplier(ArticlesReceiver receiver, Consumer<TooBroadQueryException> catcher);
}
