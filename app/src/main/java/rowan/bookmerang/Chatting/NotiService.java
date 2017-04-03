package rowan.bookmerang.Chatting;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import rowan.bookmerang.Main.MainActivity;
import rowan.bookmerang.R;

/**
 * Created by 15U560 on 2016-12-07.
 */

public class NotiService extends Service {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private String nickName = MainActivity.MyNickName;
    private String[] tels;
    private String notiTitle;
    String newKey,oldKey;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    if (key.contains(nickName)) {
                        tels = key.split("ㅎ");
                        Log.d("있는 키값",dataSnapshot.getChildrenCount()+"");
                        if (tels[0].equals(nickName)) {
                            notiTitle = tels[1];
                        } else if(tels[1].equals(nickName)){
                            notiTitle = tels[0];
                        }
                            databaseReference.child(key).limitToLast(1).addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    oldKey = dataSnapshot.getKey();
                                    ChatData chatData = dataSnapshot.getValue(ChatData.class);
                                    Log.d("칠드런크기",dataSnapshot.getKey() +""+s);
                                    if(newKey==null) {
                                        newKey = oldKey;
                                    } else if (newKey.equals(oldKey)) {

                                    } else if (!newKey.equals(oldKey) && !chatData.getUserName().equals(nickName)) {
                                        newKey = oldKey;
                                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                        Intent intent = new Intent(NotiService.this, ChattingActivity.class);
                                        intent.putExtra("myNick",nickName);
                                        intent.putExtra("youNick",notiTitle);
                                        intent.putExtra("bookName", tels[2]);
                                        PendingIntent pendingIntent = PendingIntent.getActivity(NotiService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        Notification.Builder builder = new Notification.Builder(NotiService.this);
                                        builder.setSmallIcon(R.drawable.noti_icon);
                                        builder.setTicker("새로운 메세지가 왔습니다 - 북메랑");
                                        builder.setContentTitle("새 메세지");
                                        builder.setContentText(notiTitle+"님으로부터 메세지가 왔습니다.");
                                        builder.setWhen(System.currentTimeMillis());
                                        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
                                        builder.setContentIntent(pendingIntent);
                                        builder.setAutoCancel(true);
                                        notificationManager.notify(0, builder.build());

                                    }
                                }
                                @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                                @Override public void onChildRemoved(DataSnapshot dataSnapshot) {}
                                @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                                @Override public void onCancelled(DatabaseError databaseError) {}
                            });

                    }


                }
            }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
        return super.onStartCommand(intent, flags, startId);
    }
}
