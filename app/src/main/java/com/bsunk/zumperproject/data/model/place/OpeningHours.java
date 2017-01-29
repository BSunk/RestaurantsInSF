
package com.bsunk.zumperproject.data.model.place;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpeningHours implements Parcelable
{

    @SerializedName("open_now")
    @Expose
    private Boolean openNow;
    @SerializedName("periods")
    @Expose
    private List<Period> periods = null;
    @SerializedName("weekday_text")
    @Expose
    private List<String> weekdayText = null;
    public final static Parcelable.Creator<OpeningHours> CREATOR = new Creator<OpeningHours>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OpeningHours createFromParcel(Parcel in) {
            OpeningHours instance = new OpeningHours();
            instance.openNow = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            in.readList(instance.periods, (com.bsunk.zumperproject.data.model.place.Period.class.getClassLoader()));
            in.readList(instance.weekdayText, (java.lang.String.class.getClassLoader()));
            return instance;
        }

        public OpeningHours[] newArray(int size) {
            return (new OpeningHours[size]);
        }

    }
    ;

    public Boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    public List<String> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<String> weekdayText) {
        this.weekdayText = weekdayText;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(openNow);
        dest.writeList(periods);
        dest.writeList(weekdayText);
    }

    public int describeContents() {
        return  0;
    }

}
