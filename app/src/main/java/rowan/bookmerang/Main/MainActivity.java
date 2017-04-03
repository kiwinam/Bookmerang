package rowan.bookmerang.Main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rowan.bookmerang.Chatting.MessageActivity;
import rowan.bookmerang.Chatting.NotiService;
import rowan.bookmerang.Login.SelectLoginJoinActivity;
import rowan.bookmerang.Global.MainBaseActivity;
import rowan.bookmerang.R;
import rowan.bookmerang.Global.RecyclerAdapter;
import rowan.bookmerang.Register.NormalRegisterActivity;
import rowan.bookmerang.Register.ScanBarcodeActivity;
import rowan.bookmerang.SideMenu.FavoriteActivity;
import rowan.bookmerang.SideMenu.MySellBookActivity;
import rowan.bookmerang.SideMenu.SettingActivity;
import rowan.bookmerang.Global.TaskMethod;

public class MainActivity extends MainBaseActivity {

    public static MainActivity activity;
    View nav_header_view;
    public static String MyNickName;
    LinearLayout include_sell,include_buy,regi_barcode,regi_form,search_none,result_none;
    TextView toolbar_text,nav_nick,nav_header_nick,nav_header_email,nav_header_phone,nav_header_university;
    TranslateAnimation translateAnimation;
    List<Item> items = new ArrayList<>();
    TabHost tabHost;
    private long lastTimeBackPressed;
    Toolbar toolbar;
    ActionBar ab;
    SearchView action_search;
    SearchView searchView;
    MenuItem searchItem;
    Intent intent;
    private DrawerLayout mDrawerLayout;
    RecyclerView recyclerView;
    String email;
    ProgressBar search_progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        init();

        Intent noti = new Intent(MainActivity.this, NotiService.class);
        startService(noti);
        regi_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanBarcodeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
            }
        });

        regi_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NormalRegisterActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
            }
        });
    }




    void init(){
        SharedPreferences autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        email = autoLogin.getString("email",null);
        String nickName = autoLogin.getString("loginNickName", null);
        String university = autoLogin.getString("university",null);
        MyNickName = nickName;
        //Toast.makeText(MainActivity.this,name+"님 환영합니다.", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#3498db"));
        }
        //connect id
        include_buy = (LinearLayout) findViewById(R.id.include_buy);
        include_sell = (LinearLayout) findViewById(R.id.include_sell);
        regi_barcode = (LinearLayout) findViewById(R.id.regi_barcode);
        regi_form = (LinearLayout) findViewById(R.id.regi_form);
        search_none = (LinearLayout) findViewById(R.id.search_none);
        result_none = (LinearLayout) findViewById(R.id.result_none);
        toolbar_text = (TextView) findViewById(R.id.toolbar_text);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.search_view);
        action_search = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        search_progressBar = (ProgressBar) findViewById(R.id.search_progressBar);

        // Set Card view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
        //recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_main));
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu_blue_24dp);
        ab.setDisplayHomeAsUpEnabled(true);

        // navigation connect
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) { setupDrawerContent(navigationView); }
        nav_header_view = navigationView.getHeaderView(0);
        nav_header_nick = (TextView) nav_header_view.findViewById(R.id.nav_header_nick);
        nav_header_email = (TextView) nav_header_view.findViewById(R.id.nav_header_email);
        nav_header_university = (TextView) nav_header_view.findViewById(R.id.nav_header_university);

        nav_header_nick.setText(nickName);
        nav_header_email.setText(email);
        nav_header_university.setText(university);

        //tabhost
        tabHost.setup();
        TabHost.TabSpec tabSpecTab1 = tabHost.newTabSpec("buyLinear").setIndicator("구매하기").setContent(R.id.include_buy);
        tabHost.addTab(tabSpecTab1);
        TabHost.TabSpec tabSpecTab2 = tabHost.newTabSpec("sellLinear").setIndicator("판매하기").setContent(R.id.include_sell);
        tabHost.addTab(tabSpecTab2);
        tabHost.setCurrentTab(0);


        // Set visibility
        search_none.setVisibility(View.VISIBLE);
        result_none.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        search_progressBar.setVisibility(View.INVISIBLE);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                Log.d("itemId",String.valueOf(item.getItemId()));
                return true;


        }
        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        lastTimeBackPressed = System.currentTimeMillis();
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        //SearchView hint change
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("찾을 책을 검색하세요.");
        /*int closeButtonId = getResources().getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButtonImage = (ImageView) searchView.findViewById(closeButtonId);
        closeButtonImage.setImageResource(R.drawable.ic_clear_black_24dp);*/
        // SearchView input text color & hint color change
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.GRAY);
        searchAutoComplete.setTextColor(Color.BLACK);

        // SearchView 검색어 입력/검색 이벤트 처리
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                items.clear();
                Log.d("press","ok");
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(),0);

                try {
                    String search = new TaskMethod("http://creativerowan.com/bookmerang/searchBook.jsp","query="+query, "UTF-8").execute().get();
                    if (search == null) {
                        Log.d("return","null");
                    } else {
                        JSONObject jsonObjects = new JSONObject(search.toString());
                        JSONArray jsonArrays = new JSONArray(jsonObjects.getString("result"));

                        if(jsonArrays.length()==0){
                            search_none.setVisibility(View.GONE);
                            result_none.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }else{
                            search_none.setVisibility(View.GONE);
                            result_none.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            Item[] item = new Item[jsonArrays.length()];
                            search_progressBar.setVisibility(View.VISIBLE);
                            // When Search by searchBar
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
                            search_progressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()!=0){
                    items.clear();
                    result_none.setVisibility(View.GONE);
                    search_none.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                    items.clear();
                    result_none.setVisibility(View.GONE);
                    search_none.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                return true;
            }
        });

        getMenuInflater().inflate(R.menu.talk_view, menu);
        return true;
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()){

                            case R.id.nav_logout:
                                SharedPreferences autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = autoLogin.edit();
                                editor.clear();
                                editor.commit();
                                intent = new Intent(MainActivity.this, SelectLoginJoinActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                                finish();
                                break;
                            case R.id.nav_messages:
                                intent = new Intent(MainActivity.this, MessageActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                                finish();
                                break;
                            case R.id.nav_favorite:
                                intent = new Intent(MainActivity.this, FavoriteActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                                finish();
                                break;
                            case R.id.nav_sell:
                                intent = new Intent(MainActivity.this, MySellBookActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                                finish();
                                break;
                            case R.id.nav_setting:
                                intent = new Intent(MainActivity.this, SettingActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                                break;
                            default:
                                Log.d("menu id",String.valueOf(menuItem.getItemId()));
                        }
                        mDrawerLayout.closeDrawers();
                        return false;
                    }
                });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /*switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }*/
        return true;
    }
}