package com.sb.sunsecho.services;

import android.content.res.Resources;

import com.sb.sunsecho.R;
import com.sb.sunsecho.beans.Category;
import com.sb.sunsecho.beans.SortBy;

public class SortByService {

    public static String localize(SortBy sortBy, Resources resources) {
        switch (sortBy) {
            case relevancy:
                return resources.getString(R.string.sort_by_relevancy);
            case popularity:
                return resources.getString(R.string.sort_by_popularity);
            case publishedAt:
                return resources.getString(R.string.sort_by_published_at);
            default:
                throw new IllegalArgumentException(sortBy + " is not a recognized sort.");
        }
    }
}
