package sample.sdk.dabkick.sampleappdkvp.PlayVideos;

import android.app.Activity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.dabkick.dkvideoplayer.livesession.models.StageModel;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YtFile;
import timber.log.Timber;

/**
 * Created by iFocus on 13-06-2018.
 */

public class LoadYoutubeVideos {

    private static LoadYoutubeVideos instance;
    OnFinishedDownloadListener mFinishedDownloadListener = null;

    public static LoadYoutubeVideos getInstance(){

        if (instance == null)
            instance = new LoadYoutubeVideos();
        return instance;
    }

    public LoadYoutubeVideos(){

    }

    //Listener
    public void setOnFinishedDownload(OnFinishedDownloadListener listener) {
        mFinishedDownloadListener = listener;
    }

    public interface OnFinishedDownloadListener {
        void onFinishedDownload(String fullStreamURL, boolean success);
    }

    public void loadYoutubeURL(Activity mActivity, String url){

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
                        Log.d("new_video", "stream url: " + downloadUrl);
                        if(mFinishedDownloadListener != null){
                            mFinishedDownloadListener.onFinishedDownload(downloadUrl, true);
                            mFinishedDownloadListener = null;
                        }

                    } catch (Exception e) {
                        Timber.e("exception loading youtube video");
                        Timber.e(e);
                        if(mFinishedDownloadListener != null){
                            mFinishedDownloadListener.onFinishedDownload(null, false);
                            mFinishedDownloadListener = null;
                        }
                    }
                } else {
                    if(mFinishedDownloadListener != null){
                        mFinishedDownloadListener.onFinishedDownload(null, false);
                        mFinishedDownloadListener = null;
                    }

                }
            }
        }.extract(url, true, true);

    }
}
