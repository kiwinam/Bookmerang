package rowan.bookmerang.Join;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import rowan.bookmerang.Global.BaseActivity;
import rowan.bookmerang.Login.SelectLoginJoinActivity;
import rowan.bookmerang.R;

/**
 * Created by 15U560 on 2016-11-15.
 */

public class UnivActivity extends BaseActivity {
    AutoCompleteTextView univName;
    Button goNextStep;
    String[] names;
    LinearLayout UnivBackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_univ);
        names = new UnivName().univName;
        univName = (AutoCompleteTextView) findViewById(R.id.university);
        goNextStep = (Button) findViewById(R.id.goNextStep);
        UnivBackBtn = (LinearLayout) findViewById(R.id.UnivBackBtn);

        if (Build.VERSION.SDK_INT >= 21) {   //상태바 색
            getWindow().setStatusBarColor(Color.parseColor("#1E88E5"));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.activity_univ_dropdown, names);
        univName.setAdapter(arrayAdapter);
        univName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                univName.setText(((TextView)view).getText().toString());
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(univName.getWindowToken(),0);
            }
        });
        goNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("univName", univName.getText().toString());
                for(int i=0; i<names.length; i++) {
                    if(univName.getText().toString().equals(names[i])) {
                        Intent intent = new Intent(UnivActivity.this, JoinActivity.class);
                        intent.putExtra("univName", univName.getText().toString());
                        startActivity(intent);
                        finish();
                        break;
                    }else if(i+1 == names.length) {
                        Toast.makeText(UnivActivity.this,"학교이름을 정확히 입력해주세요.",Toast.LENGTH_SHORT).show();
                    }
                }
                /*if(univName.getText().toString()==null ||univName.getText().toString().equals("")) {
                } else {
                }*/
            }
        });
        UnivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onBackPressed(){
        Intent intent = new Intent(UnivActivity.this, SelectLoginJoinActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
        finish();
    }
}