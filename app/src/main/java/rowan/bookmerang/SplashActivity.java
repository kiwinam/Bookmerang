package rowan.bookmerang;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import rowan.bookmerang.Global.BaseActivity;
import rowan.bookmerang.Login.SelectLoginJoinActivity;
import rowan.bookmerang.Main.MainActivity;

/**
 * Created by CHEONMYUNG on 2016-11-25.
 */

public class SplashActivity extends BaseActivity {
    String phoneNum;
    static SplashActivity activity;
    public static final int MULTIPLE_PERMISSIONS = 1; // code you want.
    String[] permissions = new String[] {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#1E88E5"));
        }
        activity = this;
        SharedPreferences autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        phoneNum = autoLogin.getString("loginPhone", null);
        if(Build.VERSION.SDK_INT >= 23) {
            if (checkPermissions()){
                splashThread.start();
            } else {
                // show dialog informing them that we lack certain permissions
            }
        } else {
            splashThread.start();
        }
    }
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(activity,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission granted.
                    splashThread.start();
                } else {
                    // no permissions granted.
                }
                return;
            }
        }
    }
    Thread splashThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                Thread.sleep(1300);
                if(phoneNum !=null) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, SelectLoginJoinActivity.class);
                    startActivity(intent);
                    finish();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    });

}