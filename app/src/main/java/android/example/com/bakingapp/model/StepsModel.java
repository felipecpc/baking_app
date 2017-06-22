package android.example.com.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felipe on 11/06/17.
 */

public class StepsModel implements Parcelable{

    public static final String DATA = "DATA";
    public static final String ID = "ID";
    public static final String STEP_ID = "STEP_ID";
    public static final String TOTAL_STEPS = "TOTAL_STEPS";

    int id;
    String shortDescription;
    String description;
    String videoURL;
    String thumbnailURL;

    public StepsModel(int id, String sDescription, String description, String video, String thumbnail) {
        this.id = id;
        this.shortDescription = sDescription;
        this.description = description;
        this.videoURL = video;
        this.thumbnailURL = thumbnail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StepsModel> CREATOR = new Creator<StepsModel>() {
        @Override
        public StepsModel createFromParcel(Parcel in) {
            return new StepsModel(in.readInt(), in.readString(), in.readString(),in.readString(),in.readString());
        }

        @Override
        public StepsModel[] newArray(int size) {
            return new StepsModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}
