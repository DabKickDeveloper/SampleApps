package sample.sdk.dabkick.sampleappdkvp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import sample.sdk.dabkick.sampleappdkvp.ChatMsg;

public class ChatMsgAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ChatMsg> mMessageList;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;


    public ChatMsgAdapter(Context mContext, List<ChatMsg> mMessageList) {
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
        ChatMsg message = (ChatMsg) mMessageList.get(position);

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
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMsg message = (ChatMsg) mMessageList.get(position);

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

        void bind(ChatMsg message) {
            messageText.setText(message.getChatMsg());
            nameText.setText(message.getChatMsgUserName());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.recieved_msg);
            nameText = (TextView) itemView.findViewById(R.id.recieved_msg_name);
        }

        void bind(ChatMsg message) {
            messageText.setText(message.getChatMsg());
            nameText.setText(message.getChatMsgUserName());
        }
    }

    public void updateMessageList(List<ChatMsg> msgList){
        this.mMessageList = msgList;
        notifyDataSetChanged();
    }

}
