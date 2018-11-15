package sample.sdk.dabkick.livechat;

public class ChatRoom {

    private String mRoomName;
    private String mRoomDesc;
    private String mRoomLocation, mRoomDate;


    public String getRoomDesc() {
        return mRoomDesc;
    }

    public void setRoomDesc(String mRoomDesc) {
        this.mRoomDesc = mRoomDesc;
    }

    public ChatRoom(){}

    public ChatRoom(String roomName) {
        this.mRoomName = roomName;
    }

    public ChatRoom(String roomName, String roomDesc, String roomLocation, String roomDate) {
        this.mRoomName = roomName;
        this.mRoomDesc = roomDesc;
        this.mRoomLocation = roomLocation;
        this.mRoomDate = roomDate;
    }

    public String getRoomName() {
        return mRoomName;
    }

    public void setRoomName(String mRoomName) {
        this.mRoomName = mRoomName;
    }

    public String getRoomLocation() {
        return mRoomLocation;
    }

    public void setRoomLocation(String mRoomLocation) {
        this.mRoomLocation = mRoomLocation;
    }

    public String getRoomDate() {
        return mRoomDate;
    }

    public void setRoomDate(String roomDate) {
        this.mRoomDate = roomDate;
    }
}
