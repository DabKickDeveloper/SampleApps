package sample.sdk.dabkick.sampleappdkvp.PlayVideos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dabkick.dkvideoplayer.livesession.models.StageModel;
import com.dabkick.dkvideoplayer.livesession.videoplayer.DkVideoView;
import com.dabkick.dkvideoplayer.publicsettings.DabkickRegistration;

import sample.sdk.dabkick.sampleappdkvp.R;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

//import at.huber.youtubeExtractor.VideoMeta;
//import at.huber.youtubeExtractor.YtFile;

public class PlayerActivity extends AppCompatActivity {

    public static VideoItemDetail detail;
    TextView title;
    TextView desc;
    ListView recomended;
    DkVideoView mVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        DabkickRegistration.newInstance().register(this);

        init();
    }

    void init(){

        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        recomended = findViewById(R.id.recomended);
        desc.setMovementMethod(new ScrollingMovementMethod());
        mVideoPlayer = findViewById(R.id.video_view);

        if(detail != null){
            title.setText(detail.getVideoTitle());
            desc.setText(detail.getDesc());
            final String detailUrl = detail.getUrl();

            String videoID = detailUrl.substring(detailUrl.lastIndexOf("=") + 1);

            LoadYoutubeVideos.getInstance().setOnFinishedDownload(new LoadYoutubeVideos.OnFinishedDownloadListener() {
                @Override
                public void onFinishedDownload(String fullStreamURL, boolean success) {
                    if(success){

                        StageModel stageModel = new StageModel();
                        stageModel.setUrl(fullStreamURL);

                        mVideoPlayer.setMediaItem(stageModel);
                        mVideoPlayer.prepare();
                        mVideoPlayer.seekTo(0);
                        mVideoPlayer.play();
                        mVideoPlayer.setVisibility(View.VISIBLE);

                        mVideoPlayer.addPlayerEventListener(new PlayerActivity.EventListener());
                        mVideoPlayer.addPlayerUIListener(new PlayerActivity.EventListener());

                    }else{

                        Toast.makeText(PlayerActivity.this, "Unable to play video", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            LoadYoutubeVideos.getInstance().loadYoutubeURL(PlayerActivity.this, videoID);
        }

    }

    private class EventListener implements DkVideoView.PlayerEventListener,
            DkVideoView.PlayerUIListener{

        @Override
        public void OnError(int i, String s) {

        }


        @Override
        public void OnCompleted() {

        }

        @Override
        public void OnStateChanged(int i) {

        }

        @Override
        public void OnTimeInfo(long l) {

        }

        @Override
        public void OnPositionChanged(long l, long l1) {

        }

        @Override
        public void OnPlay() {

        }

        @Override
        public void OnPaused() {

        }

        @Override
        public void OnBuffering() {

        }

        @Override
        public void OnReady() {

        }

        @Override
        public void OnUIPlay() {

        }

        @Override
        public void OnUIPause() {

        }

        @Override
        public void OnUIFullScreen() {

        }

        @Override
        public void OnUIReplay() {

        }

        @Override
        public void OnUISeek() {

        }

        @Override
        public void OnUiTouch(boolean b) {

        }

        @Override
        public void OnUiNext() {

        }

        @Override
        public void OnUiPrevious() {

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mVideoPlayer != null)
            mVideoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mVideoPlayer != null)
            mVideoPlayer.release();
    }


}
