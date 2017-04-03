package rowan.bookmerang;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import rowan.bookmerang.Chatting.ChattingActivity;
import rowan.bookmerang.Global.MainBaseActivity;
import rowan.bookmerang.Global.TaskMethod;
import rowan.bookmerang.SideMenu.FavoriteActivity;

/**
 * Created by CHEONMYUNG on 2016-11-26.
 */

public class DetailActivity extends MainBaseActivity {
    FavoriteActivity activity = FavoriteActivity.activity;
    String imageUrl;
    TextView detail_bookName,detail_author,detail_publisher,detail_pubdate,detail_state,detail_originalPrice, detail_salePrice,detail_savePrice,detail_nickName,detail_university,detail_kindOfSell;
    ImageView detail_image;
    FloatingActionButton detail_fab;
    LinearLayout detail_call,detail_sms,wait_sell,comple_sell;
    String favorite,email,bookCode,email_book,myNickName;
    String salesNumber, nickName,bookname;
    Snackbar snackbar;
    DecimalFormat priceFormat = new DecimalFormat("#,###");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        loadBackdrop();

        detail_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+salesNumber));
                startActivity(intent);
            }
        });

    }
    // initailize tv and other views
    private void init(){
        // Join View
        wait_sell = (LinearLayout) findViewById(R.id.wait_sell);
        comple_sell = (LinearLayout) findViewById(R.id.comple_sell);
        wait_sell.setVisibility(View.GONE);
        comple_sell.setVisibility(View.GONE);
        detail_bookName = (TextView) findViewById(R.id.detail_bookName);
        detail_author = (TextView) findViewById(R.id.detail_author);
        detail_publisher = (TextView) findViewById(R.id.detail_publisher);
        detail_pubdate = (TextView) findViewById(R.id.detail_pubdate);
        detail_state = (TextView) findViewById(R.id.detail_state);
        detail_originalPrice = (TextView) findViewById(R.id.detail_originalPrice);
        detail_salePrice = (TextView) findViewById(R.id.detail_salePrice);
        detail_savePrice = (TextView) findViewById(R.id.detail_savePrice);
        detail_nickName = (TextView) findViewById(R.id.detail_nickName);
        detail_university = (TextView) findViewById(R.id.detail_university);
        detail_kindOfSell = (TextView) findViewById(R.id.detail_kindOfSell);
        detail_call = (LinearLayout) findViewById(R.id.detail_call);
        detail_sms = (LinearLayout) findViewById(R.id.detail_sms);

        detail_image = (ImageView) findViewById(R.id.detail_image);
        detail_fab = (FloatingActionButton) findViewById(R.id.detail_fab);

        // Set status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#1E88E5"));
        }

        // Get information by intent
        final Intent intent = getIntent();
        SharedPreferences autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        email = autoLogin.getString("email",null);
        myNickName = autoLogin.getString("loginNickName", null);
        bookCode = intent.getStringExtra("bookCode");

        Log.d("email",email);
        Log.d("bookCode",bookCode);
        email_book = intent.getStringExtra("email_book");
        Log.d("email_book",email_book);
        bookname = intent.getStringExtra("bookName");
        String imageUrl = intent.getStringExtra("imageUrl");
        String author = intent.getStringExtra("author");
        String publisher = intent.getStringExtra("publisher");
        String pubdate = intent.getStringExtra("pubdate");
        String state = intent.getStringExtra("state");
        String originalPrice = intent.getStringExtra("originalPrice");
        String salePrice = intent.getStringExtra("salePrice");
        salesNumber = intent.getStringExtra("salesNumber");
        nickName = intent.getStringExtra("nickName");
        String university = intent.getStringExtra("university");
        String kindOfSell = intent.getStringExtra("kindOfSell");
        //String level = intent.getStringExtra("level");
        favorite = "no";

        if(email.equals(email_book)){
            Log.d("equlas","true");
            comple_sell.setVisibility(View.VISIBLE);
            comple_sell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    snackbar = Snackbar.make(v, "판매 완료하시겠습니까?.",Snackbar.LENGTH_LONG).setActionTextColor(Color.parseColor("#FF0000"))
                            .setAction("YES", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    String result = null;
                                    try {
                                        result = new TaskMethod("http://creativerowan.com/bookmerang/dismissBook.jsp", "bookCode="+bookCode, "utf-8").execute().get();
                                        if(result==null){
                                            Log.d("dismiss result ","/null/"+result);
                                        }else if(result.equals("ok")){
                                            Toast.makeText(v.getContext(),"판매완료 되었습니다.",Toast.LENGTH_SHORT);
                                            finish();
                                        }else{

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    View snackView = snackbar.getView();
                    snackView.setBackgroundColor(Color.parseColor("#3498db"));
                    snackbar.show();
                    detail_fab.setImageResource(R.drawable.ic_star_white_36dp);
                    favorite = "yes";
                }
            });
        }else {
            Log.d("equlas","false");
            wait_sell.setVisibility(View.VISIBLE);
        }
        // Set information
        detail_bookName.setText(bookname);
        Picasso.with(this).load(imageUrl)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(detail_image);
        detail_author.setText(author);
        detail_publisher.setText(publisher);
        detail_pubdate.setText(pubdate);
        detail_state.setText(state);
        detail_originalPrice.setText(String.valueOf(priceFormat.format(Integer.parseInt(originalPrice)))+" 원");
        detail_salePrice.setText(String.valueOf(priceFormat.format(Integer.parseInt(salePrice)))+" 원");
        int save = Integer.parseInt(originalPrice) - Integer.parseInt(salePrice);
        detail_savePrice.setText(String.valueOf("- "+priceFormat.format(save))+" 원");
        detail_nickName.setText(nickName);
        detail_university.setText(university);
        detail_kindOfSell.setText(kindOfSell);

        // Set toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(bookname);
        // Set Favorite
        String result=null;
        try {
            result = new TaskMethod("http://creativerowan.com/bookmerang/checkFavoriteBook.jsp", "email="+email+"&bookCode="+bookCode, "utf-8").execute().get();
            if(result==null){
                Log.d("init fav","/null/"+result);
            }else if(result.equals("check exist")){
                detail_fab.setImageResource(R.drawable.ic_star_white_36dp);
                favorite = "yes";
                Log.d("init fav",result);
            }else{
                Log.d("init fav",result);
                detail_fab.setImageResource(R.drawable.ic_star_border_white_36dp);
                favorite = "no";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        detail_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(myNickName, nickName);
                    //String result = new TaskMethod("http://creativerowan.com/bookmerang/Chat.jsp", "type=setTel&myTel="+myNickName+"&youTel="+nickName, "UTF-8").execute().get();

                    Intent intent1 = new Intent(DetailActivity.this, ChattingActivity.class);
                    intent1.putExtra("myNick", myNickName);
                    intent1.putExtra("youNick", nickName);
                    intent1.putExtra("bookName", bookname);
                    startActivity(intent1);
                    finish();

                } catch (Exception e) {

                }

            }
        });
        // Set Listener
        detail_fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(favorite.equals("no")){
                    String result = null;
                    try {
                        result = new TaskMethod("http://creativerowan.com/bookmerang/favoriteBook.jsp", "email="+email+"&bookCode="+bookCode, "utf-8").execute().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result.equals("ok")) {
                        Snackbar snackbar;
                        snackbar = Snackbar.make(v, "즐겨찾기에 추가되었습니다.",Snackbar.LENGTH_SHORT);
                        View snackView = snackbar.getView();
                        snackView.setBackgroundColor(Color.parseColor("#3498db"));
                        snackbar.show();
                        detail_fab.setImageResource(R.drawable.ic_star_white_36dp);
                        favorite = "yes";
                    }else{
                        Log.d("fav result",result);
                    }
                } else{
                    String result = null;
                    try {
                        result = new TaskMethod("http://creativerowan.com/bookmerang/dismissFavoriteBook.jsp", "email="+email+"&bookCode="+bookCode, "utf-8").execute().get();
                        if(result.equals("dismiss")){
                            Snackbar snackbar;
                            snackbar = Snackbar.make(v, "즐겨찾기에서 삭제되었습니다.",Snackbar.LENGTH_SHORT);
                            View snackView = snackbar.getView();
                            snackView.setBackgroundColor(Color.parseColor("#3498db"));
                            snackbar.show();
                            detail_fab.setImageResource(R.drawable.ic_star_border_white_36dp);
                            favorite = "no";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(BooksImage.getRandomBookDrawable()).centerCrop().into(imageView);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
        finish();
    }
}