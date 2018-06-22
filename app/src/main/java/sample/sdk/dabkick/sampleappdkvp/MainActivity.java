package sample.sdk.dabkick.sampleappdkvp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.dabkick.dkvideoplayer.publicsettings.DabkickRegistration;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import sample.sdk.dabkick.sampleappdkvp.MainViewVideos.MainVerticalListAdapter;
import sample.sdk.dabkick.sampleappdkvp.PlayVideos.PlayerActivity;
import sample.sdk.dabkick.sampleappdkvp.Slideshow.ImageModel;
import sample.sdk.dabkick.sampleappdkvp.Slideshow.SlidingImage_Adapter;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

public class MainActivity extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.dabkick1, R.drawable.dabkick2,
            R.drawable.dabkick3, R.drawable.dabkick4
            , R.drawable.dabkick6, R.drawable.dabkick8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();

        DabkickRegistration.newInstance().register(this);
        PlayerActivity.isRegistered = true;

        initViewPager();

        ListView categoriesList = (ListView)findViewById(R.id.categories_list);
        MainVerticalListAdapter adapter = new MainVerticalListAdapter(MainActivity.this);
        categoriesList.setAdapter(adapter);



       /* VideoItemDetail videoItemDetail = new VideoItemDetail("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4", "", "", "", "");

        Intent intent = new Intent(this, PlayerActivity.class);
        PlayerActivity.detail = videoItemDetail;
        startActivity(intent);*/

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

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(MainActivity.this, imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

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
}
