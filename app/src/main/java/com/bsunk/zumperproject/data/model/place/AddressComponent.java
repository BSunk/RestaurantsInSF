
package com.bsunk.zumperproject.data.model.place;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressComponent implements Parcelable
{

    @SerializedName("long_name")
    @Expose
    private String longName;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    public final static Parcelable.Creator<AddressComponent> CREATOR = new Creator<AddressComponent>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AddressComponent createFromParcel(Parcel in) {
            AddressComponent instance = new AddressComponent();
            instance.longName = ((String) in.readValue((String.class.getClassLoader())));
            instance.shortName = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.types, (java.lang.String.class.getClassLoader()));
            return instance;
        }

        public AddressComponent[] newArray(int size) {
            return (new AddressComponent[size]);
        }

    }
    ;

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(longName);
        dest.writeValue(shortName);
        dest.writeList(types);
    }

    public int describeContents() {
        return  0;
    }

}
