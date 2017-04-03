package rowan.bookmerang.Join;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

import rowan.bookmerang.Global.AlertMethod;
import rowan.bookmerang.BCrypt;
import rowan.bookmerang.Global.BaseActivity;
import rowan.bookmerang.Main.MainActivity;
import rowan.bookmerang.R;
import rowan.bookmerang.Login.SelectLoginJoinActivity;
import rowan.bookmerang.Global.TaskMethod;

/**
 * Created by CHEONMYUNG on 2016-10-11.
 */

public class JoinActivity extends BaseActivity {
    TextInputLayout ti_pw,ti_pwd,ti_nick,ti_join_email;
    TextInputEditText join_email,join_email_domain,join_pw,join_pwd,join_nickname,join_phone,join_phonecheck;
    TextView select_univ;
    Button joinPhoneBtn,joinFaceBtn,phone_submit_btn;
    LinearLayout joinBackBtn,joinLinear;
    private CallbackManager callbackManager;
    Intent intent;
    String email,email_domain,pw,pwd,nickName,phone,phoneCheck,univName,myPhoneNumber;
    Spinner email_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        init();

        // Set Listener for email spinner
        email_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    join_email_domain.setFocusable(true);
                    join_email_domain.setClickable(true);
                    ti_join_email.setHint("직접 입력하기");
                    join_email_domain.setText("");
                }else {
                    join_email_domain.setFocusable(false);
                    join_email_domain.setClickable(false);
                    ti_join_email.setHint("");
                    join_email_domain.setText(parent.getItemAtPosition(position).toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ti_pwd.setCounterEnabled(true);
        ti_nick.setCounterEnabled(true);

        join_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() < 6) {
                    ti_pw.setError("비밀번호는 6글자 이상이여야 합니다.");
                } else {
                    ti_pw.setError(null);
                }
            }
        });
/*
        TextWatcher pwTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() < 6) {
                    ti_pw.setError("비밀번호는 6글자 이상이여야 합니다.");
                } else {
                    ti_pw.setError(null);
                }
            }
        };*/

        /*phone_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(join_phone.getText().toString(), null, "SMS버튼이 눌렸음.", null, null);
            }
        });*/

        joinPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = join_email.getText().toString();
                email_domain = join_email_domain.getText().toString();
                pw = join_pw.getText().toString();
                pwd = join_pwd.getText().toString();
                nickName = join_nickname.getText().toString();
                phone = join_phone.getText().toString();

                email = email+"@"+email_domain;

                Log.d("request","email="+email+"  name="+nickName+"  &phone="+phone+"  &pw="+pw+"  &university="+univName);
                /*  for(int i = 0;i<editString.length;i++) {
                    request += "&"+editString[i]+"="+input[i];
                    }*/
                try {
                    String passwordHashed = BCrypt.hashpw(pw, BCrypt.gensalt(10));
                    Log.d("bc",String.valueOf(BCrypt.checkpw(pw,passwordHashed)));
                    Log.d("hash",passwordHashed);
                    Log.d("email",email);
                    String result = new TaskMethod("http://creativerowan.com/bookmerang/join.jsp",
                            "email="+email+"&nickName="+nickName+"&phone="+phone+"&passwordHashed="+passwordHashed+"&university="+univName, "utf-8").execute().get();
                    if (result.equals("ok")) {

                        Toast.makeText(JoinActivity.this, nickName + "님 가입이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JoinActivity.this, SelectLoginJoinActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (result.equals("phone")) {
                        AlertMethod.alertMethod("입력오류", "이미 존재하는 전화번호 입니다.", JoinActivity.this);
                    } else{
                        Log.d("else",result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // when click layout , input window down
        joinLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(joinLinear.getWindowToken(), 0);
            }
        });

        /*joinFaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLoginOnClick(v);
            }
        });
        joinBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
    }

    private void init(){
        intent = getIntent();
        univName = intent.getExtras().getString("univName");

        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#3498db"));
        }

        select_univ = (TextView) findViewById(R.id.select_univ);

        joinPhoneBtn = (Button) findViewById(R.id.joinPhoneBtn);
        /*joinFaceBtn = (Button) findViewById(R.id.joinFaceBtn);
        phone_submit_btn = (Button) findViewById(R.id.phone_submit_btn);*/
        joinBackBtn = (LinearLayout) findViewById(R.id.joinBackBtn);
        joinLinear = (LinearLayout) findViewById(R.id.joinLinear);

        ti_pw = (TextInputLayout) findViewById(R.id.ti_pw);
        ti_pwd = (TextInputLayout) findViewById(R.id.ti_pwd);
        ti_nick = (TextInputLayout) findViewById(R.id.ti_nick);
        ti_join_email = (TextInputLayout) findViewById(R.id.ti_join_email);

        join_email = (TextInputEditText) findViewById(R.id.join_email);
        join_email_domain = (TextInputEditText) findViewById(R.id.join_email_domain);
        join_pw = (TextInputEditText) findViewById(R.id.join_pw);
        join_pwd = (TextInputEditText) findViewById(R.id.join_pwd);
        join_nickname = (TextInputEditText) findViewById(R.id.join_nickname);
        join_phone = (TextInputEditText) findViewById(R.id.join_phone);
        //join_phonecheck = (TextInputEditText) findViewById(R.id.join_phonecheck);

        select_univ.setText(univName);

        email_spinner = (Spinner) findViewById(R.id.email_spinner);
        email_spinner.setSelection(0);

    }


    public void onBackPressed(){
        Intent intent = new Intent(JoinActivity.this, UnivActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //callbackManager.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void facebookLoginOnClick(View v){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(JoinActivity.this,Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult result) {

                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        if (response.getError() != null) {

                        } else {
                            Log.i("TAG", "user: " + user.toString());
                            Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());
                            setResult(RESULT_OK);

                            Intent i = new Intent(JoinActivity.this, MainActivity.class);
                            intent.putExtra("univName",univName);
                            startActivity(i);
                            finish();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                //finish();
            }
        });
    }
}