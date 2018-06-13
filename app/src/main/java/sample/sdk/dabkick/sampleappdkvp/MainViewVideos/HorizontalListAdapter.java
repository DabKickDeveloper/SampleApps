package sample.sdk.dabkick.sampleappdkvp.MainViewVideos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sample.sdk.dabkick.sampleappdkvp.R;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

/**
 * Created by iFocus on 11-06-2018.
 */

public class HorizontalListAdapter extends BaseAdapter {

    ArrayList<VideoItemDetail> videoItemDetails;
    Context mActivity;

    HorizontalListAdapter(Context mActivity, ArrayList<VideoItemDetail> videoItemDetails){

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
    public View getView(int position, View convertView, ViewGroup parent) {
        final View rowView;

        if (convertView == null) {
            rowView = LayoutInflater.from(mActivity).inflate(R.layout.horizontal_list_item, null);
        }else{
            rowView = convertView;
        }

        ImageView thumbnailImg = (ImageView) rowView.findViewById(R.id.thumbnail);
        TextView videoDesc = (TextView) rowView.findViewById(R.id.video_desc);

        Picasso.with(mActivity).setLoggingEnabled(true);
        Picasso.with(mActivity)
                .load(videoItemDetails.get(position).getThumbnailUrl()).into(thumbnailImg);

        videoDesc.setText(videoItemDetails.get(position).getVideoTitle());

        return rowView;
    }
}
