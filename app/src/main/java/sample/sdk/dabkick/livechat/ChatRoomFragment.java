package sample.sdk.dabkick.livechat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dabkick.engine.Public.Authentication;
import com.dabkick.engine.Public.CallbackListener;
import com.dabkick.engine.Public.LiveChatCallbackListener;
import com.dabkick.engine.Public.MessageInfo;
import com.dabkick.engine.Public.UserInfo;
import com.dabkick.engine.Public.UserPresenceCallBackListener;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import timber.log.Timber;

public class ChatRoomFragment extends Fragment implements ChatSessionFragment.ChatBackPress, ChatSessionFragment.ReceivedMessageUpdate {

    View view;
    private int roomPos;

    private TextView mRoomName, mUserCount, mRoomLocation, mRoomDate, mRoomDescription;
    private ImageButton mChatTogetherBtn;
    private AppCompatImageView mRoomImg;

    private Authentication auth;
    private LiveChatCallbackListener liveChatCallbackListener;
    private UserPresenceCallBackListener userPresenceCallBackListener;
    ChatSessionFragment chatSessionFragment;

    Animation slideAnimation;
    RelativeLayout mNewMsgLayoutContainer, mChatBtnContainer;
    TextView mIncomingNewMsg;

    static boolean isDetailChatOpen = false;

    private boolean isInBckGrnd = false;

    public ChatRoomFragment() {
    }

    public static ChatRoomFragment newInstance(int roomNum) {
        ChatRoomFragment fragment = new ChatRoomFragment();
        Bundle args = new Bundle();
        args.putInt("roomPos", roomNum);
        fragment.setArguments(args);
        return fragment;
    }

    public void findViews() {
        if (view == null)
            return;

        mRoomName = view.findViewById(R.id.room_name);
        mRoomDate = view.findViewById(R.id.room_date);
        mRoomLocation = view.findViewById(R.id.room_location);
        mRoomDescription = view.findViewById(R.id.room_description);
        mRoomImg = view.findViewById(R.id.room_desc_img);
        mChatTogetherBtn = view.findViewById(R.id.chat_btn);
        mUserCount = view.findViewById(R.id.unread_msgs);
        mNewMsgLayoutContainer = view.findViewById(R.id.new_msg_text_container);
        mIncomingNewMsg = view.findViewById(R.id.new_msg_text);
        mChatBtnContainer = view.findViewById(R.id.chat_btn_container);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAGGOW", "onCreate: ");
        roomPos = getArguments().getInt("roomPos", 0);
        chatSessionFragment = new ChatSessionFragment();
        chatSessionFragment.setbackPress(this);
        chatSessionFragment.setMessageList(this);
        ((HomepageActivity) getActivity()).mRoomPagerAdapter.populateRooms();

    }


