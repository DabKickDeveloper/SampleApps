package sample.sdk.dabkick.sampleappdkvp.VideoDetails;

/**
 * Created by iFocus on 11-06-2018.
 */

public class VideoItemDetail {

    String url;
    String thumbnailUrl;
    String videoTitle;
    String duration;
    String desc;

    public VideoItemDetail(){

        this.url = "";
        this.thumbnailUrl = "";
        this.videoTitle = "";
        this.duration = "";
        this.desc = "";

    }

    public VideoItemDetail(String url, String thumbnailUrl, String videoTitle, String duration, String desc){

        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.videoTitle = videoTitle;
        this.duration = duration;
        this.desc = desc;
    }

    public String getUrl(){

        return url;
    }

    public String getThumbnailUrl(){

        return thumbnailUrl;
    }

    public String getVideoTitle(){

        return videoTitle;
    }

    public String getDuration(){

        return duration;
    }

    public String getDesc(){

        return desc;
    }
}
