package rowan.bookmerang.Chatting;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import rowan.bookmerang.R;

/**
 * Created by 15U560 on 2016-12-01.
 */

public class ChattingActivity extends AppCompatActivity {

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageTextView;
        public TextView messengerTextView;
        public LinearLayout messageLayout;
        public CircleImageView messengerImageView;

        public MessageViewHolder(View v) {
            super(v);
            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messengerTextView = (TextView) itemView.findViewById(R.id.messengerTextView);
            messageLayout = (LinearLayout) itemView.findViewById(R.id.messageLayout);
            messengerImageView = (CircleImageView) itemView.findViewById(R.id.messengerImageView);
        }
    }
    String userName;
    EditText editText;
    RecyclerView listView;
    Button sendButton;
    ImageButton backBtn;
    TextView userNum;
    String dbName, user1Nick, user2Nick, bookName;
    Intent intent;
    private NotificationManager mNotificationManager;
    private DatabaseReference databaseReference;
    private LinearLayoutManager mLinearLayoutManager;

    private ProgressBar mProgressBar;
    private FirebaseRecyclerAdapter<ChatData, MessageViewHolder>
            mFirebaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#3498db"));
        }
        intent = getIntent();
        listView = (RecyclerView) findViewById(R.id.listView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        listView.setLayoutManager(mLinearLayoutManager);
        editText = (EditText) findViewById(R.id.editText);
        sendButton = (Button) findViewById(R.id.button);
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        userNum = (TextView) findViewById(R.id.userNum);
        user1Nick = intent.getStringExtra("myNick");
        user2Nick = intent.getStringExtra("youNick");
        bookName = intent.getStringExtra("bookName");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String books[] = bookName.split("\\(");
        String[] sorts = {user1Nick, user2Nick};
        userNum.setText(user2Nick +" 님과의 대화");
        Arrays.sort(sorts);
        dbName = sorts[0] + "ㅎ" + sorts[1]+"ㅎ"+ books[0];
        userName = user1Nick;  // 랜덤한 유저 이름 설정 ex) user1234


// 기본 Text를 담을 수 있는 simple_list_item_1을 사용해서 ArrayAdapter를 만들고 listview에 설정
        Log.d("dbname111",dbName);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<ChatData,
                MessageViewHolder>(
                ChatData.class,
                R.layout.simple_list_item_1,
                MessageViewHolder.class,
                databaseReference.child(dbName)) {

            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder,
                                              ChatData chatData, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.messageTextView.setText(chatData.getMessage());
                viewHolder.messengerTextView.setText(chatData.getUserName());
                if (chatData.getUserName().equals(user1Nick)) {
                    viewHolder.messengerImageView
                            .setImageDrawable(ContextCompat
                                    .getDrawable(ChattingActivity.this,
                                            R.drawable.chat_i));
                } else {
                    viewHolder.messengerImageView
                            .setImageDrawable(ContextCompat
                                    .getDrawable(ChattingActivity.this,
                                            R.drawable.chat_u));
                }
                /*if (friendlyMessage.getPhotoUrl() == null) {
                    viewHolder.messengerImageView
                            .setImageDrawable(ContextCompat
                                    .getDrawable(MainActivity.this,
                                            R.drawable.ic_account_circle_black_36dp));
                } else {
                    Glide.with(MainActivity.this)
                            .load(friendlyMessage.getPhotoUrl())
                            .into(viewHolder.messengerImageView);
                }*/
            }
        };
        Log.d("dbname222",dbName);
        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    listView.scrollToPosition(positionStart);
                }
            }
        });
        listView.setLayoutManager(mLinearLayoutManager);
        listView.setAdapter(mFirebaseAdapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals("") || editText.getText().toString() == null) {

                } else {
                    ChatData chatData = new ChatData(user1Nick, user2Nick,editText.getText().toString());  // 유저 이름과 메세지로 chatData 만들기)
                    databaseReference.child(dbName).push().setValue(chatData);  // 기본 database 하위 message라는 child에 chatData를 list로 만들기
                    editText.setText("");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChattingActivity.this, MessageActivity.class);
        startActivity(intent);
        finish();
    }
}
