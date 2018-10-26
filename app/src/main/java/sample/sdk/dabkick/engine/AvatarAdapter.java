package sample.sdk.dabkick.engine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dabkick.engineapplication.R;

import java.util.List;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder> {

    private List<AppParticipant> avatarList;
    private Context context;

    public AvatarAdapter(Context context, List<AppParticipant> avatarList){
        this.context = context;
        this.avatarList = avatarList;
    }

    @NonNull
    @Override
    public AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.avatar_item, parent, false);

        return new AvatarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarViewHolder holder, int position) {


            AppParticipant avatar = avatarList.get(position);
            holder.profileName.setText(avatar.getUserName());


//        holder.profileImage.setImageResource(avatar.getUserProfileImg());

    }

    public void updateAvatarList(List<AppParticipant> avatarList){
        this.avatarList = avatarList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(avatarList == null)
            return 0;

        return avatarList.size();
    }

    public class AvatarViewHolder extends RecyclerView.ViewHolder {

        private ImageView profileImage;
        private TextView profileName;

        public AvatarViewHolder(View itemView) {
            super(itemView);

            profileImage = (ImageView) itemView.findViewById(R.id.profile_img);
            profileName = (TextView) itemView.findViewById(R.id.profile_name);
        }
    }
}
