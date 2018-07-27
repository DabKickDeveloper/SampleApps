package sample.sdk.dabkick.sampleappdkvp.Utils;

import sample.sdk.dabkick.sampleappdkvp.PlayListModels.Playlist;
import sample.sdk.dabkick.sampleappdkvp.PlayListModels.Video;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Util {
    public static String APP_NAME = "";

    private static Util instance;
    public List<Playlist> playLists = new ArrayList<>();
    public Map<String, List<VideoItemDetail>> videosForPlaylists = new ConcurrentHashMap<>();
    public String backgroundImageUrlString;
    public String backgroundColorHexString;
    public String primaryColorHexString;
    public String appTitleString;

    public static Util getInstance() {

        if (instance == null)
            instance = new Util();
        return instance;
    }

    public float convertDpToPixel(Context c, float dp) {
        float density = c.getResources().getDisplayMetrics().density;
        float pixel = dp * density;

        return pixel;
    }

}
