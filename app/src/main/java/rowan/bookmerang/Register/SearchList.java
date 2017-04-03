package rowan.bookmerang.Register;

/**
 * Created by 15U560 on 2016-11-14.
 */

public class SearchList {
    private String bookName, author, oroginalPrice, pubdate, publisher,bookImage;

    public SearchList(String bookName, String bookImage, String author, String oroginalPrice, String publisher,String pubdate) {
        this.bookName = bookName;
        this.author = author;
        this.oroginalPrice = oroginalPrice;
        this.pubdate = pubdate;
        this.publisher = publisher;
        this.bookImage = bookImage;
    }

    public String getBookName() {return bookName;}
    public String getAuthor() {return author;}
    public String getOroginalPrice() {return oroginalPrice;}
    public String getPubdate() {return pubdate;}
    public String getPublisher() {return publisher;}
    public String getBookImage(){return bookImage;}
}
