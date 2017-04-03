package rowan.bookmerang.SideMenu;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import rowan.bookmerang.Login.SelectLoginJoinActivity;
import rowan.bookmerang.Main.MainActivity;
import rowan.bookmerang.Global.MainBaseActivity;
import rowan.bookmerang.R;
import rowan.bookmerang.Global.TaskMethod;

/**
 * Created by CHEONMYUNG on 2016-12-01.
 */

public class SettingActivity extends MainBaseActivity {
    LinearLayout setting_securite,setting_agree,dropOut;
    Intent intent;
    String email;
    ImageView goBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#1E88E5"));
        }
        SharedPreferences autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        email = autoLogin.getString("email",null);
        setting_securite = (LinearLayout) findViewById(R.id.setting_securite);
        setting_agree = (LinearLayout) findViewById(R.id.setting_agree);
        dropOut = (LinearLayout) findViewById(R.id.dropOut);
        goBack = (ImageView) findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        setting_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SettingActivity.this, AgreementActivity.class);
                startActivity(intent);
            }
        });
        setting_securite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SettingActivity.this, SecurityActivity.class);
                startActivity(intent);
            }
        });
        dropOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setMessage("정말 회원 탈퇴 하시겠습니까?").setPositiveButton("회원탈퇴", dialogClickListener)
                        .setNegativeButton("취소", dialogClickListener).show();

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                try {
                    Log.d("email", email);
                    String result = new TaskMethod("http://creativerowan.com/bookmerang/Chat.jsp", "type=dropOut&email=" + email, "UTF-8").execute().get();
                    Log.d("email", "a"+result);

                    if(result.equals("true")){
                        dialog.cancel();
                        SharedPreferences autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = autoLogin.edit();
                        editor.clear();
                        editor.commit();
                        Toast.makeText(SettingActivity.this,"북메랑을 이용해 주셔서 감사합니다.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SettingActivity.this, SelectLoginJoinActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {

                }
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.cancel();
                    break;
            }
        }
    };
}