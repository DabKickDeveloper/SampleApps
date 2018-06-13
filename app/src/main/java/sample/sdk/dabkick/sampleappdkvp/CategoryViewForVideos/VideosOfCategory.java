package sample.sdk.dabkick.sampleappdkvp.CategoryViewForVideos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import sample.sdk.dabkick.sampleappdkvp.R;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

public class VideosOfCategory extends AppCompatActivity {

    public static ArrayList<VideoItemDetail> videoItemDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_of_category);

        String title = getIntent().getExtras().getString("Name");
        setTitle(title);

        ListView videosOfCategory = (ListView)findViewById(R.id.videos_of_category);
        CategoryListViewAdapter adapter = new CategoryListViewAdapter(VideosOfCategory.this, videoItemDetails);
        videosOfCategory.setAdapter(adapter);
    }
}
