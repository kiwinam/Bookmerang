package rowan.bookmerang.Register;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import rowan.bookmerang.Main.MainActivity;
import rowan.bookmerang.Global.MainBaseActivity;
import rowan.bookmerang.R;
import rowan.bookmerang.Global.TaskMethod;

/**
 * Created by 15U560 on 2016-10-25.
 */

public class BarcodeRegisterActivity extends MainBaseActivity {
    static BarcodeRegisterActivity activity;
    JSONObject jsonObject;
    Button book_register;
    ImageView bookImageView;
    DecimalFormat decimalFormat = new DecimalFormat("#,###");
    String email,bookName, bookPrice, bookPubDate, authorName, publisherName, salesPrice ,bookImage,salesMan,salesPhone,salesUniversity, bookState="상태";
    TextView author, price, subject, date, pub,bar_state_c,bar_state_b,bar_state_a,bar_state_s,exPress, faceToFace;
    EditText sale_price;
    Bitmap bitMap;
    Intent intent;
    String[] bookImages;
    String kindOfSell ="거래";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_register);
        Log.d("car11on","on");
        intent = getIntent();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#1E88E5"));
        }
        activity = this;
        SharedPreferences autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        email = autoLogin.getString("email", null);
        salesMan = autoLogin.getString("loginNickName", null);
        salesPhone = autoLogin.getString("loginPhone", null);
        salesUniversity = autoLogin.getString("university", null);
        bookName = intent.getExtras().getString("bookName");
        bookPrice = intent.getExtras().getString("bookPrice");
        bookPubDate = intent.getExtras().getString("bookPubDate");
        authorName = intent.getExtras().getString("authorName");
        publisherName = intent.getExtras().getString("publisherName");
        bookImage = intent.getExtras().getString("bookImage");
        bookImageView = (ImageView) findViewById(R.id.bookImage);
        author = (TextView) findViewById(R.id.author);
        price = (TextView) findViewById(R.id.price);
        subject = (TextView) findViewById(R.id.subject);
        date = (TextView) findViewById(R.id.date);
        pub = (TextView) findViewById(R.id.publisher);
        bar_state_c = (TextView) findViewById(R.id.bar_state_c);
        bar_state_b = (TextView) findViewById(R.id.bar_state_b);
        bar_state_a = (TextView) findViewById(R.id.bar_state_a);
        bar_state_s = (TextView) findViewById(R.id.bar_state_s);
        exPress = (TextView) findViewById(R.id.express);
        faceToFace = (TextView) findViewById(R.id.faceToFace);
        sale_price = (EditText) findViewById(R.id.sale_price);
        bar_state_c.setOnClickListener(check);
        bar_state_b.setOnClickListener(check);
        bar_state_a.setOnClickListener(check);
        bar_state_s.setOnClickListener(check);
        exPress.setOnClickListener(check);
        faceToFace.setOnClickListener(check);
        author.setText(authorName);
        price.setText(decimalFormat.format(Integer.parseInt(bookPrice))+"원");
        subject.setText(bookName);
        date.setText(bookPubDate);
        pub.setText(publisherName);
        book_register = (Button) findViewById(R.id.bookRegister);
        bookImages = bookImage.split("\\#");

        try {
            Back back = new Back();
            back.execute(bookImages[0]);
        } catch (Exception e) {}

        try {

        }catch (Exception e) {}

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
                            jsonObject.put("bookName", bookName);
                            jsonObject.put("author", authorName);
                            jsonObject.put("originalPrice", bookPrice);
                            jsonObject.put("pubdate", bookPubDate);
                            jsonObject.put("publisher", publisherName);
                            jsonObject.put("state", bookState);
                            jsonObject.put("salePrice", sale_price.getText().toString());
                            jsonObject.put("salesman", salesMan);
                            jsonObject.put("salesNumber", salesPhone);
                            jsonObject.put("bookImage", bookImages[0]);
                            jsonObject.put("university", salesUniversity);
                            /*jsonObject.put("selfImage1", salesPhone);
                            jsonObject.put("selfImage2", bookImages[0]);
                            jsonObject.put("selfImage3", salesUniversity);*/
                            jsonObject.put("kindOfSell", kindOfSell);
                        }catch (Exception e ){}
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                        /*String sendMsg = "bookName="+bookName+"&author="+authorName+"&originalPrice="+bookPrice+"&pubdate="+bookPubDate
                                +"&publisher="+publisherName+"&state="+bookState+"&salePrice="+salesPrice+"&salesman="+salesMan
                                +"&salesNumber="+salesPhone+"&bookImage="+bookImage+"&university="+salesUniversity;*/
                                    String sendMsg = jsonObject.toString();
                                    if(sale_price.getText().toString()==null || sale_price.getText().toString().equals("")){
                                        Toast.makeText(BarcodeRegisterActivity.this,"판매 가격을 입력해주세요.",Toast.LENGTH_SHORT).show();
                                    } else if(bookState.equals("상태")) {
                                        Toast.makeText(BarcodeRegisterActivity.this,"교재 상태를 선택해주세요.",Toast.LENGTH_SHORT).show();
                                    } else if(kindOfSell.equals("거래")) {
                                        Toast.makeText(BarcodeRegisterActivity.this,"거래 방법을 선택해주세요.",Toast.LENGTH_SHORT).show();
                                    } else {
                                        String result = new TaskMethod("http://creativerowan.com/bookmerang/register.jsp", sendMsg, "UTF-8").execute().get();
                                        Log.d("result", result + "asd");
                                        if (result.equals("true")) {
                                            /*Snackbar snackbar;
                                            snackbar = Snackbar.make(view, "책이 정상적으로 등록되었습니다.",Snackbar.LENGTH_LONG);
                                            View snackView = snackbar.getView();
                                            snackView.setBackgroundColor(Color.parseColor("#3498db"));
                                            snackbar.show();*/
                                            Toast.makeText(activity.getApplicationContext(),"책이 정상적으로 등록되었습니다.",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(BarcodeRegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(BarcodeRegisterActivity.this,"결과:"+result,Toast.LENGTH_SHORT).show();
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
    }

    View.OnClickListener check = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bar_state_s :
                    backgroundChange(bar_state_s,bar_state_b, bar_state_c,bar_state_a);
                    bookState = "S";
                    break;
                case R.id.bar_state_a :
                    backgroundChange(bar_state_a, bar_state_b, bar_state_c,bar_state_s);
                    bookState = "A";
                    break;
                case R.id.bar_state_b :
                    backgroundChange(bar_state_b, bar_state_a, bar_state_c,bar_state_s);
                    bookState = "B";
                    break;
                case R.id.bar_state_c :
                    backgroundChange(bar_state_c, bar_state_b, bar_state_a,bar_state_s);
                    bookState = "C";
                    break;
                case R.id.express:
                    backgroundChange(exPress, faceToFace);
                    kindOfSell = "택배";
                    break;
                case R.id.faceToFace:
                    backgroundChange(faceToFace, exPress);
                    kindOfSell = "직거래";
                    break;
            }
        }
    };
    class Back extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitMap = BitmapFactory.decodeStream(is);
            } /*catch (FileNotFoundException e) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                bitMap = BitmapFactory.decodeResource(BarcodeRegisterActivity.this.getResources(),R.mipmap.ic_highlight_off_black_48dp,options);
            }*/catch (IOException e) {
                e.printStackTrace();
            }
            return bitMap;
        }
        protected void onPostExecute(Bitmap img) {
            bookImageView.setImageBitmap(bitMap);

        }
    }

    /*View.OnClickListener registListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bookRegister :
                    try {
                        *//*String sendMsg = "bookName="+bookName+"&author="+authorName+"&originalPrice="+bookPrice+"&pubdate="+bookPubDate
                                +"&publisher="+publisherName+"&state="+bookState+"&salePrice="+salesPrice+"&salesman="+salesMan
                                +"&salesNumber="+salesPhone+"&bookImage="+bookImage+"&university="+salesUniversity;*//*
                        String sendMsg = jsonObject.toString();
                        Log.d("보내는 정보", sendMsg);
                        String result = new TaskMethod("http://creativerowan.com/bookmerang/register.jsp","sendMsg="+sendMsg, "UTF-8").execute().get();
                        Log.d("result", result);
                        if(result.equals("true")) {
                            Toast.makeText(BarcodeRegisterActivity.this,"책이 정상적으로 등록되었습니다.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(BarcodeRegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e){}
                    break;
            }

        }
    };*/
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BarcodeRegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}