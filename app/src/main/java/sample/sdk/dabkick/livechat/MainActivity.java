package sample.sdk.dabkick.livechat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.dabkick.engine.DKServer.Retrofit.Prefs;
import com.dabkick.engine.Livestream.AddUserImpl;
import com.dabkick.engine.Public.AddUser;
import com.dabkick.engine.Public.Authentication;
import com.dabkick.engine.Public.CallbackListener;
import com.dabkick.engine.Public.DabKickEngine;
import com.dabkick.engine.Public.EnginePresenceCallbackListener;
import com.dabkick.engine.Public.LiveChatCallbackListener;
import com.dabkick.engine.Public.MessageInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static boolean isRegistered = false, onConfigurationChange = false;

    LinearLayout btnLayout;
    DabKickEngine engine;
    EditText chatEditText;
    TextView chatTextView;
    ImageButton sendChatMessage;
    RecyclerView chatListView;
    ChatMsgAdapter mChatMessageAdapter;

    List<MessageInfo> mChatMessageList = new ArrayList<MessageInfo>();
    List<AppParticipant> mParticipantList = new ArrayList<AppParticipant>();
    Button watchTogether;
    LiveChatCallbackListener messageDisplayListener;
    RelativeLayout mainLayout;
    Button endSession;
    RecyclerView mAvatarListView;
    AvatarAdapter mAvatarAdapter;
    AddUser addUser;
    View meLayout;
    RecyclerView.LayoutManager mLayoutManager;
    String sessionId;
    Uri intentURI;

    //    EnginePresenceListenerImpl presenterListener;
    EnginePresenceCallbackListener enginePresenceCallbackListener;

    public void initViews() {
        chatEditText = findViewById(R.id.chatEditText);
        sendChatMessage = findViewById(R.id.sendButton);
        watchTogether = findViewById(R.id.btn_invite_friend);
        mainLayout = findViewById(R.id.mainLayout);
        endSession = findViewById(R.id.end_session);
        getLifecycle().addObserver(engine);
        chatListView = findViewById(R.id.listview_chat);
        meLayout = findViewById(R.id.meAvatar);
        TextView meTextView = meLayout.findViewById(R.id.profile_name);
        meTextView.setText("Me");
        setUpLayoutManager();
        mAvatarListView = findViewById(R.id.avatar_recycler_view);
    }

    private void setUpLayoutManager() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        chatListView.setLayoutManager(mLayoutManager);
        chatListView.setItemAnimator(new DefaultItemAnimator());
    }

    public void setUpChatAdapter() {
        if (engine.chatEventListener.getChatMessages().size() > 0) {
            mChatMessageAdapter = new ChatMsgAdapter(MainActivity.this, engine.chatEventListener.getChatMessages());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chatListView.setAdapter(mChatMessageAdapter);
                }
            });
        } else {
            mChatMessageAdapter = new ChatMsgAdapter(MainActivity.this, new ArrayList<MessageInfo>());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chatListView.setAdapter(mChatMessageAdapter);
                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Authentication auth = new Authentication("DKe1ac069ddf1011e7a1d8062", "f84bd8d546b10cff2b601093e47f61");

        messageDisplayListener = new LiveChatCallbackListener() {
            @Override
            public void receivedChatMessage(String senderId, String appUserId, MessageInfo message) {
                MessageInfo chatMsg = new MessageInfo();
                chatMsg.setChatMessage(message.getChatMessage());
                chatMsg.setUserName(message.getUserName());
                chatMsg.setUserId(senderId);
                chatMsg.setChatMsgType(MessageInfo.MESSAGE_TYPE.RECEIVED);
                mChatMessageList.add(chatMsg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatMessageAdapter.updateMessageList(mChatMessageList);
                    }
                });

            }

            @Override
            public void getPreviousMessages(List<MessageInfo> messageInfo) {
                mChatMessageList = messageInfo;
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
            public void userEntered(com.dabkick.engine.Public.UserInfo participant) {
                AppParticipant appParticipant = new AppParticipant(participant.getAppSpecificUserID(), participant.getName(), participant.getProfilePicUrl());
                mParticipantList.add(appParticipant);
                mAvatarAdapter.updateAvatarList(mParticipantList);
                Log.d("TAGGOW", "userEntered: " + participant.getName());
            }

            @Override
            public void userExit(com.dabkick.engine.Public.UserInfo participant) {
//                AppParticipant appParticipant = new AppParticipant(participant.getUserId(), participant.getName(), participant.getProfilePicUrl());
                for (int i = 0; i < mParticipantList.size(); i++) {
                    if (mParticipantList.get(i).getUserId().equalsIgnoreCase(participant.getAppSpecificUserID())) {
                        mParticipantList.remove(i);
                        mAvatarAdapter.updateAvatarList(mParticipantList);
                        Log.d("TAGGOW", "userExites: " + participant.getName());
                    }
                }
            }

        };

        enterSession(getIntent());

        engine = new DabKickEngine(MainActivity.this, auth, sessionId, intentURI,
                messageDisplayListener, enginePresenceCallbackListener, new CallbackListener() {
            @Override
            public void onSuccess(String msg, Object... obj) {
                setUpChatAdapter();

            }

            @Override
            public void onError(String msg, Object... obj) {
                Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
            }
        });

        initViews();

        isRegistered = true;

        sendChatMessage.setOnClickListener(this);
        watchTogether.setOnClickListener(this);
        setUpAvatarAdapter();

        watchTogether.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                engine.onClickInvite();
            }
        });

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
            engine = new DabKickEngine(MainActivity.this, auth, sessionId, intentURI,
                    messageDisplayListener, enginePresenceCallbackListener, new CallbackListener() {
                @Override
                public void onSuccess(String msg, Object... obj) {
                    setUpChatAdapter();

                }

                @Override
                public void onError(String msg, Object... obj) {
                    Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mParticipantList.clear();
        engine.endSession();

        if (onConfigurationChange)
            onConfigurationChange = false;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.sendButton) {
            if (!chatEditText.getText().toString().isEmpty()) {
                if (engine.chatEventListener != null) {
                    MessageInfo messageInfo = new MessageInfo();
                    messageInfo.setChatMessage(chatEditText.getText().toString());
                    messageInfo.setUserName(Prefs.getName());
                    messageInfo.setUserId(Prefs.getUserId());
                    engine.chatEventListener.sendMessage(messageInfo, new CallbackListener() {
                        @Override
                        public void onSuccess(String msg, Object... obj) {
                            Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
                            chatEditText.setText("");
                            Log.d("Test ", msg);

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
        com.dabkick.engine.Public.UserInfo userInfo = new com.dabkick.engine.Public.UserInfo();
        userInfo.setName("Kesh");
        userInfo.setProfilePicUrl("");
        userInfo.setAppSpecificUserID("A12345");
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
    }

    public float convertDpToPixel(Context c, float dp) {
        float density = c.getResources().getDisplayMetrics().density;
        float pixel = dp * density;

        return pixel;
    }

    public void enterSession(Intent intent) {
        intentURI = intent.getData();
        if (intentURI != null) {
            sessionId = getIntent().getExtras().getString("sessionId");
        }
    }
}
