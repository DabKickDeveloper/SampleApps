package sample.sdk.dabkick.sampleappdkvp;

import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dabkick.dkvideoplayer.publicsettings.DabkickRegistration;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import sample.sdk.dabkick.sampleappdkvp.MainViewVideos.MainVerticalListAdapter;
import sample.sdk.dabkick.sampleappdkvp.PlayListModels.PlayListModel;
import sample.sdk.dabkick.sampleappdkvp.PlayVideos.PlayerActivity;
import sample.sdk.dabkick.sampleappdkvp.RetrofitInterface.VideoDetailsInterface;
import sample.sdk.dabkick.sampleappdkvp.Slideshow.ImageModel;
import sample.sdk.dabkick.sampleappdkvp.Slideshow.SlidingImage_Adapter;
import sample.sdk.dabkick.sampleappdkvp.Utils.RetrofitInit;
import sample.sdk.dabkick.sampleappdkvp.Utils.Util;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        mDrawerList = findViewById(R.id.navList);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        DabkickRegistration.newInstance().register(this);
        PlayerActivity.isRegistered = true;

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
                    util.playLists = response.body().getPlaylists();
                    util.backgroundImageUrlString = response.body().getBackgroundImageUrl();
                    util.backgroundColorHexString = response.body().getBackgroundColorHex();
                    util.primaryColorHexString = response.body().getPrimaryColorHex();
                    util.appTitleString = response.body().getAppTitle();
                    for (int i = 0; i < response.body().getPlaylists().size(); i++) {
                        util.videosForPlaylists.put(response.body().getPlaylists().get(i).getPlaylistId(), response.body().getPlaylists().get(i).getVideos());

                        // TODO: duration and url of the VideoItemDetails are not yet set
                    }

                    categoriesList.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PlayListModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
            }
        });
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
        String[] osArray = {"Home", "Your Videos", "Your Pictures", "Your Playlists", "", "Settings", "Profile", "Contacts"};
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

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                categoriesList.bringToFront();
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
}