package sample.sdk.dabkick.livechat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dabkick.engine.Public.MessageInfo;

import java.util.List;

public class ChatMsgAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MessageInfo> mMessageList;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int DEFAULT = 0;


    public ChatMsgAdapter(Context mContext, List<MessageInfo> mMessageList) {
        this.mContext = mContext;
        this.mMessageList = mMessageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_msg_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_msg_received, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageInfo message = (MessageInfo) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        if(mMessageList == null)
            return  0;
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        MessageInfo message = (MessageInfo) mMessageList.get(position);

        //sometimes seems to be crashing as null
        if (message == null) {
            return DEFAULT;
        }

        if (message.getChatMsgType() == ChatMsg.MESSAGE_TYPE.SENT) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, nameText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.sent_msg);
            nameText = (TextView) itemView.findViewById(R.id.sender_msg_name);
        }

        void bind(MessageInfo message) {
            messageText.setText(message.getChatMessage());
            if(message.isSystemMessage()){
                nameText.setText("System");
            }else {
                nameText.setText(message.getUserName());
            }
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.recieved_msg);
            nameText = (TextView) itemView.findViewById(R.id.recieved_msg_name);
        }

        void bind(MessageInfo message) {
            messageText.setText(message.getChatMessage());
            if(message.isSystemMessage()){
                nameText.setText("System");
            }else {
                nameText.setText(message.getUserName());
            }
        }
    }

    public void updateMessageList(List<MessageInfo> msgList){
        this.mMessageList = msgList;
//        notifyDataSetChanged();
    }

}
