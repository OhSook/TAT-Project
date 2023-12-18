package com.example.tatproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyPageActivity extends AppCompatActivity {

    // 본인 DB계정으로 변경
    private static final String DRIVER = "oracle.jdbc.OracleDriver";

    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;
    String user_id;
    String book_id;
    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        //보안정책
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        TextView name = findViewById(R.id.username);
        TextView email = findViewById(R.id.email);
        TextView mile = findViewById(R.id.mile);
        TextView grade = findViewById(R.id.grade);
        TextView bookcheck = findViewById(R.id.bookcheck);
        TextView paycheck = findViewById(R.id.paycheck);
        TextView readbookbtn = findViewById(R.id.readbookbtn);
        TextView paybtn = findViewById(R.id.paybtn);
        TextView readpaybtn = findViewById(R.id.readpaybtn);
        TextView cancelbtn = findViewById(R.id.cancelbtn);
        ImageView gradeimg = findViewById(R.id.gradeimg);
        ImageView box1 = findViewById(R.id.box1);
        ImageView box2 = findViewById(R.id.box2);

        Intent getIntent = getIntent();
        user_id = getIntent.getStringExtra("user");
        this.username = getIntent.getStringExtra("username");

        name.setText(user_id);
        try{
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();

            StringBuffer gradeBuffer = new StringBuffer();
            StringBuffer nameBuffer = new StringBuffer();
            StringBuffer mileBuffer = new StringBuffer();
            StringBuffer emailBuffer = new StringBuffer();
            StringBuffer packnameBuffer = new StringBuffer();
            StringBuffer packidBuffer = new StringBuffer();
            StringBuffer payBuffer = new StringBuffer();

            ResultSet resultSet = statement.executeQuery("select username,mile,rank,email,book_id,pay from cus_user where userid = '"+user_id+"'");

            while(resultSet.next()){
                nameBuffer.append(resultSet.getString(1));
                mileBuffer.append(resultSet.getString(2));
                gradeBuffer.append(resultSet.getString(3));
                emailBuffer.append(resultSet.getString(4));
                packidBuffer.append(resultSet.getString(5));
                payBuffer.append(resultSet.getString(6));
            }
            name.setText(nameBuffer.toString()+" 님");
            mile.setText(mileBuffer.toString());
            grade.setText(gradeBuffer.toString());
            email.setText(emailBuffer.toString());
            book_id = packidBuffer.toString();
            String pay = payBuffer.toString();
            String mileimg = gradeBuffer.toString();

            if(mileimg.contains("bronze")){
                gradeimg.setImageResource(R.drawable.bronze);
            }else if(mileimg.contains("silver")){
                gradeimg.setImageResource(R.drawable.silver);
            }else if(mileimg.contains("gold")){
                gradeimg.setImageResource(R.drawable.gold);
            }else if(mileimg.contains("platinum")){
                gradeimg.setImageResource(R.drawable.pla);
            }else if(mileimg.contains("diamond")){
                gradeimg.setImageResource(R.drawable.dia);
            }


            ResultSet resultSet2 = statement.executeQuery("select pack_name from create_pack where pack_id = "+book_id);

            while(resultSet2.next()){
                packnameBuffer.append(resultSet2.getString(1));
            }

            String packname = packnameBuffer.toString();

            // 1. book, 결제 아무 것도 안 했을 때
            // 2. book만 했을 때
            // 3. 둘 다 했을 때

            if(book_id.contains("null")){
                bookcheck.setText("내역이 없습니다.");
                paycheck.setText("내역이 없습니다.");
            }else if(!book_id.contains("null") && pay.contains("null")){
                bookcheck.setText(packname);
                paycheck.setText("내역이 없습니다.");
                readbookbtn.setVisibility(View.VISIBLE);
                paybtn.setVisibility(View.VISIBLE);
            }else if(!book_id.contains("null") && pay.contains("결제완료")){
                readbookbtn.setVisibility(View.INVISIBLE);
                paybtn.setVisibility(View.INVISIBLE);
                bookcheck.setText("내역이 없습니다.");
                paycheck.setText(packname);
                readpaybtn.setVisibility(View.VISIBLE);
                cancelbtn.setVisibility(View.VISIBLE);
            }


        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }

    }


    // 상세보기 버튼
    public void onClicked(View view){
        Intent intent = new Intent(this, PackDetailActivity.class);

        intent.putExtra("user",user_id);
        intent.putExtra("id",book_id);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    // 결제하기 버튼
    public void onClicked2(View view){
        Intent intent = new Intent(this, PaymentActivity.class);

        intent.putExtra("user",user_id);
        intent.putExtra("packID",book_id);
        startActivity(intent);
    }

    // 결제 취소
    public void onClicked3(View view){

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("UPDATE CUS_USER SET PAY = null, book_id = null, book_date = null " +
                    "WHERE USERID = '"+user_id+"'");

            Toast.makeText(this,"결제가 취소되었습니다.",Toast.LENGTH_LONG).show();

            finish();
            overridePendingTransition(0, 0);
            Intent intent = getIntent();
            startActivity(intent);
            overridePendingTransition(0, 0);

        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void onClickedLogout(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }
    public void onClickecToQA(View view){
        Intent toQA = new Intent(this, ComplainOrQA.class);
        toQA.putExtra("username", username);

        startActivity(toQA);
    }

    public void onClickedToMain(View view){
        Intent toMain = new Intent(this, MainActivity.class);
        toMain.putExtra("username", username);
        toMain.putExtra("user", user_id);
        startActivity(toMain);
    }
}
