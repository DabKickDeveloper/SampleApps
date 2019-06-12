package sample.sdk.dabkick.sampleappdkvp;

import android.util.Log;

import com.dabkick.videosdk.publicsettings.DabKick;
import com.dabkick.videosdk.publicsettings.SdkApp;

public class PartnerApplication extends SdkApp {


    @Override
    public void onCreate() {
        super.onCreate();

        DabKick.setVideoProvider(Util.createDabKickVideoProvider(null));

        // developer must register successfully before starting a DabKick Session
        DabKick.OnRegisterCallback onRegisterCallback = new DabKick.OnRegisterCallback() {
            @Override
            public void onFinishRegister(boolean success, String errorMsg) {
                if (success) {
                    Log.d("TAG", "registered with DabKick");
                } else {
                    Log.w("TAG", "failed to register with DabKick: " + errorMsg);
                }
            }
        };

//        DabKick.register(this, onRegisterCallback);
    }


}
