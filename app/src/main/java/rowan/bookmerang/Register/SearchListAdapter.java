package rowan.bookmerang.Register;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Vector;

import rowan.bookmerang.R;

/**
 * Created by 15U560 on 2016-11-14.
 */

public class SearchListAdapter extends BaseAdapter {

    Context context;
    Vector<SearchList> listViewItemList;
    LayoutInflater inflater;

    String bookImagess;
    DecimalFormat decimalFormat = new DecimalFormat("#,###");

    public SearchListAdapter(Context context, LayoutInflater inflater, Vector<SearchList> listViewItemList){
        this.context = context;

        //객체를 생성하면서 전달받은 datas(MemberData 객체배열)를 멤버변수로 전달
        //아래의 다른 멤버 메소드에서 사용하기 위해서.멤버변수로 참조값 전달
        this.listViewItemList= listViewItemList;


        //이 MemberDataAdapter 클래스를 객체로 만들어내는 클래스에서 LayoutInflater 객체 전달해주야 함.
        //이번 예제어세는 MainActivity.java에서 전달.
        //xml 종이의 글씨를 부풀려서 객체로 메모리에 만들어 낸다고 해서 '부풀리다(inflate)'라는 표현 사용
        this.inflater= inflater;
    }


    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        Log.d("Adater size",String.valueOf(getCount()));
        Log.d("Adater getView",String.valueOf(pos));
        Context context = parent.getContext();
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.search_book_row, parent, false);
            holder = new ViewHolder();
            holder.bookName = (TextView) convertView.findViewById(R.id.subject3);
            holder.author = (TextView) convertView.findViewById(R.id.author3);
            holder.price = (TextView) convertView.findViewById(R.id.price3);
            holder.pubdate = (TextView) convertView.findViewById(R.id.date3);
            holder.publisher = (TextView) convertView.findViewById(R.id.publisher3);
            holder.bookImage = (ImageView) convertView.findViewById(R.id.bookImage3);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        // Draw on ListView
        Picasso.with(context).load(listViewItemList.get(pos).getBookImage()).into(holder.bookImage);
        bookImagess = listViewItemList.get(pos).getBookImage();
        holder.bookName.setText(listViewItemList.get(pos).getBookName());
        holder.author.setText(listViewItemList.get(pos).getAuthor());
        holder.price.setText(listViewItemList.get(pos).getOroginalPrice());
        holder.pubdate.setText(listViewItemList.get(pos).getPubdate());
        holder.publisher.setText(listViewItemList.get(pos).getPublisher());



        return convertView;
    }

    static class ViewHolder{
        ImageView bookImage;
        TextView bookName,author,price,pubdate, publisher;
    }
}