package rowan.bookmerang.Chatting;

/**
 * Created by 15U560 on 2016-12-01.
 */

public class ChatData {
    private String userName;
    private String userName2;
    private String message;

    public ChatData() {

    }

    public ChatData(String userName, String userName2, String message) {
        this.userName = userName;
        this.userName2 = userName2;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getUserName2() {return userName2;}
    public void setUserName2(String userName2) {this.userName2 = userName2;}
}
