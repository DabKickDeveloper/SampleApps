package sample.sdk.dabkick.sampleappdkvp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import sample.sdk.dabkick.sampleappdkvp.PlayListModels.Video;
import sample.sdk.dabkick.sampleappdkvp.Utils.Util;

import java.util.ArrayList;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;
import sample.sdk.dabkick.sampleappdkvp.VideoDetails.VideoItemDetail;

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
        final String categoryText = Util.getInstance().playLists.get(position).getPlaylistId();
        category.setText(categoryText);

        TextView more = (TextView) rowView.findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(mActivity, VideosOfCategory.class);
//                VideosOfCategory.videos = Util.getInstance().categoryMap.get(categoryText);
//                intent.putExtra("Name", categoryText);
//                mActivity.startActivity(intent);

            }
        });

        HListView listView = (HListView) rowView.findViewById(R.id.videos_list);
        ArrayList<VideoItemDetail> videoItemDetails = new ArrayList<VideoItemDetail>((Util.getInstance().videosForPlaylists.get(categoryText)).subList(0,4));
        HorizontalListAdapter adapter = new HorizontalListAdapter(mActivity, videoItemDetails);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

//                Intent intent = new Intent(mActivity, PlayerActivity.class);
//                PlayerActivity.detail = (Util.getInstance().categoryMap.get(categoryText)).get(pos);
//                mActivity.startActivity(intent);
            }
        });

        return rowView;
    }
}
