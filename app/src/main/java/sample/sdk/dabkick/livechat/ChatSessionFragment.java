package sample.sdk.dabkick.livechat;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dabkick.engine.DKServer.Retrofit.Prefs;
import com.dabkick.engine.Public.CallbackListener;
import com.dabkick.engine.Public.InitializeLiveChat;
import com.dabkick.engine.Public.MessageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatSessionFragment extends Fragment implements View.OnClickListener{

    ImageButton sendBtn;
    AppCompatImageView mBackBtn;
    View view;
    AppCompatEditText chatEditText;
    private InitializeLiveChat engine;
    RecyclerView chatListView;
    ChatMsgAdapter mChatMessageAdapter;
    List<MessageInfo> mChatMessageList = new ArrayList<MessageInfo>();
    ChatBackPress chatBackPress;
    TextView mUserName;
//    String currentUserAppSpecificID;
    ReceivedMessageUpdate updateMessageList;
    RelativeLayout mParentLayout;

    public ChatSessionFragment() {
    }


    public void findViews() {
        chatEditText = view.findViewById(R.id.chatEditText);
        sendBtn = view.findViewById(R.id.send_chat_msg);
        sendBtn.setOnClickListener(this);
        chatListView = view.findViewById(R.id.listview_chat);
        mUserName = view.findViewById(R.id.user_name);
        setUpLayoutManager();
        setUpChatAdapter();
        mBackBtn = view.findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(this);
        mParentLayout = view.findViewById(R.id.parent_layout);
        mParentLayout.setOnClickListener(this);
    }

    public void setUpChatAdapter() {
        if (((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat.chatEventListener != null) {
            mChatMessageList.addAll(((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat.chatEventListener.getChatMessages(""));
            mChatMessageAdapter = new ChatMsgAdapter(this.getActivity(), mChatMessageList);
            if (chatListView != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        chatListView.setAdapter(mChatMessageAdapter);
                    }
                });
            }

        } else {
            mChatMessageAdapter = new ChatMsgAdapter(this.getActivity(), new ArrayList<MessageInfo>());
            if (chatListView != null)
                chatListView.setAdapter(mChatMessageAdapter);
        }
        chatListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                chatListView.scrollToPosition(chatListView.getAdapter().getItemCount() - 1);
            }
        }, 100);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat_session, container, false);

        findViews();

        //Setting User Name
        mUserName.setText(Prefs.getName());

        //added to scroll the list to the last item when edit text looses focus
        chatEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                chatListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (chatListView != null && chatListView.getAdapter() != null && !isFocused)
                            chatListView.scrollToPosition(chatListView.getAdapter().getItemCount() - 1);
                    }
                }, 100);

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_chat_msg:
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setChatMessage(chatEditText.getText().toString());
                messageInfo.setUserId(((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat.getUserId());
                messageInfo.setUserName(((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat.getUserName());
//                messageInfo.setAppSpecificUserID(currentUserAppSpecificID);
                Utils.hideKeyboard(getActivity());
                ((HomepageActivity) Objects.requireNonNull(getActivity())).initializeLiveChat.chatEventListener.sendMessage("", messageInfo, new CallbackListener() {
                    @Override
                    public void onSuccess(String msg, Object... obj) {
                        chatEditText.setText("");
                    }

                    @Override
                    public void onError(String msg, Object... obj) {

                    }
                });
                break;

            case R.id.back_btn:
                if (getActivity() != null) {
                    Utils.hideKeyboard(getActivity());
                    getActivity().onBackPressed();
                    mChatMessageList.clear();
                    chatBackPress.backButtonClick();
                }
                break;

            case R.id.parent_layout:
                Utils.hideKeyboard(getActivity());
                break;
        }
    }

    private void setUpLayoutManager() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        chatListView.setLayoutManager(mLayoutManager);
        chatListView.setItemAnimator(new DefaultItemAnimator());
    }

    /*public void setEngine(InitializeLiveChat initializeLiveChat){
        this.initializeLiveChat = initializeLiveChat;
        setUpChatAdapter();
    }*/

    void setbackPress(ChatBackPress chatBackPressed){
        chatBackPress = chatBackPressed;
    }

    void setMessageList(ReceivedMessageUpdate messageUpdate){
        updateMessageList = messageUpdate;
    }


    public void updateMessageList(MessageInfo messageInfo) {
        mChatMessageList.add(messageInfo);
        if(mChatMessageAdapter == null)
            mChatMessageAdapter = new ChatMsgAdapter(this.getActivity(), mChatMessageList);
        mChatMessageAdapter.updateMessageList(mChatMessageList);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mChatMessageAdapter.notifyDataSetChanged();
                if(chatListView != null) {
                    chatListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            chatListView.scrollToPosition(chatListView.getAdapter().getItemCount() - 1);
                        }
                    }, 100);
                }
            }
        });
    }

    public interface ChatBackPress{

        void backButtonClick();
    }

    public interface ReceivedMessageUpdate{
        void messageUpdateList(MessageInfo messageInfo);
    }


}
