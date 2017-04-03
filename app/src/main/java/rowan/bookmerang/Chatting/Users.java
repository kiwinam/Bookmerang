package rowan.bookmerang.Chatting;

/**
 * Created by 15U560 on 2016-12-01.
 */

public class Users {
    private String myTel;
    private String youTel;
    private String bookName;
    public Users(String myTel, String youTel, String bookName) {
        this.myTel = myTel;
        this.youTel = youTel;
        this.bookName = bookName;
    }
    public String getMyTel() {return myTel;}
    public String getYouTel() {return youTel;}
    public String getBookName() {return bookName;}
}
