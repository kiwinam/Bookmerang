package rowan.bookmerang.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import rowan.bookmerang.Global.AlertMethod;
import rowan.bookmerang.Global.BaseActivity;
import rowan.bookmerang.Main.MainActivity;
import rowan.bookmerang.R;
import rowan.bookmerang.Global.TaskMethod;

/**
 * Created by CHEONMYUNG on 2016-10-12.
 */

public class LoginActivity extends BaseActivity {
    SelectLoginJoinActivity activity = SelectLoginJoinActivity.activity;
    Button loginbtn;
    LinearLayout loginActivity,LoginBackBtn;
    EditText inputMail,inputPw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbtn = (Button) findViewById(R.id.loginbtn);
        LoginBackBtn = (LinearLayout) findViewById(R.id.LoginBackBtn);
        inputMail = (EditText) findViewById(R.id.inputMail);
        inputPw = (EditText) findViewById(R.id.inputPw);
        loginActivity = (LinearLayout) findViewById(R.id.loginActivity);
        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#3498db"));
        }
        //Login button Listener
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(20);
                String email = inputMail.getText().toString();
                String pw = inputPw.getText().toString();
                if (email.equals("") || pw.equals("")) {
                    AlertMethod.alertMethod("입력오류", "아이디 또는 비밀번호를 입력해주세요.", LoginActivity.this);
                } else {
                    try {
                        String result = new TaskMethod("http://creativerowan.com/bookmerang/login.jsp", "&email=" +email+ "&pw=" +pw, "UTF-8").execute().get();
                        String[] results = result.split("/");
                        Log.i("logbtn", result);
                        if (results[0].equals("true")) {
                            Toast.makeText(LoginActivity.this, "[" + results[1] + "]님 환영합니다^^", Toast.LENGTH_SHORT).show();
                            SharedPreferences autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
                            SharedPreferences.Editor editor = autoLogin.edit();
                            editor.putString("email",email);
                            editor.putString("loginPhone", results[3]);
                            editor.putString("loginNickName", results[1]);
                            editor.putString("university", results[2]);
                            editor.commit();
                            if (activity != null) {
                                activity.finish();
                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                            finish();
                        } else if (results[0].equals("false")) {
                            AlertMethod.alertMethod("로그인 오류", "입력하신 아이디 또는 비밀번호가 존재하지 않습니다.", LoginActivity.this);
                        } else {
                            Log.i("logbtn", "else");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });


        //바탕 눌렀을 때 입력창 사라지게하기
        loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(inputPw.getWindowToken(), 0);
            }
        });

        LoginBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, SelectLoginJoinActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
        finish();
    }
}
