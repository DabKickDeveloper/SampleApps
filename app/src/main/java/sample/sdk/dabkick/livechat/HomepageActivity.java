package sample.sdk.dabkick.livechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.util.ArrayList;

import sample.sdk.dabkick.livechat.adapter.ChatRoomPagerAdapter;
import sample.sdk.dabkick.livechat.model.ChatRoom;

public class HomepageActivity extends AppCompatActivity {

    CustomViewPager mChatRoomPager;
    public ChatRoomPagerAdapter mRoomPagerAdapter;
    ArrayList<ChatRoom> mChatRoomList;
    FrameLayout chatSessionFragContainer;


    public void findViews() {
        mChatRoomPager = findViewById(R.id.chat_room_view_pager);
        chatSessionFragContainer = findViewById(R.id.chat_session_fragment_container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        findViews();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mChatRoomList = new ArrayList<ChatRoom>();
        mRoomPagerAdapter = new ChatRoomPagerAdapter(getSupportFragmentManager(), HomepageActivity.this);
        mChatRoomPager.setOffscreenPageLimit(0);
        mChatRoomPager.setAdapter(mRoomPagerAdapter);

        mChatRoomPager.setOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                ChatRoomPagerAdapter.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mDKLiveChat.endLiveChat();
    }
}

