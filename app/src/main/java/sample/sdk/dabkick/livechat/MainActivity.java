package sample.sdk.dabkick.livechat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

    Button watchTogether, endSession, mNameDoneBtn, mInviteBtn;
    EditText chatEditText, mNameEditText;
    ImageButton sendChatMessage, mChatTogether;
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
    String sessionId, mUserName = "";
    Uri intentURI;
    private static boolean isRegistered = false;
    RelativeLayout mainLayout;
    View meLayout;

    LinearLayout mChatListViewContainer;

    private GestureDetectorCompat gestureDetectorCompat = null;
    Animation slideAnimation;
    TextView mNewMsgText;
    boolean isEndSessionClicked = false, isUserJoinedViaLink = false;
    String currentUserAppSpecificID;


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
        endSession.setOnClickListener(this);
        chatListView = findViewById(R.id.listview_chat);
        mChatListViewContainer = findViewById(R.id.chat_list_view_container);
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
        mainLayout = findViewById(R.id.mainLayout);

        meLayout = findViewById(R.id.meAvatar);
        TextView meTextView = meLayout.findViewById(R.id.profile_name);
        meTextView.setText("Me");
        mNewMsgText = findViewById(R.id.new_msg_text);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        Authentication auth = new Authentication("DKe1ac069ddf1011e7a1d8062", "f84bd8d546b10cff2b601093e47f61");

        messageDisplayListener = new LiveChatCallbackListener() {
            @Override
            public void receivedChatMessage(MessageInfo message) {
//                MessageInfo chatMsg = new MessageInfo();
//                chatMsg.setChatMessage(message.getChatMessage());
//                chatMsg.setUserName(message.getUserName());
//                chatMsg.setUserId(message.getUserId());
//                chatMsg.setChatMsgType(MessageInfo.MESSAGE_TYPE.RECEIVED);
                message.setChatMsgType(MessageInfo.MESSAGE_TYPE.RECEIVED);
                mChatMessageList.add(message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatMessageAdapter.updateMessageList(mChatMessageList);
                        if (mChatListViewContainer.getVisibility() == View.GONE) {
                            mNewMsgText.setText(message.getChatMessage());
                            mNewMsgText.setVisibility(View.VISIBLE);
                            slideAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_animation);
                            mNewMsgText.startAnimation(slideAnimation);

                            slideAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    mNewMsgText.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {
                                }
                            });
                        }
                        chatListView.scrollToPosition(chatListView.getAdapter().getItemCount() - 1);
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
                AppParticipant appParticipant = new AppParticipant(participant.getUserId(), participant.getName(), participant.getProfilePicUrl());
                mParticipantList.add(appParticipant);
                mAvatarAdapter.updateAvatarList(mParticipantList);
                Log.d("TAGGOW", "userEntered: " + participant.getName());
            }

            @Override
            public void userExit(UserInfo participant) {
//                AppParticipant appParticipant = new AppParticipant(participant.getUserId(), participant.getName(), participant.getProfilePicUrl());
                for (int i = 0; i < mParticipantList.size(); i++) {
                    if (mParticipantList.get(i).getUserId().equalsIgnoreCase(participant.getUserId())) {
                        mParticipantList.remove(mParticipantList.get(i));
                        mAvatarAdapter.updateAvatarList(mParticipantList);
                        Log.d("TAGGOW", "userExites: " + participant.getName());
                    }
                }
            }

            @Override
            public void userDataChanged(UserInfo participant) {
                for (int i = 0; i < mParticipantList.size(); i++) {
                    boolean checkCondition = mParticipantList.get(i).getUserId().equalsIgnoreCase(participant.getUserId());
                    if (checkCondition) {
                        mParticipantList.get(i).setUserName(participant.getName());
                        mParticipantList.get(i).setUserPic(participant.getProfilePicUrl());
                        Log.d("TAGGOW", "userDataChanged: ");
                    }
                }
                mAvatarAdapter.updateAvatarList(mParticipantList);
            }

        };
        enterSession(getIntent());

        engine = new DabKickEngine(MainActivity.this, auth, sessionId, intentURI,
                messageDisplayListener, enginePresenceCallbackListener, new CallbackListener() {
            @Override
            public void onSuccess(String msg, Object... obj) {
                setUpChatAdapter();
                if (isUserJoinedViaLink) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatEditText.setHint(R.string.chat_edit_text_hint_after_invite);
                            mChatTogether.callOnClick();
                        }
                    });

                }
            }

            @Override
            public void onError(String msg, Object... obj) {
                Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
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

        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();

        // Set activity in the listener.
        gestureListener.setActivity(this);

        // Create the gesture detector with the gesture listener.
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Pass activity on touch event to the gesture detector.
        gestureDetectorCompat.onTouchEvent(event);
        // Return true to tell android OS that event has been consumed, do not pass it to other event listeners.
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.send_chat_msg:
                if (!chatEditText.getText().toString().isEmpty()) {
                    hideKeyboard(this);
                    if (engine.chatEventListener != null) {
                        if (DabKickEngine.isInviteDone || isUserJoinedViaLink) {
                            MessageInfo messageInfo = new MessageInfo();
                            messageInfo.setChatMessage(chatEditText.getText().toString());
                            messageInfo.setUserId(engine.getUserId());
                            messageInfo.setUserName(engine.getUserName());
                            messageInfo.setAppSpecificUserID(currentUserAppSpecificID);

                            engine.chatEventListener.sendMessage(messageInfo, new CallbackListener() {
                                @Override
                                public void onSuccess(String msg, Object... obj) {
                                    chatEditText.setText("");
                                }

                                @Override
                                public void onError(String msg, Object... obj) {
                                    Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
                                    Log.d("Test ", msg);
                                }
                            });
                        } else {
                            Snackbar.make(mainLayout, "Invite Friend before sending message", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        //error - user needs to initialize engine first
                        Snackbar.make(mainLayout, "Invite a Friend, before sending message", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    //dismiss KB if up

                }
                break;

            case R.id.chat_together_btn:
                //user has already set the name
                if (engine.checkIfPrevSessionExists() && !isUserJoinedViaLink) {
                    showRejoinDialog();
                    hideAllRelatedChatLayouts();
                } else {
                    hideAllRelatedChatLayouts();
                    mChatTogether.setVisibility(View.GONE);
                    if (engine.checkIfUserNameIsSet())
                        mNameDoneBtn.callOnClick();
                    else
                        mNameTextLayout.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.name_done_btn:
                mUserName = mNameEditText.getText().toString().trim();
                if (!engine.checkIfUserNameIsSet()) {
                    if (mNameEditText.getText().toString().isEmpty() && mUserName.isEmpty()) {
                        Snackbar.make(mainLayout, "Enter Name", Snackbar.LENGTH_LONG).show();
                        return;
                    } else {
                        hideKeyboard(this);
                        updateName(mUserName);
                    }
                }
                mNameTextLayout.setVisibility(View.GONE);
                mChatTogether.setVisibility(View.GONE);
                endSession.setVisibility(View.VISIBLE);
                mAvatarLayout.setVisibility(View.VISIBLE);
                mInviteBtn.setVisibility(View.VISIBLE);
                mChatBoxContainer.setVisibility(View.VISIBLE);
                mChatListViewContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.end_session:
                mParticipantList.clear();
                mChatMessageList.clear();
                mAvatarAdapter.updateAvatarList(mParticipantList);
                engine.endSession();
                isEndSessionClicked = true;
                isUserJoinedViaLink = false;

                DabKickEngine.isInviteDone = false;
                mUserName = "";
                engine.chatEventListener.reset();
                mChatTogether.setVisibility(View.VISIBLE);
                mChatTogether.setBackgroundColor(getResources().getColor(R.color.transparent));
                hideAllRelatedChatLayouts();

                break;
        }
    }

    public void enterSession(Intent intent) {
        intentURI = intent.getData();
        //means app is entering from link
        if (intentURI != null) {
            isUserJoinedViaLink = true;
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

    //Way of updating User Name
    public void updateName(String name) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setProfilePicUrl("");
        userInfo.setAppSpecificUserID("A12345" + UUID.randomUUID().toString());
        currentUserAppSpecificID = userInfo.getAppSpecificUserID();
        engine.addUserListener.addUser(userInfo, new CallbackListener() {
            @Override
            public void onSuccess(String msg, Object... obj) {
                //copy obj[0] into currentUserInfo here
            }

            @Override
            public void onError(String msg, Object... obj) {
                Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
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
                    Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
                }
            });
        }

        if (DabKickEngine.isInviteDone) {
            chatEditText.setHint(R.string.chat_edit_text_hint_after_invite);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mParticipantList.clear();
        engine.endSessionOnDestroy();
        engine.clearAllData();
    }

    @Override
    public void onBackPressed() {
        if (endSession.getVisibility() == View.VISIBLE || mNameTextLayout.getVisibility() == View.VISIBLE || mChatMessageAdapter.getItemCount() > 0) {
            mChatTogether.setVisibility(View.VISIBLE);
            hideAllRelatedChatLayouts();
            return;
        }
        super.onBackPressed();
    }

    public void hideAllRelatedChatLayouts(){
        endSession.setVisibility(View.GONE);
        mNameTextLayout.setVisibility(View.GONE);
        mAvatarLayout.setVisibility(View.GONE);
        mChatBoxContainer.setVisibility(View.GONE);
        mChatListViewContainer.setVisibility(View.GONE);
        mInviteBtn.setVisibility(View.GONE);
    }

    public float convertDpToPixel(Context c, float dp) {
        float density = c.getResources().getDisplayMetrics().density;
        float pixel = dp * density;

        return pixel;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showRejoinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Previous Session still exists. Would you like to rejoin?")
                .setCancelable(true)
                .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        engine.rejoinSession(new CallbackListener() {
                            @Override
                            public void onSuccess(String msg, Object... obj) {
                                hideAllRelatedChatLayouts();
                                mChatTogether.setBackgroundResource(R.drawable.rounded_chat_icon);
                                mChatTogether.setVisibility(View.GONE);
                                endSession.setVisibility(View.VISIBLE);
                                mAvatarLayout.setVisibility(View.VISIBLE);
                                mInviteBtn.setVisibility(View.VISIBLE);
                                mChatBoxContainer.setVisibility(View.VISIBLE);
                                mChatListViewContainer.setVisibility(View.VISIBLE);
                                isUserJoinedViaLink = true;
                            }

                            @Override
                            public void onError(String msg, Object... obj) {
                                Snackbar.make(mainLayout, msg, Snackbar.LENGTH_LONG).show();
                                dialog.dismiss();
                                if (engine.checkIfUserNameIsSet()) {
                                    mNameDoneBtn.callOnClick();
                                } else {
                                    mNameTextLayout.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("Start a New One", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        mChatTogether.setBackgroundResource(R.drawable.rounded_chat_icon);
                        if (engine.checkIfUserNameIsSet()) {
                            mNameDoneBtn.callOnClick();
                        } else {
                            mNameTextLayout.setVisibility(View.VISIBLE);
                            mChatTogether.setVisibility(View.GONE);
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Rejoin");
        alert.show();
    }

    public void hideChatList() {

        if (chatListView.getVisibility() == View.VISIBLE) {

            mChatListViewContainer.setVisibility(View.GONE);
        }
    }

    public void showChatList() {

        mChatListViewContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetectorCompat.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

}
