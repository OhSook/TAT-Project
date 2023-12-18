package com.example.tatproject.ForRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatproject.PackDetailActivity;
import com.example.tatproject.R;

import java.util.ArrayList;

public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.ViewHolder> {
    private ArrayList<BookSingleItem> items = null;
    private Context context;
    String username;

    public class ViewHolder extends RecyclerView.ViewHolder{
        // 객체 정의
        TextView textTitle;
        TextView textPrice;
        TextView textJourney;
        TextView textPackNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.text_title);
            textPrice = itemView.findViewById(R.id.textPrice);
            textJourney = itemView.findViewById(R.id.textJourney);
            textPackNumber = itemView.findViewById(R.id.articleNum);
        }
    }
        // 생성자
    public BookRecyclerViewAdapter(ArrayList<BookSingleItem> list, Context context, String username){
        items = list;
        this.username = username;
        this.context = context;
    }

    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public BookRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.items_for_bookmark, parent, false);
        BookRecyclerViewAdapter.ViewHolder vh = new BookRecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    // position 에 해당하는 데이터를 뷰홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull BookRecyclerViewAdapter.ViewHolder holder, int position) {
        String title = items.get(position).title;
        String price = items.get(position).price;
        String journey = items.get(position).journey;
        int packID = items.get(position).packID;

        holder.textTitle.setText(title);
        holder.textJourney.setText(journey);
        holder.textPackNumber.setText(String.valueOf(packID));
        holder.textPrice.setText(price + " 원");

        holder.textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PackDetailActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("id", String.valueOf(packID));
                intent.putExtra("username", username);

                context.startActivity(intent);
            }
        });
    }

    // 전체 데이터 개수 리턴
    @Override
    public int getItemCount() {
        return items.size();
    }

    // 어댑터 아이템 수정
    public void setItems(ArrayList<BookSingleItem> list){
        items = list;
        notifyDataSetChanged();
    }
}