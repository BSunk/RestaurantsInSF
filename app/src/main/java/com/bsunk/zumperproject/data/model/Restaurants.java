
package com.bsunk.zumperproject.data.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurants implements Parcelable
{

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions = null;
    @SerializedName("next_page_token")
    @Expose
    private String nextPageToken;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Parcelable.Creator<Restaurants> CREATOR = new Creator<Restaurants>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Restaurants createFromParcel(Parcel in) {
            Restaurants instance = new Restaurants();
            in.readList(instance.htmlAttributions, (java.lang.Object.class.getClassLoader()));
            instance.nextPageToken = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.results, (com.bsunk.zumperproject.data.model.Result.class.getClassLoader()));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Restaurants[] newArray(int size) {
            return (new Restaurants[size]);
        }

    }
    ;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(htmlAttributions);
        dest.writeValue(nextPageToken);
        dest.writeList(results);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
