
package com.bsunk.zumperproject.data.model.place;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place implements Parcelable
{

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions = null;
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("status")
    @Expose
    private String status;
    public final static Parcelable.Creator<Place> CREATOR = new Creator<Place>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Place createFromParcel(Parcel in) {
            Place instance = new Place();
            in.readList(instance.htmlAttributions, (java.lang.Object.class.getClassLoader()));
            instance.result = ((Result) in.readValue((Result.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Place[] newArray(int size) {
            return (new Place[size]);
        }

    }
    ;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(htmlAttributions);
        dest.writeValue(result);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
