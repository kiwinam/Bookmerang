package rowan.bookmerang.Chatting;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

import rowan.bookmerang.Global.MainBaseActivity;
import rowan.bookmerang.Main.MainActivity;
import rowan.bookmerang.R;

/**
 * Created by CHEONMYUNG on 2016-11-30.
 */

public class MessageActivity extends MainBaseActivity {
    public static MessageActivity activity;
    ListView chatting_list;
    static Vector<Users> users = new Vector<>();
    LinearLayout message_BackBtn;
    String myTel;
    int position;
    private ProgressBar mProgressBar;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        activity = this;
        // Set status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#3498db"));
        }
            users.clear();
        SharedPreferences autoLogin = getSharedPreferences("login", Activity.MODE_PRIVATE);
        myTel = autoLogin.getString("loginNickName", null);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        message_BackBtn = (LinearLayout) findViewById(R.id.message_BackBtn);
        chatting_list = (ListView) findViewById(R.id.chatting_list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if(postSnapshot.getKey().contains(myTel)) {
                        String[] tels = postSnapshot.getKey().split("ㅎ");
                        if (tels[0].equals(myTel)) {
                            users.add(new Users(tels[0], tels[1],tels[2]));
                        } else if(tels[1].equals(myTel)){
                            users.add(new Users(tels[1], tels[0],tels[2]));
                        }
                    }
                }
                try {
                    mProgressBar.setVisibility(ProgressBar.GONE);
                    ChattingListAdapter adapter = new ChattingListAdapter(MessageActivity.this,getLayoutInflater(),users);
                    chatting_list.setAdapter(adapter);
                    chatting_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int posi, long l) {
                            position = posi;
                            Intent intent = new Intent(MessageActivity.this,ChattingActivity.class);
                            intent.putExtra("myNick",users.get(position).getMyTel());
                            intent.putExtra("youNick",users.get(position).getYouTel());
                            intent.putExtra("bookName", users.get(position).getBookName());
                            startActivity(intent);
                            finish();
                        }
                    });
                }catch (Exception e) {

                }
            }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });

        message_BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MessageActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
        finish();
    }
}
