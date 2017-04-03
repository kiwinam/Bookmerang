package rowan.bookmerang.Main;

/**
 * Created by CHEONMYUNG on 2016-11-07.
 */

public class Item {
    public String bookCode,imageUrl,title, author,originalPrice,pubdate,publisher,state,salePrice,nickName,salesNumber,university,kindOfSell,sellstate,email_book;
    public String getEmail_book() {return this.email_book; }
    public String getBookCode() {return this.bookCode; }
    public String getImageUrl() {
        return this.imageUrl;
     }
    public String getTitle() {
        return this.title;
    }
    public String getAuthor() { return  this.author;}
    public String getOriginalPrice() { return  this.originalPrice; }
    public String getPubdate(){ return this.pubdate; }
    public String getPublisher() { return this.publisher; }
    public String getState() { return this.state; }
    public String getSalePrice() { return  this.salePrice; }
    public String getNickName() { return this.nickName; }
    public String getSalesNumber() { return this.salesNumber; }
    public String getUniversity() { return this.university; }
    public String getKindOfSell() { return this.kindOfSell; }
    public String getSellstate() { return this.sellstate; }

    public Item(String email_book,String bookCode,String title, String imageUrl, String author, String originalPrice, String pubdate, String publisher, String state, String salePrice, String nickName, String salesNumber, String university,String kindOfSell,String sellstate) {

        this.email_book = email_book;
        this.bookCode = bookCode;
        this.title = title;
        this.imageUrl = imageUrl;
        this.author = author;
        this.originalPrice = originalPrice;
        this.pubdate = pubdate;
        this.publisher = publisher;
        this.state = state;
        this.salePrice = salePrice;
        this.nickName = nickName;
        this.salesNumber = salesNumber;
        this.university = university;
        this.kindOfSell = kindOfSell;
        this.sellstate = sellstate;
    }
}