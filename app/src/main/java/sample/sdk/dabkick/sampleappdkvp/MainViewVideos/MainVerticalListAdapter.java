package sample.sdk.dabkick.sampleappdkvp.MainViewVideos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;
import sample.sdk.dabkick.sampleappdkvp.CategoryViewForVideos.VideosOfCategory;
import sample.sdk.dabkick.sampleappdkvp.PlayVideos.PlayerActivity;
import sample.sdk.dabkick.sampleappdkvp.R;
import sample.sdk.dabkick.sampleappdkvp.Util.Util;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

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
        return Util.getInstance().categoryMap.size();
    }

    @Override
    public Object getItem(int position) {
        return Util.getInstance().categoryMap.get(Util.getInstance().categories.get(position));
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
        final String categoryText = Util.getInstance().categories.get(position);
        category.setText(categoryText);

        TextView more = (TextView) rowView.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mActivity, VideosOfCategory.class);
                VideosOfCategory.videoItemDetails = Util.getInstance().categoryMap.get(categoryText);
                intent.putExtra("Name", categoryText);
                mActivity.startActivity(intent);

            }
        });

        HListView listView = (HListView) rowView.findViewById(R.id.videos_list);
        ArrayList<VideoItemDetail> videoItemDetails = new ArrayList<VideoItemDetail>((Util.getInstance().categoryMap.get(categoryText)).subList(0,4));
        HorizontalListAdapter adapter = new HorizontalListAdapter(mActivity, videoItemDetails);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Intent intent = new Intent(mActivity, PlayerActivity.class);
                PlayerActivity.detail = (Util.getInstance().categoryMap.get(categoryText)).get(pos);
                mActivity.startActivity(intent);
            }
        });

        return rowView;
    }
}
