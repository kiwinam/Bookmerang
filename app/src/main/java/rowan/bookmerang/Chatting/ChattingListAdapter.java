package rowan.bookmerang.Chatting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

import rowan.bookmerang.R;

/**
 * Created by 15U560 on 2016-12-01.
 */

public class ChattingListAdapter extends BaseAdapter {
    LinearLayout goChatting;
    public static MessageActivity activity = MessageActivity.activity;
    Vector<Users> users;
    LayoutInflater inflater;
    Context context;
    public ChattingListAdapter(Context context, LayoutInflater inflater, Vector<Users> users) {
        this.context = context;
        this.inflater = inflater;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final int pos = position;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.chatting_list_row, viewGroup, false);
        }
        TextView bookNamess = (TextView) convertView.findViewById(R.id.bookNamess);
        TextView textView = (TextView)convertView.findViewById(R.id.roomNamess);
        bookNamess.append(users.get(pos).getBookName());
        textView.setText(users.get(pos).getYouTel()+"님과의 채팅방");

        return convertView;
    }
}
