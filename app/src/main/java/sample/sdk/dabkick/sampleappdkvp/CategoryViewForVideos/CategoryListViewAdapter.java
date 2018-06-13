package sample.sdk.dabkick.sampleappdkvp.CategoryViewForVideos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sample.sdk.dabkick.sampleappdkvp.PlayVideos.PlayerActivity;
import sample.sdk.dabkick.sampleappdkvp.R;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

/**
 * Created by iFocus on 11-06-2018.
 */

public class CategoryListViewAdapter extends BaseAdapter {

    ArrayList<VideoItemDetail> videoItemDetails;
    Context mActivity;

    CategoryListViewAdapter(Context mActivity, ArrayList<VideoItemDetail> videoItemDetails){

        this.mActivity = mActivity;
        this.videoItemDetails = videoItemDetails;

    }

    @Override
    public int getCount() {
        return videoItemDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return videoItemDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View rowView;

        if (convertView == null) {
            rowView = LayoutInflater.from(mActivity).inflate(R.layout.category_list_item, null);
        }else{
            rowView = convertView;
        }

        ImageView thumbnail = (ImageView)rowView.findViewById(R.id.thumbnail);
        Picasso.with(mActivity).load(videoItemDetails.get(position).getThumbnailUrl()).into(thumbnail);

        TextView videoTitle = (TextView)rowView.findViewById(R.id.video_desc);
        TextView duration = (TextView)rowView.findViewById(R.id.duration);

        videoTitle.setText(videoItemDetails.get(position).getVideoTitle());
        duration.setText(videoItemDetails.get(position).getDuration());

        RelativeLayout clickableLayout = (RelativeLayout)rowView.findViewById(R.id.clickable_layout);
        clickableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mActivity, PlayerActivity.class);
                PlayerActivity.detail = videoItemDetails.get(position);
                mActivity.startActivity(intent);
            }
        });

        return rowView;
    }
}
