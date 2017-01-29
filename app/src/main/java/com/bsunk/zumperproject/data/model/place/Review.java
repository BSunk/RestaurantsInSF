
package com.bsunk.zumperproject.data.model.place;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review implements Parcelable
{

    @SerializedName("aspects")
    @Expose
    private List<Aspect> aspects = null;
    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("author_url")
    @Expose
    private String authorUrl;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("profile_photo_url")
    @Expose
    private String profilePhotoUrl;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("relative_time_description")
    @Expose
    private String relativeTimeDescription;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("time")
    @Expose
    private Integer time;
    public final static Parcelable.Creator<Review> CREATOR = new Creator<Review>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Review createFromParcel(Parcel in) {
            Review instance = new Review();
            in.readList(instance.aspects, (com.bsunk.zumperproject.data.model.place.Aspect.class.getClassLoader()));
            instance.authorName = ((String) in.readValue((String.class.getClassLoader())));
            instance.authorUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.language = ((String) in.readValue((String.class.getClassLoader())));
            instance.profilePhotoUrl = ((String) in.readValue((String.class.getClassLoader())));
            instance.rating = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.relativeTimeDescription = ((String) in.readValue((String.class.getClassLoader())));
            instance.text = ((String) in.readValue((String.class.getClassLoader())));
            instance.time = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        public Review[] newArray(int size) {
            return (new Review[size]);
        }

    }
    ;

    public List<Aspect> getAspects() {
        return aspects;
    }

    public void setAspects(List<Aspect> aspects) {
        this.aspects = aspects;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getRelativeTimeDescription() {
        return relativeTimeDescription;
    }

    public void setRelativeTimeDescription(String relativeTimeDescription) {
        this.relativeTimeDescription = relativeTimeDescription;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(aspects);
        dest.writeValue(authorName);
        dest.writeValue(authorUrl);
        dest.writeValue(language);
        dest.writeValue(profilePhotoUrl);
        dest.writeValue(rating);
        dest.writeValue(relativeTimeDescription);
        dest.writeValue(text);
        dest.writeValue(time);
    }

    public int describeContents() {
        return  0;
    }

}