    public void initViews() {
        if (roomPos < ((HomepageActivity) getActivity()).mRoomPagerAdapter.getCount()) {
            ChatRoom room = ((HomepageActivity) getActivity()).mRoomPagerAdapter.listOfRooms.get(roomPos);
            mRoomName.setText(room.getRoomName());
            mRoomDescription.setText(room.getRoomDesc());
            mRoomLocation.setText(room.getRoomLocation());
            mRoomDate.setText(room.getRoomDate());

            switch (roomPos) {
                case 0:
                    mRoomImg.setBackgroundResource(R.drawable.coucou);
                    break;
                case 1:
                    mRoomImg.setBackgroundResource(R.drawable.innerrealm);
                    break;
                case 2:
                    mRoomImg.setBackgroundResource(R.drawable.missmodular);
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.chat_room_item, container, false);
        Log.d("TAGGOW", "onCreateView: ");

        findViews();

        //to initialize the values
        initViews();

        mChatTogetherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //add chat fragment
                if (getActivity().getClass() == HomepageActivity.class) {
                    //pass in the user details here

                    if (slideAnimation != null) {
                        mNewMsgLayoutContainer.clearAnimation();
                        slideAnimation.reset();
                        slideAnimation = null;
                    }

                    mChatBtnContainer.setBackgroundColor(getResources().getColor(R.color.sixty_black));

                    FragmentTransaction transaction = ((HomepageActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(((((HomepageActivity) getActivity()).chatSessionFragContainer.getId())), chatSessionFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    isInBckGrnd = true;
                    isDetailChatOpen = true;
                }

            }
        });


        ((HomepageActivity) Objects.requireNonNull(getActivity())).startLiveChat.joinSession(getRoomName(ChatRoomPagerAdapter.getCurrentItem()), createUserInfo(), new CallbackListener() {
            @Override
            public void onSuccess(String msg, Object... obj) {
                //call subscribe here
            }

            @Override
            public void onError(String msg, Object... obj) {

            }
        });

        liveChatCallbackListener = new LiveChatCallbackListener() {
            @Override
            public void receivedChatMessage(String roomName, MessageInfo message) {
                if(isDetailChatOpen) {
                    messageUpdateList(message);
                }else {
                    incomingMsgSlideAnimation(message.getChatMessage());
                    updateChatMessagList(message);
                }
            }

            @Override
            public void getPreviousMessages(String roomName, List<MessageInfo> messageInfo) {
                String userEnteredMessage = ((HomepageActivity) Objects.requireNonNull(getActivity())).startLiveChat.getUserName() + " entered the room";
                MessageInfo addUserInmessageInfo = new MessageInfo();
                addUserInmessageInfo.setUserId(((HomepageActivity) Objects.requireNonNull(getActivity())).startLiveChat.getUserId());
                addUserInmessageInfo.setUserName(((HomepageActivity) Objects.requireNonNull(getActivity())).startLiveChat.getUserName());
                addUserInmessageInfo.setChatMessage(userEnteredMessage);
                addUserInmessageInfo.setSystemMessage(true);
                messageInfo.add(addUserInmessageInfo);
                previousMessageList(messageInfo);
            }

        };

        userPresenceCallBackListener = new UserPresenceCallBackListener() {
            @Override
            public void userEntered(String roomName, UserInfo participant) {
                String userEnteredMessage = participant.getName() + " entered the room";
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setUserId(participant.getUserId());
                messageInfo.setUserName(participant.getName());
                messageInfo.setChatMessage(userEnteredMessage);
                messageInfo.setSystemMessage(true);
                if(isDetailChatOpen) {
                    messageUpdateList(messageInfo);
                }else{
                    incomingMsgSlideAnimation(userEnteredMessage);
                    updateChatMessagList(messageInfo);
                }
            }

            @Override
            public void userExited(String roomName, UserInfo participant) {
                String userExitMessage = participant.getName() + " exited the room";
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setUserId(participant.getUserId());
                messageInfo.setUserName(participant.getName());
                messageInfo.setChatMessage(userExitMessage);
                messageInfo.setSystemMessage(true);
                if(isDetailChatOpen) {
                    messageUpdateList(messageInfo);
                }else{
                    incomingMsgSlideAnimation(userExitMessage);
                    updateChatMessagList(messageInfo);
                }
            }

            @Override
            public void userDataUpdated(String roomName, UserInfo participant) {
            }

            @Override
            public void getNumberOfUsersLiveNow(String roomName, int userCount) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mUserCount.setText(String.valueOf(userCount));
                    }
                });

            }

        };

        ChatRoom room = ((HomepageActivity) getActivity()).mRoomPagerAdapter.listOfRooms.get(roomPos);

        ((HomepageActivity) Objects.requireNonNull(getActivity())).startLiveChat.subscribe(room.getRoomName(), liveChatCallbackListener, userPresenceCallBackListener, new CallbackListener() {
            @Override
            public void onSuccess(String msg, Object... obj) {
            }

            @Override
            public void onError(String msg, Object... obj) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("chatRoom", "onResume: " + roomPos);

    }

    public void unSubscribeCall() {
        Timber.d("startLiveChat value", ((HomepageActivity) Objects.requireNonNull(getActivity())).startLiveChat);
    }

    @Override
    public void backButtonClick() {
        Timber.d("startLiveChat value", ((HomepageActivity) Objects.requireNonNull(getActivity())).startLiveChat);
        ((HomepageActivity) Objects.requireNonNull(getActivity())).startLiveChat.chatEventListener.clearAllMessages();
    }

    private UserInfo createUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setAppSpecificUserID(UUID.randomUUID().toString());
        userInfo.setName(((HomepageActivity) Objects.requireNonNull(getActivity())).startLiveChat.getUserName());
        return userInfo;
    }

    private String getRoomName(int position) {
        String[] nameArray = getResources().getStringArray(R.array.roomNames);
        switch (position) {
            case 0:
                return nameArray[0];
            case 1:
                return nameArray[1];
            case 2:
                return nameArray[2];
        }

        return "NONE";
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TAGGOW", "onStart: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("TAGGOW", "onDestroyView: ");
        ChatRoom room = ((HomepageActivity) getActivity()).mRoomPagerAdapter.listOfRooms.get(roomPos);
        CallbackListener cbl = new CallbackListener() {
            @Override
            public void onSuccess(String s, Object... objects) {
                Log.d("TAGGOW", "Leave Session: Success");

            }

            @Override
            public void onError(String s, Object... objects) {

                Log.d("TAGGOW", "Leave Session: Error");

            }
        };
        chatSessionFragment.mChatMessageList.clear();
        ((HomepageActivity) Objects.requireNonNull(getActivity())).startLiveChat.leaveSession(room.getRoomName(), cbl);
        ((HomepageActivity) Objects.requireNonNull(getActivity())).startLiveChat
                .unSubscribe(room.getRoomName(), liveChatCallbackListener, userPresenceCallBackListener, new CallbackListener() {
                    @Override
                    public void onSuccess(String msg, Object... obj) {

                    }

                    @Override
                    public void onError(String msg, Object... obj) {

                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TAGGOW", "onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAGGOW", "onDestroy: ");
    }

    @Override
    public void messageUpdateList(MessageInfo messageInfo) {
        chatSessionFragment.updateMessageList(messageInfo);
    }

    @Override
    public void previousMessageList(List<MessageInfo> previousMessageInfo) {
        chatSessionFragment.updatePreviousMessageList(previousMessageInfo);
    }

    @Override
    public void updateChatMessagList(MessageInfo messageInfoList) {
        chatSessionFragment.updateChatMessageList(messageInfoList);
    }

    public void incomingMsgSlideAnimation(String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isDetailChatOpen)
                    return;

                mChatBtnContainer.setBackgroundColor(getResources().getColor(R.color.active_session_green));
                slideAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_animation);
                mNewMsgLayoutContainer.startAnimation(slideAnimation);

                slideAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mNewMsgLayoutContainer.setVisibility(View.VISIBLE);
                        mIncomingNewMsg.setVisibility(View.VISIBLE);
                        if (msg != null && !msg.isEmpty())
                            mIncomingNewMsg.setText(msg);

                        mChatBtnContainer.bringToFront();
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mNewMsgLayoutContainer.clearAnimation();
                        if (slideAnimation != null) {
                            slideAnimation.reset();
                            slideAnimation.cancel();
                        }
                        mIncomingNewMsg.setVisibility(View.GONE);
                        mNewMsgLayoutContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

        if (slideAnimation != null) {
            mNewMsgLayoutContainer.clearAnimation();
            slideAnimation.reset();
            slideAnimation = null;
        }
    }
}
