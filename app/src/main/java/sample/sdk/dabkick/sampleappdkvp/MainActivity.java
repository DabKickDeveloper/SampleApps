package sample.sdk.dabkick.sampleappdkvp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dabkick.dkvideoplayer.livesession.videoplayer.DkVideoView;
import com.dabkick.dkvideoplayer.publicsettings.DabKickRegistration;
import com.dabkick.dkvideoplayer.publicsettings.NotifyStageVideoReceived;
import com.viewpagerindicator.CirclePageIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import sample.sdk.dabkick.sampleappdkvp.MainViewVideos.MainVerticalListAdapter;
import sample.sdk.dabkick.sampleappdkvp.PlayListModels.PlayListModel;
import sample.sdk.dabkick.sampleappdkvp.PlayListModels.Playlist;
import sample.sdk.dabkick.sampleappdkvp.PlayVideos.PlayerActivity;
import sample.sdk.dabkick.sampleappdkvp.RetrofitInterface.VideoDetailsInterface;
import sample.sdk.dabkick.sampleappdkvp.Slideshow.ImageModel;
import sample.sdk.dabkick.sampleappdkvp.Slideshow.SlidingImage_Adapter;
import sample.sdk.dabkick.sampleappdkvp.Utils.RetrofitInit;
import sample.sdk.dabkick.sampleappdkvp.Utils.Util;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    private int[] myImageList = new int[]{R.drawable.dabkick1, R.drawable.dabkick2,
            R.drawable.dabkick3, R.drawable.dabkick4,
            R.drawable.dabkick6, R.drawable.dabkick8};

    ListView categoriesList;
    DabKickRegistration dabkickRegistration = DabKickRegistration.newInstance();
    public DkVideoView mVideoPlayer;
    boolean isFromShareIntent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        this.setTitle("");

        setContentView(R.layout.activity_main);

        if(getIntent().getData() != null)
            isFromShareIntent = true;

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        mDrawerList = findViewById(R.id.navList);

        mVideoPlayer = findViewById(R.id.video_view);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        dabkickRegistration.register(this);
        getLifecycle().addObserver(mVideoPlayer);
        getLifecycle().addObserver(dabkickRegistration);

        initViewPager();

        categoriesList = findViewById(R.id.categories_list);
        final MainVerticalListAdapter adapter = new MainVerticalListAdapter(MainActivity.this);
        categoriesList.setAdapter(adapter);

        addDrawerItems();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupDrawer();

        getPlaylists(new Callback<PlayListModel>() {
            @Override
            public void onResponse(Call<PlayListModel> call, Response<PlayListModel> response) {
                if (response.body() != null && response.isSuccessful()) {
                    Util.APP_NAME = response.body().getAppName();
                    Util util = Util.getInstance();
                    List<Playlist> playlists =  response.body().getPlaylists();
                    util.playLists = playlists;
                    util.backgroundImageUrlString = response.body().getBackgroundImageUrl();
                    util.backgroundColorHexString = response.body().getBackgroundColorHex();
                    util.primaryColorHexString = response.body().getPrimaryColorHex();
                    util.appTitleString = response.body().getAppTitle();

//                    MainActivity.this.setTitle(getString(R.string.app_name_watch_together) + " - " + util.appTitleString);
                    MainActivity.this.setTitle(util.appTitleString);

                    for (int i = 0; i < response.body().getPlaylists().size(); i++) {
                        util.videosForPlaylists.put(response.body().getPlaylists().get(i).getPlaylistId(), response.body().getPlaylists().get(i).getVideos());
                    }
                    //Setting the adapter with new data
                    categoriesList.setAdapter(adapter);

                    if(!isFromShareIntent) {
                        String defaultVideoId = response.body().getDefaultVideoId();
                        if (defaultVideoId != null && !defaultVideoId.equals("")) {
                            play(defaultVideoId);
                        } else if (playlists != null && playlists.size() > 0) {
                            // If there is a video in the playlists, play the first one
                            Playlist playlist = playlists.get(0);
                            if (playlist != null) {
                                List<VideoItemDetail> videos = playlist.getVideos();
                                if (videos != null && videos.size() > 0) {
                                    VideoItemDetail videoItemDetail = videos.get(0);
                                    if (videoItemDetail != null) {
                                        play(videoItemDetail.getId());
                                    }
                                }
                            }
                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PlayListModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });

        mDrawerList.setOnItemClickListener(this);
    }

    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }

    private void initViewPager() {

        mPager = findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(MainActivity.this, imageModelArrayList));

        CirclePageIndicator indicator = findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    private void addDrawerItems() {
        String[] osArray = {"Home", "Settings"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                mDrawerLayout.bringToFront();
                mDrawerList.bringToFront();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mDrawerLayout.bringToFront();
                mDrawerList.bringToFront();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                categoriesList.bringToFront();
                mVideoPlayer.bringToFront();
            }
        };


        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
        categoriesList.bringToFront();
        mVideoPlayer.bringToFront();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void getPlaylists(Callback callback) {
        Retrofit retrofit = new RetrofitInit(getApplicationContext()).initializeRetrofit();
        VideoDetailsInterface videoDetailsInterface = retrofit.create(VideoDetailsInterface.class);
        String urlString = RetrofitInit.BASE_URL + "videoPlayer/v1/getAppInfo.php?appName=" + getPackageName();
        Call<PlayListModel> playListModelCall = videoDetailsInterface.getPlayLists(urlString);
        playListModelCall.enqueue(callback);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mDrawerLayout.closeDrawers();
    }

    public void play(String defaultVideoId) {

            String videoID = defaultVideoId;

            sample.sdk.dabkick.sampleappdkvp.PlayVideos.LoadYoutubeVideos.getInstance().setOnFinishedDownload(new sample.sdk.dabkick.sampleappdkvp.PlayVideos.LoadYoutubeVideos.OnFinishedDownloadListener() {
                @Override
                public void onFinishedDownload(String fullStreamURL, boolean success) {
                    if (success) {

                        mVideoPlayer.setMediaItem(fullStreamURL);
                        mVideoPlayer.prepare(true);
                        mVideoPlayer.bringToFront();

                    } else {

                        Toast.makeText(MainActivity.this, "Unable to play video", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            sample.sdk.dabkick.sampleappdkvp.PlayVideos.LoadYoutubeVideos.getInstance().loadYoutubeURL(MainActivity.this, videoID, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NotifyStageVideoReceived event) {

        Timber.d("onMessageEvent: " + event.url);

        if (event.url != null && mVideoPlayer != null) {
            mVideoPlayer.release();
            mVideoPlayer.setMediaItem(event.url);
            mVideoPlayer.prepare(false);
            mVideoPlayer.showPopUp = false;
            mVideoPlayer.bringToFront();
            ((MainVerticalListAdapter)categoriesList.getAdapter()).unhightSelectedVideo();
//            ((MainVerticalListAdapter)categoriesList.getAdapter()).highlightVideo(event.);
        }
    }
}