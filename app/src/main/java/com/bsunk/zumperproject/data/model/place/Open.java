
package com.bsunk.zumperproject.data.model.place;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Open implements Parcelable
{

    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("time")
    @Expose
    private String time;
    public final static Parcelable.Creator<Open> CREATOR = new Creator<Open>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Open createFromParcel(Parcel in) {
            Open instance = new Open();
            instance.day = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.time = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Open[] newArray(int size) {
            return (new Open[size]);
        }

    }
    ;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(day);
        dest.writeValue(time);
    }

    public int describeContents() {
        return  0;
    }

}
