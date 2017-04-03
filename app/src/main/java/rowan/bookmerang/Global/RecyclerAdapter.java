package rowan.bookmerang.Global;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import rowan.bookmerang.DetailActivity;
import rowan.bookmerang.Main.Item;
import rowan.bookmerang.Main.MainActivity;
import rowan.bookmerang.R;

/**
 * Created by CHEONMYUNG on 2016-11-07.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {
    static MainActivity activity = MainActivity.activity;
    Context context;
    List<Item> items;
    int item_layout;
    Bitmap bitMap;
    String url;
    DecimalFormat decimalFormat = new DecimalFormat("#,###");
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView bookName,author, originalPrice, pubdate, publisher,state,salePrice,nickName,university;
        CardView cardview;

        public CustomViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            bookName = (TextView) itemView.findViewById(R.id.bookName);
            author = (TextView) itemView.findViewById(R.id.author);
            originalPrice = (TextView) itemView.findViewById(R.id.originalPrice);
            pubdate = (TextView) itemView.findViewById(R.id.pubdate);
            publisher = (TextView) itemView.findViewById(R.id.publisher);
            state = (TextView) itemView.findViewById(R.id.state);
            salePrice = (TextView) itemView.findViewById(R.id.salePrice);
            nickName = (TextView) itemView.findViewById(R.id.salesman);
            university = (TextView) itemView.findViewById(R.id.university);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

    public RecyclerAdapter(Context context, List<Item> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, null);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int position) {
        final int pos = position;
        final Item item = items.get(pos);
        url = items.get(pos).getImageUrl();

        Picasso.with(context).load(item.getImageUrl())
 /*               .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)*/
                .into(customViewHolder.image);

        customViewHolder.bookName.setText(item.getTitle());
        customViewHolder.author.setText(item.getAuthor());
        customViewHolder.originalPrice.setText(decimalFormat.format(Integer.parseInt(item.getOriginalPrice()))+"원");
        customViewHolder.pubdate.setText(item.getPubdate());
        customViewHolder.publisher.setText(item.getPublisher());
        customViewHolder.state.setText(item.getState());
        customViewHolder.salePrice.setText(decimalFormat.format(Integer.parseInt(item.getSalePrice()))+"원");
        customViewHolder.nickName.setText(item.getNickName());
        customViewHolder.university.setText(item.getUniversity());

        customViewHolder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("email_book",item.getEmail_book());
                intent.putExtra("bookCode",item.getBookCode());
                intent.putExtra("bookName",item.getTitle());
                intent.putExtra("imageUrl",item.getImageUrl());
                intent.putExtra("author",item.getAuthor());
                intent.putExtra("originalPrice",item.getOriginalPrice());
                intent.putExtra("pubdate",item.getPubdate());
                intent.putExtra("publisher",item.getPublisher());
                intent.putExtra("state",item.getState());
                intent.putExtra("salesNumber",item.getSalesNumber());
                intent.putExtra("salePrice",item.getSalePrice());
                intent.putExtra("nickName",item.getNickName());
                intent.putExtra("university",item.getUniversity());
                intent.putExtra("kindOfSell",item.getKindOfSell());
                context.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

}
