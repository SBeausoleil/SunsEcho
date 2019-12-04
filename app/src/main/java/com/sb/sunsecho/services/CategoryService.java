package com.sb.sunsecho.services;

import android.content.res.Resources;

import com.sb.sunsecho.R;
import com.sb.sunsecho.beans.Category;

public class CategoryService {

    public static String localize(Category category, Resources resources) {
        switch (category) {
            case business:
                return resources.getString(R.string.category_business);
            case entertainment:
                return resources.getString(R.string.category_entertainment);
            case general:
                return resources.getString(R.string.category_general);
            case health:
                return resources.getString(R.string.category_health);
            case science:
                return resources.getString(R.string.category_science);
            case sports:
                return resources.getString(R.string.category_sports);
            case technology:
                return resources.getString(R.string.category_technology);
            default:
                throw new IllegalArgumentException(category + " is not a recognized category.");
        }
    }
}
