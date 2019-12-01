package com.sb.sunsecho.beans;

import android.net.Uri;

public interface QueryArguments {
    void toUrlArguments(Uri.Builder builder);
}
