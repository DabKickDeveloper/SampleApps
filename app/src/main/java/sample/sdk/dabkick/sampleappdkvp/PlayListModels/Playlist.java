
package sample.sdk.dabkick.sampleappdkvp.PlayListModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

public class Playlist {

    @SerializedName("playlistId")
    @Expose
    private String playlistId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("videos")
    @Expose
    private List<VideoItemDetail> videos = null;

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VideoItemDetail> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoItemDetail> videos) {
        this.videos = videos;
    }

}
