package sample.sdk.dabkick.livechat;

public class ChatMsg {

    private String chatMsg;
    private String chatMsgUserName;
    private int chatMsgType;

    public ChatMsg(String chatMsg, String chatMsgUserName, int chatMsgType) {
        this.chatMsg = chatMsg;
        this.chatMsgUserName = chatMsgUserName;
        this.chatMsgType = chatMsgType;
    }

    public String getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(String chatMsg) {
        this.chatMsg = chatMsg;
    }

    public String getChatMsgUserName() {
        return chatMsgUserName;
    }

    public void setChatMsgUserName(String chatMsgUserName) {
        this.chatMsgUserName = chatMsgUserName;
    }

    public int getChatMsgType() {
        return chatMsgType;
    }

    public void setChatMsgType(int chatMsgType) {
        this.chatMsgType = chatMsgType;
    }

    public static final class MESSAGE_TYPE{
        public final static int SENT = 0;
        public final static int RECEIVED = 1;
    }
}
