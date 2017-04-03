package rowan.bookmerang.Register;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Vector;

import rowan.bookmerang.Global.BaseActivity;
import rowan.bookmerang.Main.MainActivity;
import rowan.bookmerang.R;
import rowan.bookmerang.Global.TaskMethod;

import static rowan.bookmerang.R.id.faceToFace2;

/**
 * Created by 15U560 on 2016-10-25.
 */

public class NormalRegisterActivity extends BaseActivity {
    public static MainActivity activity = (MainActivity)MainActivity.activity;
    public static NormalRegisterActivity activity2;
    Bitmap bitMap;
    JSONObject jsonObject;
    public Dialog searchAddrDialog;
    private static String CLIENT_ID = "jXe99B1cbTtNv7fupLQW";
    private static String CLIENT_PASSWORD = "ZZCGI765rI";
    Button book_register;
    String email,bookName, bookPrice, bookPubDate, authorName, publisherName, salesPrice ,bookImage,salesMan,salesPhone,salesUniversity,
            bookState ="상태", kindOfSell = "거래",mail;
    TextView subject, author, price, pubDate, publisher,exPress, faceToFace;
    ImageView bookImage2;
    TextView state_s,state_a, state_b, state_c;
    EditText sale_price;
    Button searchBook;
    ListView searchList;
    LinearLayout regiBackBtn;
    public static Vector<SearchList> vector = new Vector<>();
    DecimalFormat decimalFormat = new DecimalFormat("#,###");
    SearchListAdapter adapter;

    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#1E88E5"));
        }
        setContentView(R.layout.activity_normal_register);
        activity2 = this;
        regiBackBtn = (LinearLayout) findViewById(R.id.regiBackBtn);
        subject = (TextView) findViewById(R.id.subject2);
        author = (TextView) findViewById(R.id.author2);
        price = (TextView) findViewById(R.id.price2);
        pubDate = (TextView) findViewById(R.id.date2);
        publisher = (TextView) findViewById(R.id.publisher2);
        state_s = (TextView) findViewById(R.id.state_s);
        state_a = (TextView) findViewById(R.id.state_a);
        state_b = (TextView) findViewById(R.id.state_b);
        state_c = (TextView) findViewById(R.id.state_c);
        bookImage2 = (ImageView) findViewById(R.id.bookImage2);
        exPress = (TextView) findViewById(R.id.express2);
        faceToFace = (TextView) findViewById(faceToFace2);
        state_s.setOnClickListener(check);
        state_a.setOnClickListener(check);
        state_c.setOnClickListener(check);
        state_b.setOnClickListener(check);
        exPress.setOnClickListener(check);
        faceToFace.setOnClickListener(check);
        searchBook = (Button) findViewById(R.id.search_book);
        sale_price = (EditText) findViewById(R.id.sale_price2);
        final SharedPreferences autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        email = autoLogin.getString("email",null);
        salesMan = autoLogin.getString("loginNickName", null);
        salesPhone = autoLogin.getString("loginPhone", null);
        salesUniversity = autoLogin.getString("university", null);
        salesPrice = sale_price.getText().toString();
        book_register = (Button) findViewById(R.id.bookRegister2);
        searchBook = (Button) findViewById(R.id.search_book);
        /*
        bookName = intent.getExtras().getString("bookName");
        bookPrice = intent.getExtras().getString("bookPrice");
        bookPubDate = intent.getExtras().getString("bookPubDate");
        authorName = intent.getExtras().getString("authorName");
        publisherName = intent.getExtras().getString("publisherName");
        bookImage = intent.getExtras().getString("bookImage");*/
        searchBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showView2Dialog();
            }
        });
        book_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            salesPrice = sale_price.getText().toString();
                            jsonObject = new JSONObject();
                            jsonObject.put("email", email);
                            jsonObject.put("bookName", subject.getText().toString());
                            jsonObject.put("author", author.getText().toString());
                            jsonObject.put("originalPrice", price.getText().toString().replace("원","").replace(",",""));
                            jsonObject.put("pubdate", pubDate.getText().toString());
                            jsonObject.put("publisher", publisher.getText().toString());
                            jsonObject.put("state", bookState);
                            jsonObject.put("salePrice", sale_price.getText().toString());
                            jsonObject.put("salesman", salesMan);
                            jsonObject.put("salesNumber", salesPhone);
                            jsonObject.put("bookImage", bookImage);
                            jsonObject.put("university", salesUniversity);
                            /*jsonObject.put("selfImage1", salesPhone);
                            jsonObject.put("selfImage2", bookImages[0]);
                            jsonObject.put("selfImage3", salesUniversity);*/
                            jsonObject.put("kindOfSell", kindOfSell);
                            Log.d("regi",bookImage);
                        }catch (Exception e ){}
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String sendMsg = jsonObject.toString();
                                    Log.d("send", sendMsg);
                                    if(sale_price.getText().toString()==null || sale_price.getText().toString().equals("")){
                                        Toast.makeText(NormalRegisterActivity.this,"판매 가격을 입력해주세요.",Toast.LENGTH_SHORT).show();
                                    } else if(bookState.equals("상태")) {
                                        Toast.makeText(NormalRegisterActivity.this,"교재 상태를 선택해주세요.",Toast.LENGTH_SHORT).show();
                                    } else if(kindOfSell.equals("거래")) {
                                        Toast.makeText(NormalRegisterActivity.this,"거래 방법을 선택해주세요.",Toast.LENGTH_SHORT).show();
                                    } else {
                                        String result = new TaskMethod("http://creativerowan.com/bookmerang/register.jsp", sendMsg, "UTF-8").execute().get();
                                        Log.d("result", result + "asd");
                                        if (result.equals("true")) {

                                            /*Snackbar snackbar;
                                            snackbar = Snackbar.make(activity.main_view, "책이 정상적으로 등록되었습니다.",Snackbar.LENGTH_LONG);
                                            View snackView = snackbar.getView();
                                            snackView.setBackgroundColor(Color.parseColor("#3498db"));
                                            snackbar.show();*/
                                            Toast.makeText(activity.getApplicationContext(),"책이 정상적으로 등록되었습니다.",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(NormalRegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(NormalRegisterActivity.this,"결과:"+result,Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                }).start();
            }
        });
        regiBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    View.OnClickListener check = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.state_s :
                    backgroundChange(state_s, state_b, state_c,state_a);
                    bookState = "S";
                    break;
                case R.id.state_a :
                    backgroundChange(state_a, state_b, state_c,state_s);
                    bookState = "A";
                    break;
                case R.id.state_b :
                    backgroundChange(state_b, state_a, state_c,state_s);
                    bookState = "B";
                    break;
                case R.id.state_c :
                    backgroundChange(state_c, state_b, state_a,state_s);
                    bookState = "C";
                    break;
                case R.id.express2 :
                    backgroundChange(exPress, faceToFace);
                    kindOfSell = "택배";
                    break;
                case faceToFace2 :
                    backgroundChange(faceToFace, exPress);
                    kindOfSell = "직거래";
                    break;
            }
        }
    };

    public void backgroundChange(TextView v, TextView v2, TextView v3,TextView v4) {
        v.setBackgroundResource(R.drawable.normal_sel_regi);
        v2.setBackgroundResource(R.drawable.normal_regi);
        v3.setBackgroundResource(R.drawable.normal_regi);
        v4.setBackgroundResource(R.drawable.normal_regi);
        v.setTextColor(Color.WHITE);
        v2.setTextColor(Color.parseColor("#747474"));
        v3.setTextColor(Color.parseColor("#747474"));
        v4.setTextColor(Color.parseColor("#747474"));
    }
    public void backgroundChange(TextView v, TextView v2) {
        v.setBackgroundResource(R.drawable.normal_sel_regi);
        v2.setBackgroundResource(R.drawable.normal_regi);
        v.setTextColor(Color.WHITE);
        v2.setTextColor(Color.parseColor("#747474"));
    }

    private void showView2Dialog() {
        searchAddrDialog = new Dialog(this);
        searchAddrDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchAddrDialog.setContentView(R.layout.search_book);
        searchList = (ListView) searchAddrDialog.findViewById(R.id.searchList);
        final EditText searchText = (EditText) searchAddrDialog.findViewById(R.id.searchText3);
        final Button searchBtn = (Button) searchAddrDialog.findViewById(R.id.searchBtn3);

        //선택했을 때 mainAddr에 텍스트 세팅 될 수 있도록
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                subject.setText(vector.get(pos).getBookName());
                author.setText(vector.get(pos).getAuthor());
                price.setText(decimalFormat.format(Integer.parseInt(vector.get(pos).getOroginalPrice()))+"원");
                pubDate.setText(vector.get(pos).getPubdate());
                publisher.setText(vector.get(pos).getPublisher());

                bookImage = vector.get(pos).getBookImage();
                Picasso.with(NormalRegisterActivity.this).load(vector.get(pos).getBookImage()).into(bookImage2);
                searchAddrDialog.cancel();
                Log.d("select position",String.valueOf(pos));
                Log.d("select name",vector.get(pos).getBookName());
                Log.d("select bookUrl",vector.get(pos).getBookImage());
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(vector.size()!=0){
                    vector.clear();
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String text = searchText.getText().toString();
                        searchMethod(text);
                        Log.d("크기", vector.size() + "");
                        for(int i =0;i<vector.size();i++){
                            Log.d(i+"번째",vector.get(i).getBookName());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("vector size",String.valueOf(vector.size()));
                                Log.d("runOnUiThread",String.valueOf(i++));
                                adapter = new SearchListAdapter(getApplicationContext(),getLayoutInflater(),vector);
                                searchList.setAdapter(adapter);

                            }
                        });
                    }
                }).start();
            }
        });
        searchAddrDialog.show();
    }
    public void searchMethod(String bookNames) {
        try {
            String isbn = URLEncoder.encode(bookNames, "UTF8");
            String sendQuery = "https://openapi.naver.com/v1/search/book.xml?&start=1&sort=sim&display=30&query="+isbn;
            //String sendQuery = "http://openapi.naver.com/search?&start=1&target=book&query="+isbn;
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
                            authorName = xpp.getText().replace("<b>", "").replace("</b>", "");;
                        } else if (tag.equals("price")) {
                            xpp.next();
                            bookPrice = xpp.getText();
                        } else if (tag.equals("publisher")) { // getmapx value
                            xpp.next();
                            publisherName = xpp.getText();
                        } else if (tag.equals("pubdate")) { // getmapy valye
                            xpp.next();
                            bookPubDate = xpp.getText();
                            vector.add(new SearchList(bookName,bookImage,authorName,bookPrice,publisherName,bookPubDate));
                            Log.d("vector","add");
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
        Intent intent = new Intent(NormalRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}