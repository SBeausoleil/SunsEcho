package com.sb.sunsecho.services;

import android.content.res.Resources;

import com.sb.sunsecho.beans.CountryCode;

public class CountryCodeService {

    /**
     * Attempts to find the localized name of the country associated with the given country code.
     *
     * @param countryCode
     * @param res
     * @param packageName
     * @return the localized name of the country code, the country code if no localization has been found.
     */
    public String localizedCountryName(String countryCode, Resources res, String packageName) {
        int identifier = res.getIdentifier("country_code_" + countryCode, "string", packageName);
        if (identifier != 0)
            return res.getString(identifier);
        return countryCode;
    }
}
