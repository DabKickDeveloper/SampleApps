package sample.sdk.dabkick.sampleappdkvp.PlayVideos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import sample.sdk.dabkick.sampleappdkvp.R;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

public class RecomendedListAdapter extends BaseAdapter {

    ArrayList<VideoItemDetail> videos;
    Context mContext;

    RecomendedListAdapter(Context mContext, ArrayList<VideoItemDetail> details){

        this.mContext = mContext;
        videos = details;

    }


    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int i) {
        return videos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final View rowView;

        if (view == null) {
            rowView = LayoutInflater.from(mContext).inflate(R.layout.recommended_videos, null);
        }else{
            rowView = view;
        }

        ImageView thumbnail = (ImageView)rowView.findViewById(R.id.thumbnail);
        Picasso.with(mContext).load(videos.get(i).getThumbnailUrl()).into(thumbnail);

        TextView videoTitle = (TextView)rowView.findViewById(R.id.video_desc);
        videoTitle.setText(videos.get(i).getTitle());

        TextView duration = (TextView)rowView.findViewById(R.id.duration);
        duration.setText(videos.get(i).getDuration());

        RelativeLayout lyt = (RelativeLayout)rowView.findViewById(R.id.main_lyt);
        lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((PlayerActivity)mContext).mVideoPlayer.isPlaying())
                    ((PlayerActivity)mContext).mVideoPlayer.pause();

                ((PlayerActivity)mContext).mVideoPlayer.release();

                PlayerActivity.detail = videos.get(i);
                ((PlayerActivity) mContext).play();
            }
        });

        return rowView;
    }
}
