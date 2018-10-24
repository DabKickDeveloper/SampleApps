package sample.sdk.dabkick.sampleappdkvp;

public class AppParticipant {

    private String userId;
    private String userName;
    private String userPic;

    public AppParticipant(String userId, String userName, String userPic) {
        this.userId = userId;
        this.userName = userName;
        this.userPic = userPic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setChatMsgUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String chatMsgType) {
        this.userPic = userPic;
    }
}
