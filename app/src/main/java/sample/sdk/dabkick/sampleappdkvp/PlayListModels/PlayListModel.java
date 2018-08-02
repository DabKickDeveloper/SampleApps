
package sample.sdk.dabkick.sampleappdkvp.PlayListModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayListModel {

    @SerializedName("appName")
    @Expose
    private String appName;

    @SerializedName("playlists")
    @Expose
    private List<Playlist> playlists = null;

    @SerializedName("backgroundImageUrl")
    @Expose
    private String backgroundImageUrl;

    @SerializedName("backgroundColorHex")
    @Expose
    private String backgroundColorHex;

    @SerializedName("primaryColorHex")
    @Expose
    private String primaryColorHex;

    @SerializedName("appTitle")
    @Expose
    private String appTitle;

    @SerializedName("defaultVideoId")
    @Expose
    private String defaultVideoId;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getBackgroundColorHex() {
        return backgroundColorHex;
    }

    public void setBackgroundColorHex(String backgroundColorHex) {
        this.backgroundColorHex = backgroundColorHex;
    }

    public String getPrimaryColorHex() {
        return primaryColorHex;
    }

    public void setPrimaryColorHex(String primaryColorHex) {
        this.primaryColorHex = primaryColorHex;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getDefaultVideoId() {
        return defaultVideoId;
    }

    public void setDefaultVideoId(String defaultVideoId) {
        this.defaultVideoId = defaultVideoId;
    }
}
