package sample.sdk.dabkick.sampleappdkvp.VideoDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by iFocus on 11-06-2018.
 */

public class VideoItemDetail {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("image")
    @Expose
    private String thumbnailUrl;

    @SerializedName("duration")
    @Expose
    private String duration;

    @SerializedName("description")
    @Expose
    private String desc;

    private String url;

    public VideoItemDetail(){

        this.url = "";
        this.thumbnailUrl = "";
        this.title = "";
        this.duration = "";
        this.desc = "";

    }

    public VideoItemDetail(String url, String thumbnailUrl, String title, String duration, String desc){

        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
        this.duration = duration;
        this.desc = desc;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
