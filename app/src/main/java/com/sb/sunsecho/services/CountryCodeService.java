package com.sb.sunsecho.services;

import android.content.res.Resources;

import com.sb.sunsecho.beans.CountryCode;

public class CountryCodeService {

    public String localizedCountryName(CountryCode countryCode, Resources res, String packageName) {
        return res.getString(res.getIdentifier("country_code_" + countryCode.name(), "string", packageName));
    }
}
