package sample.sdk.dabkick.sampleappdkvp.MainViewVideos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dabkick.dkvideoplayer.livesession.stage.VideoManager;

import java.util.List;
import java.util.ArrayList;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;
import sample.sdk.dabkick.sampleappdkvp.CategoryViewForVideos.VideosOfCategory;
import sample.sdk.dabkick.sampleappdkvp.PlayVideos.PlayerActivity;
import sample.sdk.dabkick.sampleappdkvp.R;
import sample.sdk.dabkick.sampleappdkvp.Utils.Util;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;
import sample.sdk.dabkick.sampleappdkvp.PlayListModels.Video;

/**
 * Created by iFocus on 11-06-2018.
 */

public class MainVerticalListAdapter extends BaseAdapter {

    Context mActivity;

    public MainVerticalListAdapter(Context mActivity){

        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return Util.getInstance().videosForPlaylists.size();
    }

    @Override
    public Object getItem(int position) {
        return Util.getInstance().videosForPlaylists.get(Util.getInstance().playLists.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View rowView;

        if (convertView == null) {
            rowView = LayoutInflater.from(mActivity).inflate(R.layout.list_item, null);
        }else{
            rowView = convertView;
        }

        TextView category = (TextView) rowView.findViewById(R.id.category);
        final String categoryText = Util.getInstance().playLists.get(position).getName();
        category.setText(categoryText);
        final String categoryId = Util.getInstance().playLists.get(position).getPlaylistId();

        TextView more = (TextView) rowView.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mActivity, VideosOfCategory.class);
                List<VideoItemDetail> videoItemDetails = Util.getInstance().videosForPlaylists.get(categoryId);
                VideosOfCategory.videos = videoItemDetails;
                intent.putExtra("Name", categoryText);
                mActivity.startActivity(intent);

            }
        });

        HListView listView = rowView.findViewById(R.id.videos_list);
        final List<VideoItemDetail> videoItemDetails  = Util.getInstance().videosForPlaylists.get(categoryId).subList(0,4);

        HorizontalListAdapter adapter = new HorizontalListAdapter(mActivity, videoItemDetails);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Intent intent = new Intent(mActivity, PlayerActivity.class);
                VideoItemDetail videoItemDetail = Util.getInstance().videosForPlaylists.get(categoryId).get(pos);

                PlayerActivity.detail = videoItemDetail;
                mActivity.startActivity(intent);
            }
        });

        return rowView;
    }
}
