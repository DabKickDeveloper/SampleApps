package sample.sdk.dabkick.sampleappdkvp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dabkick.videosdk.publicsettings.DabKickVideoInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview_videos)
    RecyclerView recyclerView;

    private ArrayList<String> videoCategories = new ArrayList<>(Arrays.asList("Favorite Videos"));
    private Map<String, ArrayList<DabKickVideoInfo>> videoHolder = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        videoHolder.put(videoCategories.get(0), Util.getDailyMailVideos());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
                this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        VideoRecyclerViewAdapter adapter = new VideoRecyclerViewAdapter(this, Util.getDailyMailVideos(),
                new VideoRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(DabKickVideoInfo videoInfo) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        WatchVideoFragment frag = WatchVideoFragment.newInstance(videoInfo);
                        fragmentTransaction.add(R.id.frag_container, frag, WatchVideoFragment.TAG);
                        fragmentTransaction.commit();
                    }
                });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        WatchVideoFragment watchVideoFragment = (WatchVideoFragment) fragmentManager.findFragmentByTag(WatchVideoFragment.TAG);
        if (watchVideoFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(watchVideoFragment);
            fragmentTransaction.commit();
        } else {
            super.onBackPressed();
        }

    }
}