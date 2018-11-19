package sample.sdk.dabkick.livechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.dabkick.engine.Public.Authentication;
import com.dabkick.engine.Public.CallbackListener;
import com.dabkick.engine.Public.StartLiveChat;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    CustomViewPager mChatRoomPager;
    public ChatRoomPagerAdapter mRoomPagerAdapter;
    ArrayList<ChatRoom> mChatRoomList;
    public StartLiveChat startLiveChat;
    FrameLayout chatSessionFragContainer;
    Authentication auth;

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

        //Initialize Engine
         startLiveChat = new StartLiveChat(HomepageActivity.this, auth, new CallbackListener() {
            @Override
            public void onSuccess(String msg, Object... obj) {

            }

            @Override
            public void onError(String msg, Object... obj) {
            }
        });

        mRoomPagerAdapter = new ChatRoomPagerAdapter(getSupportFragmentManager(), HomepageActivity.this);
        mChatRoomPager.setOffscreenPageLimit(0);
        mChatRoomPager.setAdapter(mRoomPagerAdapter);

        if (!startLiveChat.checkIfUserNameIsSet())
            createNameAlertDialog();

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
    protected void onDestroy() {
        super.onDestroy();
        //startLiveChat.endLiveChat();
    }


    private void createNameAlertDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.user_name_layout, null);

        final AlertDialog alertD = new AlertDialog.Builder(this).create();

        EditText userInput = promptView.findViewById(R.id.name_edit_text);

        Button skip = promptView.findViewById(R.id.skip);

        Button update = promptView.findViewById(R.id.update);

        skip.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertD.dismiss();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = userInput.getText().toString().trim();
                if (!name.isEmpty()) {
                    startLiveChat.updateName(name, new CallbackListener() {
                        @Override
                        public void onSuccess(String msg, Object... obj) {
                            alertD.dismiss();
                        }

                        @Override
                        public void onError(String msg, Object... obj) {
                            Snackbar.make(promptView, msg, Snackbar.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Snackbar.make(promptView, "Name should not be empty", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        alertD.setView(promptView);
        alertD.setCancelable(false);
        alertD.show();
    }

}