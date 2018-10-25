package sample.sdk.dabkick.sampleappdkvp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.dabkick.engine.Public.AddUser;
import com.dabkick.engine.Public.DabKickEngine;
import com.dabkick.engineapplication.R;
import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;
import java.util.List;


//import com.dabkick.engine.Public.EnginePresenceCallbackListener;
//import com.dabkick.engine.Public.LiveChatCallbackListener;
//import com.dabkick.engine.Public.UserInfo;
//import com.dabkick.engine.Public.VideoEventListener;
//import com.dabkick.engine.Receiver.CheckChooserReceiver;
//import com.dabkick.engine.Util.Util;
//import com.dabkick.engine.video.DkVideoView;

//import com.dabkick.engine.DKServer.Retrofit.Prefs;
//import com.dabkick.engine.Firebase.Common.AbstractDatabaseReferences;
//import com.dabkick.engine.Firebase.Models.Participant;
//import com.dabkick.engine.Livestream.AddUserImpl;
//import com.dabkick.engine.Public.AddUser;
//import com.dabkick.engine.Public.Authentication;
//import com.dabkick.engine.Public.CallbackListener;

//import com.dabkick.dkvideoplayer.Engine.Public.DabKickEngine;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    public DkVideoView mVideoPlayer;
    public VideoView mVideoPlayer;
    ImageButton video1, video2, video3, video4, video5;
    private static boolean isRegistered = false, onConfigurationChange = false;

    LinearLayout btnLayout;
    DabKickEngine engine;
    EditText chatEditText;
    TextView chatTextView;
    ImageButton sendChatMessage;
    RecyclerView chatListView;
    ChatMsgAdapter mChatMessageAdapter;

    List<ChatMsg> mChatMessageList = new ArrayList<ChatMsg>();
    List<AppParticipant> mParticipantList = new ArrayList<AppParticipant>();
    Button watchTogether;
    LiveChatCallbackListener messageDisplayListener;
    RelativeLayout mainLayout;
    Button endSession;
    RecyclerView mAvatarListView;
    AvatarAdapter mAvatarAdapter;
    AddUser addUser;
    View meLayout;

    //    EnginePresenceListenerImpl presenterListener;
    EnginePresenceCallbackListener enginePresenceCallbackListener;

    public void initViews() {
        mVideoPlayer = findViewById(R.id.video_view);
        video1 = findViewById(R.id.video1);
        video2 = findViewById(R.id.video2);
        video3 = findViewById(R.id.video3);
        video4 = findViewById(R.id.video4);
        video5 = findViewById(R.id.video5);
        btnLayout = findViewById(R.id.btn_layout);
        chatEditText = findViewById(R.id.chatEditText);
//        chatTextView = findViewById(R.id.chatTextView);
        sendChatMessage = findViewById(R.id.sendButton);
        watchTogether = findViewById(R.id.btn_invite_friend);
        mainLayout = findViewById(R.id.mainLayout);
        endSession = findViewById(R.id.end_session);
        getLifecycle().addObserver(mVideoPlayer);
        getLifecycle().addObserver(engine);
        chatListView = findViewById(R.id.listview_chat);
        meLayout = findViewById(R.id.meAvatar);
        TextView meTextView = meLayout.findViewById(R.id.profile_name);
        meTextView.setText("Me");

        setUpChatAdapter();
//        listView = findViewById(R.id.listview_chat);
        mAvatarListView = findViewById(R.id.avatar_recycler_view);
    }

    public void setUpChatAdapter() {
        mChatMessageAdapter = new ChatMsgAdapter(MainActivity.this, new ArrayList<ChatMsg>());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        chatListView.setLayoutManager(mLayoutManager);
        chatListView.setItemAnimator(new DefaultItemAnimator());
        chatListView.setAdapter(mChatMessageAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Authentication auth = new Authentication("DKe1ac069ddf1011e7a1d8062", "f84bd8d546b10cff2b601093e47f61");

        messageDisplayListener = new LiveChatCallbackListener() {
            @Override
            public void receivedChatMessage(String senderId, String message, String name) {
                ChatMsg chatMsg = new ChatMsg(message, name, ChatMsg.MESSAGE_TYPE.RECEIVED);
                mChatMessageList.add(chatMsg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatMessageAdapter.updateMessageList(mChatMessageList);
                    }
                });

            }
        };

        enginePresenceCallbackListener = new EnginePresenceCallbackListener() {
            @Override
            public void userEntered(UserInfo participant) {
                AppParticipant appParticipant = new AppParticipant(participant.getAppUserID(), participant.getName(), participant.getProfilePicUrl());
                mParticipantList.add(appParticipant);
                mAvatarAdapter.updateAvatarList(mParticipantList);
                Log.d("TAGGOW", "userEntered: " + participant.getName());
            }

            @Override
            public void userExit(UserInfo participant) {
//                AppParticipant appParticipant = new AppParticipant(participant.getUserId(), participant.getName(), participant.getProfilePicUrl());
                for (int i = 0; i < mParticipantList.size(); i++) {
                    if (mParticipantList.get(i).getUserId().equalsIgnoreCase(participant.getAppUserID())) {
                        mParticipantList.remove(i);
                        mAvatarAdapter.updateAvatarList(mParticipantList);
                        Log.d("TAGGOW", "userExites: " + participant.getName());
                    }
                }
            }

        };

        engine = new DabKickEngine(MainActivity.this, auth, new CallbackListener() {
            @Override
            public void onSuccess(String msg, Object... obj) {
            }

            @Override
            public void onError(String msg, Object... obj) {

            }
        }, messageDisplayListener, enginePresenceCallbackListener);
        initViews();

