
package com.bsunk.zumperproject.data.model.place;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Period implements Parcelable
{

    @SerializedName("close")
    @Expose
    private Close close;
    @SerializedName("open")
    @Expose
    private Open open;
    public final static Parcelable.Creator<Period> CREATOR = new Creator<Period>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Period createFromParcel(Parcel in) {
            Period instance = new Period();
            instance.close = ((Close) in.readValue((Close.class.getClassLoader())));
            instance.open = ((Open) in.readValue((Open.class.getClassLoader())));
            return instance;
        }

        public Period[] newArray(int size) {
            return (new Period[size]);
        }

    }
    ;

    public Close getClose() {
        return close;
    }

    public void setClose(Close close) {
        this.close = close;
    }

    public Open getOpen() {
        return open;
    }

    public void setOpen(Open open) {
        this.open = open;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(close);
        dest.writeValue(open);
    }

    public int describeContents() {
        return  0;
    }

}
