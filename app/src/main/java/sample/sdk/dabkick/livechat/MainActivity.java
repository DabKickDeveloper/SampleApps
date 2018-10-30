package sample.sdk.dabkick.livechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dabkick.engine.Public.CallbackListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mInviteBtn, endSession, mChatTogether, mNameDoneBtn;
    EditText chatEditText, mNameEditText;
    ImageButton sendChatMessage;
    RecyclerView chatListView, mAvatarListView;
    LinearLayout mAvatarLayout, mChatBoxContainer, mNameTextLayout;
    RecyclerView.LayoutManager mLayoutManager;

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
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.send_chat_msg:

                break;

            case R.id.chat_together_btn:
                Toast.makeText(MainActivity.this, "This feature will work on implemnting the engine library", Toast.LENGTH_LONG).show();
                break;

            case R.id.name_done_btn:

                break;


        }
    }
}
