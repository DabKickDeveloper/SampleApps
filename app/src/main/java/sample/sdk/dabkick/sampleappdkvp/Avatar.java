package sample.sdk.dabkick.sampleappdkvp;

import android.widget.ImageView;

public class Avatar {

    String mUserName;
    ImageView mUserProfileImg;

    public Avatar(String mUserName, int mUserProfileImg) {
        this.mUserName = mUserName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public ImageView getUserProfileImg() {
        return mUserProfileImg;
    }

    public void setUserProfileImg(ImageView mUserProfileImg) {
        this.mUserProfileImg = mUserProfileImg;
    }
}
