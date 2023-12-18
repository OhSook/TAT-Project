package com.example.tatproject.ForRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatproject.PackDetailActivity;
import com.example.tatproject.PackageActivity;
import com.example.tatproject.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class PackRecyclerViewAdapter extends RecyclerView.Adapter<PackRecyclerViewAdapter.ViewHolder> {
    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@192.168.137.1:1521:xe";
    private static final String USERNAME = "c##group";
    private static final String PASSWORD = "1234";
    private Connection connection;
    //
    private ArrayList<PackageSingleItem> items = null;
    private Context context;
    private final String username;

    public class ViewHolder extends RecyclerView.ViewHolder{
        // 객체 정의
        TextView textPackageID;
        TextView textTitle;
        TextView textPrice;
        ImageView bookmarkImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textPackageID = itemView.findViewById(R.id.text_pack_id);
            textTitle = itemView.findViewById(R.id.text_pack_title);
            textPrice = itemView.findViewById(R.id.text_price);
            bookmarkImg = itemView.findViewById(R.id.bookmark);
        }
    }

    // 생성자
    public PackRecyclerViewAdapter(ArrayList<PackageSingleItem> list, Context context, String username){
        items = list;
        this.context = context;
        this.username = username;
    }

    // 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @Override
    public PackRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.items_for_packrecyclerview, parent, false);
        PackRecyclerViewAdapter.ViewHolder vh = new PackRecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    // position 에 해당하는 데이터를 뷰홀더의 아이템 뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull PackRecyclerViewAdapter.ViewHolder holder, int position) {
        String packageID = items.get(position).packageID;
        String title = items.get(position).title;
        String price = items.get(position).price;
        int resID = items.get(position).resID; // 북마크 저장 전
        int resID2 = items.get(position).resID2; // 북마크 저장 후

        Intent intent = ((Activity) context).getIntent();
        String username = intent.getStringExtra("username");
        String userid = intent.getStringExtra("user");

        holder.textPackageID.setText("\n" + packageID + "\n");
        holder.textTitle.setText("\n" + title + "\n");
        holder.textPrice.setText("\n" + price + "\n");

        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM BOOK_TABLE WHERE USERNAME='" +
                    username + "' AND BOOK_ID=" + Integer.parseInt(packageID));
            StringBuffer stringBuffer = new StringBuffer();

            while(resultSet.next()){
                stringBuffer.append(resultSet.getString(1) + ",");
                stringBuffer.append(resultSet.getString(2) + ",");
                stringBuffer.append("\n");
            }

            if (stringBuffer.toString().contains(username) && stringBuffer.toString().contains(packageID)){
                holder.bookmarkImg.setImageResource(resID2);
            }else{
                holder.bookmarkImg.setImageResource(resID);
            }

            stringBuffer.setLength(0);
        }catch(Exception e){
            e.printStackTrace();
        }

        holder.textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PackDetailActivity.class);
                intent.putExtra("id", packageID);
                intent.putExtra("username", username);
                intent.putExtra("user", userid);

                context.startActivity(intent);
            }
        });

        holder.bookmarkImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (username.length() > 0){
                    try{
                        Class.forName(DRIVER);
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("SELECT * FROM BOOK_TABLE WHERE USERNAME='" +
                                username + "' AND BOOK_ID=" + Integer.parseInt(packageID));

                        if (! resultSet.next()) {
                            statement.executeQuery("INSERT INTO BOOK_TABLE(USERNAME, BOOK_ID) VALUES('"
                                    + username + "', "+ Integer.parseInt(packageID) + ")");

                            holder.bookmarkImg.setImageResource(resID2);
                            Snackbar.make(v, "보관함에 해당 패키지를 저장했습니다.", BaseTransientBottomBar.LENGTH_LONG).show();
                        }else{
                            statement.executeQuery("DELETE FROM BOOK_TABLE WHERE BOOK_ID=" + Integer.parseInt(packageID));
                            holder.bookmarkImg.setImageResource(resID);

                            Snackbar.make(v, "보관함에서 해당 패키지를 삭제했습니다.", BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else {
                    Snackbar.make(v, "로그인 이후에 이용할 수 있는 서비스입니다.", BaseTransientBottomBar.LENGTH_LONG).show();
                }
            }
        });
    }

    // 전체 데이터 개수 리턴
    @Override
    public int getItemCount() {
        return items.size();
    }

    // 어댑터 아이템 수정
    public void setItems(ArrayList<PackageSingleItem> list){
        items = list;
        notifyDataSetChanged();
    }
}
