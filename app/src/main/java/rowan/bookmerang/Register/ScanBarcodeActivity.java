package rowan.bookmerang.Register;

import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import rowan.bookmerang.Main.MainActivity;
import rowan.bookmerang.R;

/**
 * Created by 15U560 on 2016-10-25.
 */

public class ScanBarcodeActivity extends CaptureActivity {
    static MainActivity activity = MainActivity.activity;
    static ScanBarcodeActivity activity2;
    private static String CLIENT_ID = "jXe99B1cbTtNv7fupLQW";
    private static String CLIENT_PASSWORD = "ZZCGI765rI";
    int count = 0;
    String bookName, bookPrice, bookPubDate, authorName, publisherName, bookImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);
        count++;
        new IntentIntegrator(this)
                .setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
                .setPrompt("바코드를 촬영해주세요.")
                .setCameraId(0)
                .setOrientationLocked(true)
                .initiateScan();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // QR코드/바코드를 스캔한 결과 값을 가져옵니다.
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        // 결과값 출력
        final String isbn_number = result.getContents();
        if (result.getContents() != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    searchMethod(isbn_number);
                }
            }).start();
        }
    }

    public void searchMethod(String isbn_number) {
        try {
            String isbn = URLEncoder.encode(isbn_number, "UTF8");
            String sendQuery = "https://openapi.naver.com/v1/search/book_adv.xml?query=" + "" + "&d_isbn=" + isbn;
            URL url = new URL(sendQuery);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setRequestProperty("X-Naver-Client-Id", CLIENT_ID); //발급받은ID
            conn.setRequestProperty("X-Naver-Client-Secret", CLIENT_PASSWORD);//발급받은PW
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(conn.getInputStream(), null);
            String tag;

            xpp.next();
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();    //테그 이름 얻어오기
                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else if (tag.equals("title")) {
                            xpp.next();
                            if (xpp.getText().contains("Naver Open API")) {
                                break;
                            } else {
                                bookName = xpp.getText().replace("<b>", "").replace("</b>", "");
                            }
                        } else if (tag.equals("image")) {
                            xpp.next();
                            bookImage = xpp.getText();
                        }else if (tag.equals("author")) {
                            xpp.next();
                            authorName = xpp.getText();
                        } else if (tag.equals("price")) {
                            xpp.next();
                            bookPrice = xpp.getText();
                        } else if (tag.equals("publisher")) { // getmapx value
                            xpp.next();
                            publisherName = xpp.getText();
                        } else if (tag.equals("pubdate")) { // getmapy valye
                            xpp.next();
                            bookPubDate = xpp.getText();

                            Intent intent = new Intent(ScanBarcodeActivity.this, BarcodeRegisterActivity.class);
                            intent.putExtra("bookName", bookName);
                            intent.putExtra("authorName", authorName);
                            intent.putExtra("bookImage", bookImage);
                            intent.putExtra("bookPrice", bookPrice);
                            intent.putExtra("publisherName", publisherName);
                            intent.putExtra("bookPubDate", bookPubDate);
                            startActivity(intent);
                            if(activity!=null) {
                                MainActivity.activity.finish();
                            }
                            finish();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();    //테그 이름 얻어오기
                        if (tag.equals("item"))
                            break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {
        /*Intent intent = new Intent(ScanBarcodeActivity.this, MainActivity.class);
        startActivity(intent);*/
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*icount++;
        if(count==2) {
            if (ScanBarcodeActivity.this!=null){
                finish();
            }
        }*/
    }
}