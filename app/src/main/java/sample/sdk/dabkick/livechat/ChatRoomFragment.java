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
import sample.sdk.dabkick.livechat.model.ChatRoom;

public class ChatRoomFragment extends Fragment implements ChatSessionFragment.ChatBackPress {

    View view;
    private int roomPos;

    private TextView mRoomName, mUserCount, mRoomLocation, mRoomDate, mRoomDescription;
    private ImageButton mChatTogetherBtn;
    private AppCompatImageView mRoomImg;

    ChatSessionFragment chatSessionFragment;

    Animation slideAnimation, fadeInOutAnim;
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

                    if (fadeInOutAnim != null) {
                        mChatTogetherBtn.clearAnimation();
                        fadeInOutAnim.reset();
                        fadeInOutAnim = null;
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


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("chatRoom", "onResume: " + roomPos);
        if (fadeInOutAnim != null) {
            mChatTogetherBtn.startAnimation(fadeInOutAnim);
        }

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void incomingMsgSlideAnimation(String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isDetailChatOpen)
                    return;

                fadeInOutAnim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_in_out);
                mChatTogetherBtn.startAnimation(fadeInOutAnim);

//                mChatBtnContainer.setBackgroundColor(getResources().getColor(R.color.active_session_green));
                slideAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fade_out);
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

    @Override
    public void backButtonClick() {

    }
}