//        mAvatarAdapter.updateAvatarList(prepareAvatarList());

        isRegistered = true;


            /*@Override
            public void receivedChatMessage(String senderId, String message) {
//                chatTextView.setText(String.valueOf(chatEventListener.getChatMessages().size()));

                Log.d("TAGGOW", "receivedChatMessage: " + presenterListener.getPresenceList().size());

               *//* messageList.clear();
                for (int i = 0; i < chatEventListener.getChatMessages().size(); i++) {
                    messageList.add(chatEventListener.getChatMessages().get(i).getMessage());
                }
                listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, messageList));*//*

            }
        };*/


//        presenterListener = new EnginePresenceListenerImpl();
//        presenterListener.addListener(enginePresenceListener);


        video1.setOnClickListener(this);
        video2.setOnClickListener(this);
        video3.setOnClickListener(this);
        video4.setOnClickListener(this);
        video5.setOnClickListener(this);

        sendChatMessage.setOnClickListener(this);
        watchTogether.setOnClickListener(this);
        setUpAvatarAdapter();

        mVideoPlayer.setVideoEventListner(new VideoEventListener() {
            @Override
            public void addVideo(boolean success) {
                Toast.makeText(MainActivity.this, "video added successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveVideo(String url) {
                Toast.makeText(MainActivity.this, "video received", Toast.LENGTH_SHORT).show();
                //make sure to run in main thread
                synchronized (new Object()) {
                    if (mVideoPlayer != null)
                        mVideoPlayer.release();

                    mVideoPlayer.setVisibility(View.VISIBLE);
                    mVideoPlayer.setMediaItem(url);
                    mVideoPlayer.prepare(false);
                    mVideoPlayer.showPopUp = false;
                }
            }
        });

        video1.setOnClickListener(this);
        video2.setOnClickListener(this);
        video3.setOnClickListener(this);
        video4.setOnClickListener(this);
        video5.setOnClickListener(this);
        watchTogether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInvite();

                //chatEventListener.addListener(messageDisplayListener);

            }
        });

        enterSession(getIntent());


        updateName();

        endSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Remove Participant from Database
                mParticipantList.clear();
                mAvatarAdapter.updateAvatarList(mParticipantList);
                engine.endSession();
                engine.chatEventListener.reset();
            }
        });
    }

    public void setUpAvatarAdapter() {

        if (engine.presenceListener != null) {
            for (int i = 0; i < engine.presenceListener.getPresenceList().size(); i++) {
                AppParticipant appParticipant = new AppParticipant(engine.presenceListener.getPresenceList().get(i).getUserId()
                        , engine.presenceListener.getPresenceList().get(i).getName()
                        , engine.presenceListener.getPresenceList().get(i).getProfilePicUrl());
                mParticipantList.add(appParticipant);
            }
        }

        mAvatarAdapter = new AvatarAdapter(MainActivity.this, mParticipantList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mAvatarListView.setLayoutManager(mLayoutManager);
        mAvatarListView.setItemAnimator(new DefaultItemAnimator());
        mAvatarListView.setAdapter(mAvatarAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isRegistered) {
            Authentication auth = new Authentication("DKe1ac069ddf1011e7a1d8062", "f84bd8d546b10cff2b601093e47f61");
            engine = new DabKickEngine(MainActivity.this, auth, new CallbackListener() {
                @Override
                public void onSuccess(String msg, Object... obj) {
                }

                @Override
                public void onError(String msg, Object... obj) {

                }
            }, messageDisplayListener, enginePresenceCallbackListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mVideoPlayer != null && !onConfigurationChange) {
            getLifecycle().removeObserver(mVideoPlayer);
            mVideoPlayer.release();
        }

        if (onConfigurationChange)
            onConfigurationChange = false;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.sendButton) {
            if (!chatEditText.getText().toString().isEmpty()) {
                if (engine.chatEventListener != null) {
                    engine.chatEventListener.sendMessage(chatEditText.getText().toString(), new CallbackListener() {
                        @Override
                        public void onSuccess(String msg, Object... obj) {
                            Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
                            chatEditText.setText("");
                            Log.d("Test ", msg);

//                            ChatMsg chatMsg = new ChatMsg(msg, Prefs.getName(), ChatMsg.MESSAGE_TYPE.SENT);
//                            mChatMessageList.add(chatMsg);
//                            mChatMessageAdapter.updateMessageList(mChatMessageList);
                        }

                        @Override
                        public void onError(String msg, Object... obj) {
                            Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
                            Log.d("Test ", msg);
                        }
                    });
                } else {
                    //error - user needs to initialize engine first
                    Snackbar.make(mainLayout, "Invite a Friend, before sending message", Snackbar.LENGTH_LONG).show();
                }
            } else {
                //dismiss KB if up

            }
            return;
        } /*else if (v.getId() == R.id.watchTogether) {
            onClickInvite();
            return;
        }*/

       /* if (mVideoPlayer != null)
            mVideoPlayer.release();
        String url = "", videoID = "";

        if (v.getId() == R.id.video1) {
            url = "https://www.youtube.com/watch?v=jiuKa0eO0-A";
            videoID = url.substring(url.lastIndexOf("=") + 1);
        } else if (v.getId() == R.id.video2) {
            url = "https://www.youtube.com/watch?v=k9KM-aPj-d0";
            videoID = url.substring(url.lastIndexOf("=") + 1);
        } else if (v.getId() == R.id.video3) {
            url = "https://www.youtube.com/watch?v=_xA0AU0xOuE";
            videoID = url.substring(url.lastIndexOf("=") + 1);
        } else if (v.getId() == R.id.video4) {
            url = "https://www.youtube.com/watch?v=4G4lIXQeCgc";
            videoID = url.substring(url.lastIndexOf("=") + 1);
        } else if (v.getId() == R.id.video5) {
            url = "https://www.youtube.com/watch?v=-go6B_OxpoY";
            videoID = url.substring(url.lastIndexOf("=") + 1);
        }

        LoadYoutubeVideos.getInstance().setOnFinishedDownload((fullStreamURL, success) -> {
            Log.d("MainActivity", "success: " + success);

            if (success) {
                mVideoPlayer.setVisibility(View.VISIBLE);
                mVideoPlayer.setMediaItem(fullStreamURL);
                mVideoPlayer.prepare(true);
            } else {

                Toast.makeText(MainActivity.this, "Unable to play video", Toast.LENGTH_SHORT).show();
            }
        });

        LoadYoutubeVideos.loadYoutubeURL(MainActivity.this, videoID);
        //this.hasLoadedVideos = true;*/
    }

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NotifyStageVideoReceived event) {
        synchronized (new Object()) {
            Timber.d("onMessageEvent: " + event.url);
            if (mVideoPlayer != null)
                mVideoPlayer.release();

            mVideoPlayer.setVisibility(View.VISIBLE);
            mVideoPlayer.setMediaItem(event.url);
            mVideoPlayer.prepare(false);
            mVideoPlayer.showPopUp = false;
        }
    }*/

    public void onClickInvite() {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String appName = Util.getAppName(getApplicationContext());
        String stagingUrl = Util.getQueryServerUrl(getApplicationContext());

        String serverLink = String.format(
                stagingUrl + "/sdk/user/sdkInvite.php?s=%s&d=%s",
                AbstractDatabaseReferences.getSessionId(),
                "DKe1ac069ddf1011e7a1d8062");
        String text = "Hey \n" +
                "Come and watch this video with me, I am waiting on " + appName + " by clicking " + serverLink;
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        Intent receiver = new Intent(getApplicationContext(), CheckChooserReceiver.class);
        receiver.putExtra("test", "test");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, receiver, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent chooserIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            chooserIntent = Intent.createChooser(shareIntent, "Share Room With", pendingIntent.getIntentSender());
        } else {
            chooserIntent = Intent.createChooser(shareIntent, "Share Room With");
        }
        if (chooserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooserIntent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    //Way of updating User Name
    public void updateName() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Kesh");
        userInfo.setProfilePicUrl("");
        userInfo.setAppUserID("A12345");
        addUser = new AddUserImpl();
        addUser.addUser(userInfo, new CallbackListener() {
            @Override
            public void onSuccess(String msg, Object... obj) {
                Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onError(String msg, Object... obj) {
                Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChange = true;
        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            btnLayout.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mVideoPlayer.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            mVideoPlayer.setLayoutParams(params);
            mVideoPlayer.setBackgroundColor(Color.BLACK);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            btnLayout.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mVideoPlayer.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = (int) convertDpToPixel(this, 200);
            mVideoPlayer.setLayoutParams(params);
        }
    }

    public float convertDpToPixel(Context c, float dp) {
        float density = c.getResources().getDisplayMetrics().density;
        float pixel = dp * density;

        return pixel;
    }

    public void enterSession(Intent intent) {
        Uri intentUri = intent.getData();
        if (intentUri != null) {
            String sessionId = getIntent().getExtras().getString("sessionId");
            Prefs.setSessionId(sessionId);
//            presenterListener = new EnginePresenceListenerImpl();
//            presenterListener.addListener(enginePresenceListener);
        }
    }
}
