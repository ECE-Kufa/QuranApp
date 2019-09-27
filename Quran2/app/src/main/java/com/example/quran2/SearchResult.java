package com.example.quran2;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchResult implements Parcelable {
    private String previosAyaNumber;
    private int positionOfPage;

    public SearchResult(String previosAyaNumber, int positionOfPage) {
        this.positionOfPage = positionOfPage;
        this.previosAyaNumber = previosAyaNumber;
    }

    protected SearchResult(Parcel in) {
        previosAyaNumber = in.readString();
        positionOfPage = in.readInt();
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public int getPositionOfPage() {
        return positionOfPage;
    }

    public String getPreviosAyaNumber() {
        return previosAyaNumber;
    }

    public String getAya(){
        String res = "";
        int startOfAya = Data.getPageText(positionOfPage).indexOf(previosAyaNumber) + previosAyaNumber.length() + 1;
        int i = startOfAya;
        while(Data.getPageText(positionOfPage).charAt(i) != '\uFD3E'){
            i++;
        }
        return Data.getPageText(positionOfPage).substring(startOfAya, i+1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(previosAyaNumber);
        dest.writeInt(positionOfPage);
    }
}
