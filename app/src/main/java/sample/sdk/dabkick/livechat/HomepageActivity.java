package sample.sdk.dabkick.livechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dabkick.engine.Public.Authentication;
import com.dabkick.engine.Public.CallbackListener;
import com.dabkick.engine.Public.DKLiveChat;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    CustomViewPager mChatRoomPager;
    public ChatRoomPagerAdapter mRoomPagerAdapter;
    ArrayList<ChatRoom> mChatRoomList;
    public DKLiveChat mDKLiveChat;
    FrameLayout chatSessionFragContainer;
    Authentication auth;
    public static boolean isFirebaseMessagesTaken = false;
    public static String currentRoomName;


    public void findViews() {
        mChatRoomPager = findViewById(R.id.chat_room_view_pager);
        chatSessionFragContainer = findViewById(R.id.chat_session_fragment_container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        mChatRoomList = new ArrayList<ChatRoom>();
        for (int i = 1; i <= 5; i++) {
            ChatRoom chatRoom = new ChatRoom("Room " + i);
            mChatRoomList.add(chatRoom);
        }
            auth = new Authentication("DKe1ac069ddf1011e7a1d8062", "f84bd8d546b10cff2b601093e47f61");

        if(Utils.isWifiConnected(this)) {
//            Initialize Engine
            mDKLiveChat = new DKLiveChat(HomepageActivity.this, auth, new CallbackListener() {
                @Override
                public void onSuccess(String msg, Object... obj) {
                    //handle success
                }

                @Override
                public void onError(String msg, Object... obj) {
                    //handle error
                }
            });
        }else{
            Toast.makeText(this, "Please check the internet and reload the app", Toast.LENGTH_SHORT).show();
            this.finish();
            return;
        }

            mRoomPagerAdapter = new ChatRoomPagerAdapter(getSupportFragmentManager(), HomepageActivity.this);
            mChatRoomPager.setOffscreenPageLimit(0);
            mChatRoomPager.setAdapter(mRoomPagerAdapter);

        mChatRoomPager.setOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

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
        mDKLiveChat.endLiveChat();
    }
}
