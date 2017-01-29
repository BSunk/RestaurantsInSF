
package com.bsunk.zumperproject.data.model.place;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aspect implements Parcelable
{

    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("type")
    @Expose
    private String type;
    public final static Parcelable.Creator<Aspect> CREATOR = new Creator<Aspect>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Aspect createFromParcel(Parcel in) {
            Aspect instance = new Aspect();
            instance.rating = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.type = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Aspect[] newArray(int size) {
            return (new Aspect[size]);
        }

    }
    ;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(rating);
        dest.writeValue(type);
    }

    public int describeContents() {
        return  0;
    }

}
