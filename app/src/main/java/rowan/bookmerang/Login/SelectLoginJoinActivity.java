package rowan.bookmerang.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import rowan.bookmerang.Global.BaseActivity;
import rowan.bookmerang.R;
import rowan.bookmerang.Join.UnivActivity;

/**
 * Created by CHEONMYUNG on 2016-11-25.
 */

public class SelectLoginJoinActivity extends BaseActivity {
    public static SelectLoginJoinActivity activity;
    Button gotoJoin,gotoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_login_join);
        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#1E88E5"));
        }
        gotoLogin = (Button) findViewById(R.id.gotoLogin);
        gotoJoin = (Button) findViewById(R.id.gotoJoin);
        gotoJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    gotoJoin.setElevation(8);
                }
                Intent intent = new Intent(SelectLoginJoinActivity.this,UnivActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                finish();
            }
        });
        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    gotoLogin.setElevation(8);
                }
                Intent intent = new Intent(SelectLoginJoinActivity.this,LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                finish();
            }
        });
    }
}
