package com.sb.sunsecho.beans;

import android.os.Parcel;
import android.os.Parcelable;

public enum Category implements Parcelable {
    business, entertainment, general, health, science, sports, technology;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return Category.valueOf(in.readString());
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
