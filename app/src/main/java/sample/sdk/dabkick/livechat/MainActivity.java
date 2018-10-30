package sample.sdk.dabkick.livechat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

import com.dabkick.engine.Livestream.AddUserImpl;
import com.dabkick.engine.Public.AddUser;
import com.dabkick.engine.Public.Authentication;
import com.dabkick.engine.Public.CallbackListener;
import com.dabkick.engine.Public.DabKickEngine;
import com.dabkick.engine.Public.EnginePresenceCallbackListener;
import com.dabkick.engine.Public.LiveChatCallbackListener;
import com.dabkick.engine.Public.MessageInfo;
import com.dabkick.engine.Public.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mInviteBtn, endSession, mChatTogether, mNameDoneBtn;
    EditText chatEditText, mNameEditText;
    ImageButton sendChatMessage;
    RecyclerView chatListView, mAvatarListView;
    LinearLayout mAvatarLayout, mChatBoxContainer, mNameTextLayout;
    RecyclerView.LayoutManager mLayoutManager;

    LiveChatCallbackListener messageDisplayListener;
    EnginePresenceCallbackListener enginePresenceCallbackListener;
    DabKickEngine engine;
    AddUser addUser;

    List<MessageInfo> mChatMessageList = new ArrayList<MessageInfo>();
    List<AppParticipant> mParticipantList = new ArrayList<AppParticipant>();
    ChatMsgAdapter mChatMessageAdapter;
    AvatarAdapter mAvatarAdapter;
    String sessionId;
    Uri intentURI;
    private static boolean isRegistered = false;

    private void setUpLayoutManager() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        chatListView.setLayoutManager(mLayoutManager);
        chatListView.setItemAnimator(new DefaultItemAnimator());
    }

    public void initViews() {
        chatEditText = findViewById(R.id.chatEditText);
        sendChatMessage = findViewById(R.id.send_chat_msg);
        mInviteBtn = findViewById(R.id.btn_invite_friend);
        endSession = findViewById(R.id.end_session);
        chatListView = findViewById(R.id.listview_chat);
        setUpLayoutManager();
        mAvatarListView = findViewById(R.id.avatar_recycler_view);
        mAvatarLayout = findViewById(R.id.avatar_layout);

        mChatTogether = findViewById(R.id.chat_together_btn);
        mChatTogether.setOnClickListener(MainActivity.this);

        mNameTextLayout = findViewById(R.id.name_text_box_container);
        mNameDoneBtn = findViewById(R.id.name_done_btn);
        mNameDoneBtn.setOnClickListener(MainActivity.this);
        mNameEditText = findViewById(R.id.name_edit_text);
        mChatBoxContainer = findViewById(R.id.chat_box_container);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        Authentication auth = new Authentication("DKe1ac069ddf1011e7a1d8062", "f84bd8d546b10cff2b601093e47f61");

        messageDisplayListener = new LiveChatCallbackListener() {
            @Override
            public void receivedChatMessage(String senderId, String appUserId, String message, String name) {
                MessageInfo chatMsg = new MessageInfo();
                chatMsg.setChatMessage(message);
                chatMsg.setUserName(name);
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
            public void userEntered(UserInfo participant) {
                AppParticipant appParticipant = new AppParticipant(participant.getAppSpecificUserID(), participant.getName(), participant.getProfilePicUrl());
                mParticipantList.add(appParticipant);
                mAvatarAdapter.updateAvatarList(mParticipantList);
                Log.d("TAGGOW", "userEntered: " + participant.getName());
            }

            @Override
            public void userExit(UserInfo participant) {
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
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        isRegistered = true;

        sendChatMessage.setOnClickListener(this);
        mInviteBtn.setOnClickListener(this);
        setUpAvatarAdapter();

        mInviteBtn.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.send_chat_msg:
                if (!chatEditText.getText().toString().isEmpty()) {
                    if (engine.chatEventListener != null) {
                        engine.chatEventListener.sendMessage(chatEditText.getText().toString(), new CallbackListener() {
                            @Override
                            public void onSuccess(String msg, Object... obj) {
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                                chatEditText.setText("");
                            }

                            @Override
                            public void onError(String msg, Object... obj) {
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        //error - user needs to initialize engine first
                        Toast.makeText(MainActivity.this, "Invite a Friend, before sending message", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //dismiss KB if up
                    Toast.makeText(MainActivity.this, "Type the message to send", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.chat_together_btn:
                Toast.makeText(MainActivity.this, "chat together clicked", Toast.LENGTH_LONG).show();
                mNameTextLayout.setVisibility(View.VISIBLE);
                mChatTogether.setVisibility(View.GONE);
                //set user name
                break;

            case R.id.name_done_btn:
                if (mNameEditText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter name", Toast.LENGTH_LONG).show();
                } else {
                    endSession.setVisibility(View.VISIBLE);
                    mAvatarLayout.setVisibility(View.VISIBLE);
                    mInviteBtn.setVisibility(View.VISIBLE);
                    mChatBoxContainer.setVisibility(View.VISIBLE);
                    mNameTextLayout.setVisibility(View.GONE);

                }
                break;

        }
    }

    public void enterSession(Intent intent) {
        intentURI = intent.getData();
        if (intentURI != null) {
            sessionId = getIntent().getExtras().getString("sessionId");
        }
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

    public void updateName() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Kesh" + UUID.randomUUID().toString());
        userInfo.setProfilePicUrl("");
        userInfo.setAppSpecificUserID("A12345" + UUID.randomUUID().toString());
        addUser = new AddUserImpl();
        addUser.addUser(userInfo, new CallbackListener() {
            @Override
            public void onSuccess(String msg, Object... obj) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String msg, Object... obj) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
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
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
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
        engine.clearAllData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mParticipantList.clear();
        engine.endSession();
        engine.clearAllData();
    }
}
