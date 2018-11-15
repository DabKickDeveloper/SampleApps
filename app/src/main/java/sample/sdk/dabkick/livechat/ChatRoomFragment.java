package sample.sdk.dabkick.livechat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dabkick.engine.Public.Authentication;
import com.dabkick.engine.Public.CallbackListener;
import com.dabkick.engine.Public.EnginePresenceCallbackListener;
import com.dabkick.engine.Public.InitializeLiveChat;
import com.dabkick.engine.Public.LiveChatCallbackListener;
import com.dabkick.engine.Public.MessageInfo;
import com.dabkick.engine.Public.UserInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import timber.log.Timber;

public class ChatRoomFragment extends Fragment implements ChatSessionFragment.ChatBackPress, ChatSessionFragment.ReceivedMessageUpdate {

    View view;
    private int roomPos;

    private TextView mRoomName, mUserCount, mRoomLocation, mRoomDate, mRoomDescription;
    private ImageButton mChatTogetherBtn;

    private Authentication auth;
    private LiveChatCallbackListener liveChatCallbackListener;
    private EnginePresenceCallbackListener enginePresenceCallbackListener;
    private InitializeLiveChat engine;
    ChatSessionFragment chatSessionFragment;

    // class variable
    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

    final java.util.Random rand = new java.util.Random();

    // consider using a Map<String,Boolean> to say whether the identifier is being used or not
    final Set<String> identifiers = new HashSet<String>();

    public String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(5) + 5;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
            if (identifiers.contains(builder.toString())) {
                builder = new StringBuilder();
            }
        }
        return builder.toString();
    }


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

        mChatTogetherBtn = view.findViewById(R.id.chat_btn);
        mUserCount = view.findViewById(R.id.unread_msgs);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAGGOW", "onCreate: ");
        roomPos = getArguments().getInt("roomPos", 0);
        chatSessionFragment = new ChatSessionFragment();
        chatSessionFragment.setbackPress(this);
        chatSessionFragment.setMessageList(this);
        ((HomepageActivity)getActivity()).mRoomPagerAdapter.populateRooms();

    }


     public void initViews(){
         if(roomPos < ((HomepageActivity)getActivity()).mRoomPagerAdapter.getCount()){
             ChatRoom room = ((HomepageActivity)getActivity()).mRoomPagerAdapter.listOfRooms.get(roomPos);
             mRoomName.setText(room.getRoomName());
             mRoomDescription.setText(room.getRoomDesc());
             mRoomLocation.setText(room.getRoomLocation());
             mRoomDate.setText(room.getRoomDate());
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

                    FragmentTransaction transaction = ((HomepageActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(((((HomepageActivity) getActivity()).chatSessionFragContainer.getId())), chatSessionFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

            }
        });


        ((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat.joinSession(getRoomName(ChatRoomPagerAdapter.getCurrentItem()), createUserInfo(), new CallbackListener() {
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
                messageUpdateList(message);

            }

            @Override
            public void getPreviousMessages(String roomName, List<MessageInfo> messageInfo) {
            }

        };

        enginePresenceCallbackListener = new EnginePresenceCallbackListener() {
            @Override
            public void userEntered(String roomName, UserInfo participant) {
                String userEnteredMessage = participant.getName() + " entered the room";
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setUserId(participant.getUserId());
                messageInfo.setUserName(participant.getName());
                messageInfo.setChatMessage(userEnteredMessage);
                messageInfo.setSystemMessage(true);
                messageUpdateList(messageInfo);
            }

            @Override
            public void userExit(String roomName, UserInfo participant) {
                String userExitMessage = participant.getName() + " exited the room";
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setUserId(participant.getUserId());
                messageInfo.setUserName(participant.getName());
                messageInfo.setChatMessage(userExitMessage);
                messageInfo.setSystemMessage(true);
                messageUpdateList(messageInfo);
            }

            @Override
            public void userDataChanged(String roomName, UserInfo participant) {
            }

            @Override
            public void userCount(String roomName, int userCount) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mUserCount.setText(String.valueOf(userCount));
                    }
                });

            }

        };

        ChatRoom room = ((HomepageActivity)getActivity()).mRoomPagerAdapter.listOfRooms.get(roomPos);

        ((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat.subscribe(room.getRoomName(), liveChatCallbackListener, enginePresenceCallbackListener, new CallbackListener() {
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
        Timber.d("initializeLiveChat value", ((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat);
    }

    @Override
    public void backButtonClick() {
        Timber.d("initializeLiveChat value", ((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat);
        ((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat.chatEventListener.clearAllMessages();
    }

    private UserInfo createUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setAppSpecificUserID(UUID.randomUUID().toString());
        userInfo.setName(randomIdentifier());
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
        ChatRoom room = ((HomepageActivity)getActivity()).mRoomPagerAdapter.listOfRooms.get(roomPos);
        ((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat.endSession();
        ((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat
                .unSubscribe(room.getRoomName(), liveChatCallbackListener, enginePresenceCallbackListener, new CallbackListener() {
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
}
