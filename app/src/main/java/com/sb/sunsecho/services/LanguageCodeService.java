package com.sb.sunsecho.services;

import android.content.res.Resources;

public class LanguageCodeService {

    /**
     * Attempts to find the localized name of the language associated with the given language code.
     *
     * @param languageCode
     * @param res
     * @param packageName
     * @return the localized name of the language code, the language code if no localization has been found.
     */
    public static String localizedLanguageName(String languageCode, Resources res, String packageName) {
        int identifier = res.getIdentifier("language_code_" + languageCode, "string", packageName);
        if (identifier != 0)
            return res.getString(identifier);
        return languageCode;
    }
}
