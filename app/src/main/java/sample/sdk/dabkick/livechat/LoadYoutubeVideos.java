package sample.sdk.dabkick.livechat;

/**
 * Created by iFocus on 13-06-2018.
 */

public class LoadYoutubeVideos {

 /*   private static LoadYoutubeVideos instance;
    static OnFinishedDownloadListener mFinishedDownloadListener = null;

    public static LoadYoutubeVideos getInstance() {

        if (instance == null)
            instance = new LoadYoutubeVideos();
        return instance;
    }

    public LoadYoutubeVideos() {

    }

    //Listener
    public void setOnFinishedDownload(OnFinishedDownloadListener listener) {
        mFinishedDownloadListener = listener;
    }

    public interface OnFinishedDownloadListener {
        void onFinishedDownload(String fullStreamURL, boolean success);
    }

    public static void loadYoutubeURL(Activity mActivity, String videoID) {

        String url = "https://www.youtube.com/watch?v=" + videoID + "&list=FLEYfH4kbq85W_CiOTuSjf8w&feature=mh_lolz";

        new at.huber.youtubeExtractor.YouTubeExtractor(mActivity) {
            @Override
            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {
                if (ytFiles != null) {
                    int itag = 22; //YouTube format identifier
                    try {
                        String downloadUrl;
                        if (ytFiles.get(itag) == null) {
                            downloadUrl = ytFiles.get(ytFiles.keyAt(0)).getUrl();
                        } else {
                            downloadUrl = ytFiles.get(itag).getUrl();
                        }
                        if (mFinishedDownloadListener != null) {
                            mFinishedDownloadListener.onFinishedDownload(downloadUrl, true);
                            mFinishedDownloadListener = null;
                        }

                    } catch (Exception e) {
                        if (mFinishedDownloadListener != null) {
                            mFinishedDownloadListener.onFinishedDownload(null, false);
                            mFinishedDownloadListener = null;
                        }
                    }
                } else {
                    if (mFinishedDownloadListener != null) {
                        mFinishedDownloadListener.onFinishedDownload(null, false);
                        mFinishedDownloadListener = null;
                    }

                }
            }
        }.extract(url, true, true);

    }*/
}
