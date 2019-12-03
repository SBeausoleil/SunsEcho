package com.sb.sunsecho.beans;

import android.net.Uri;

public interface QueryArguments {
    Uri.Builder toUrlArguments(Uri.Builder builder);
}
