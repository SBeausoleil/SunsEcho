package com.sb.sunsecho;

import com.sb.sunsecho.beans.Source;

@FunctionalInterface
public interface SourcesReceiver {

    void receive(Source[] sources);
}
