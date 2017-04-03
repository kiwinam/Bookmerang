package rowan.bookmerang.SideMenu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rowan.bookmerang.Main.Item;
import rowan.bookmerang.Main.MainActivity;
import rowan.bookmerang.Global.MainBaseActivity;
import rowan.bookmerang.R;
import rowan.bookmerang.Global.RecyclerAdapter;
import rowan.bookmerang.Global.TaskMethod;

/**
 * Created by CHEONMYUNG on 2016-11-30.
 */

public class MySellBookActivity extends MainBaseActivity {
    List<Item> items = new ArrayList<>();
    LinearLayout mysel_BackBtn,mysel_none;
    RecyclerView recyclerView;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysellbook);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#1E88E5"));
        }
        SharedPreferences autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        email = autoLogin.getString("email",null);
        mysel_BackBtn = (LinearLayout) findViewById(R.id.mysel_BackBtn);
        mysel_none = (LinearLayout) findViewById(R.id.mysel_none);
        recyclerView = (RecyclerView) findViewById(R.id.mysel_recyclerview);
        recyclerView.setVisibility(View.GONE);
        mysel_none.setVisibility(View.VISIBLE);
        mysel_BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        items.clear();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_favorite));
        Log.d("email",email);
        try {
            String favorite = new TaskMethod("http://creativerowan.com/bookmerang/mySellBook.jsp","email="+email, "UTF-8").execute().get();
            if (favorite == null){
                Log.d("return","null");
            } else {
                Log.d("return","not null");
                Log.d("return",favorite.toString());
                JSONObject jsonObjects = new JSONObject(favorite.toString());
                JSONArray jsonArrays = new JSONArray(jsonObjects.getString("result"));

                if(jsonArrays.length()==0){
                    recyclerView.setVisibility(View.GONE);
                    mysel_none.setVisibility(View.VISIBLE);
                }else{
                    mysel_none.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    // selling book by me
                    Item[] item = new Item[jsonArrays.length()];
                    for (int i = 0; i < jsonArrays.length(); i++) {
                        JSONObject jsonObjectss = (JSONObject) jsonArrays.get(i);
                        String email_book = jsonObjectss.getString("email");
                        String bookCode = jsonObjectss.getString("bookCode");
                        String bookName = jsonObjectss.getString("bookName");
                        String bookImageUrl = jsonObjectss.getString("bookImage");
                        String author = jsonObjectss.getString("author");
                        String originalPrice = jsonObjectss.getString("originalPrice");
                        String pubdate = jsonObjectss.getString("pubdate");
                        String publisher = jsonObjectss.getString("publisher");
                        String state = jsonObjectss.getString("state");
                        String salePrice = jsonObjectss.getString("salePrice");
                        String salesman = jsonObjectss.getString("salesman");
                        String salesNumber = jsonObjectss.getString("salesNumber");
                        String university = jsonObjectss.getString("university");
                        String kindOfSell = jsonObjectss.getString("kindOfSell");
                        String sellstate = jsonObjectss.getString("sellstate");
                        Log.d("bookCode",bookCode);
                        item[i] = new Item(email_book,bookCode,bookName, bookImageUrl, author, originalPrice, pubdate, publisher,state,salePrice,salesman,salesNumber,university,kindOfSell,sellstate);
                        items.add(item[i]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MySellBookActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
        finish();
    }
}
