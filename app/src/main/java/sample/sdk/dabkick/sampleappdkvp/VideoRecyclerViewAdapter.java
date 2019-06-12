package sample.sdk.dabkick.sampleappdkvp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dabkick.videosdk.publicsettings.DabKickVideoInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.VideoHolder> {

    private ArrayList<DabKickVideoInfo> list;
    private Context context;
    private OnItemClickListener itemClickListener;

    VideoRecyclerViewAdapter(Context context, ArrayList<DabKickVideoInfo> videoInfo, OnItemClickListener itemClickListener) {
        list = videoInfo;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        DabKickVideoInfo videoInfo = list.get(position);
        holder.title.setText(videoInfo.getTitle());
        Picasso.with(context)
                .load(videoInfo.getThumbnailUrl())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    interface OnItemClickListener {
        void onItemClick(DabKickVideoInfo videoInfo);
    }

    class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail;
        TextView title;
        VideoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            thumbnail = itemView.findViewById(R.id.item_video_thumbnail);
            title = itemView.findViewById(R.id.item_video_title);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(list.get(getAdapterPosition()));
        }
    }
}
