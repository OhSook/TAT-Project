package com.example.tatproject.ForRecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatproject.QADetail;
import com.example.tatproject.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    //
    private ArrayList<SingleItem> items = null;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        // 객체 정의
        TextView textTitle;
        TextView textWriter;
        TextView textWriteDate;
        TextView textArticleNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.text_title);
            textWriter = itemView.findViewById(R.id.textPrice);
            textWriteDate = itemView.findViewById(R.id.textJourney);
            textArticleNum = itemView.findViewById(R.id.articleNum);
        }
    }

    // 생성자
    public RecyclerViewAdapter(ArrayList<SingleItem> list, Context context){
        items = list;
        this.context = context;
    }

    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.items_for_recyclerview, parent, false);
        RecyclerViewAdapter.ViewHolder vh = new RecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    // position 에 해당하는 데이터를 뷰홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        String title = items.get(position).QATitle;
        String writer = items.get(position).writer;
        String writeDate = items.get(position).writeDate;
        int articleNum = items.get(position).articleNum;

        holder.textTitle.setText(title);
        holder.textWriter.setText(writer);
        holder.textWriteDate.setText(writeDate);
        holder.textArticleNum.setText("\n" + String.valueOf(articleNum));

        holder.textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QADetail.class);
                intent.putExtra("title", title);

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
    public void setItems(ArrayList<SingleItem> list){
        items = list;
        notifyDataSetChanged();
    }
}
