package com.example.tatproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatproject.ForRecyclerView.RecyclerViewAdapter;
import com.example.tatproject.ForRecyclerView.SingleItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ComplainOrQA extends AppCompatActivity {
    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;
    private String username;
    private String writer;
    private String title;
    private String writeDate;
    private RecyclerViewAdapter adapter;
    ArrayList<SingleItem> list = new ArrayList<>();
    private String userid = "";

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_complain);

        Intent fromMain = getIntent();
        this.username = fromMain.getStringExtra("username");
        this.userid = fromMain.getStringExtra("user");

        ProgressBar pr = findViewById(R.id.progress_bar);
        TextView quetisonCntText = findViewById(R.id.questionCnt);
        TextView answerPercent = findViewById(R.id.answerPercent);

        try{
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT TITLE, WRITE_DATE, USER_WRITER, ANSWER_CONTENT FROM QA");
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer answered = new StringBuffer();

            while(resultSet.next()){
                stringBuffer.append(resultSet.getString(1 )+ ",");
                stringBuffer.append(resultSet.getString(2 )+ ",");
                stringBuffer.append(resultSet.getString(3 )+ ",");
                answered.append(resultSet.getString(4) + ",");
            }

            int idx = 0;
            String[] stringArr = stringBuffer.toString().split(",");
            int questionCnt = stringArr.length / 3; // 질문개수
            String[] answerArr = answered.toString().split(",");
            float answerCnt = 0;
            for(String i : answerArr){
                if (! i.equals("null")){ // null이 아니다 = 답변이 있다
                    answerCnt += 1.0;
                }
            }

            int num = 1;
            for (int i = 0; i < stringArr.length / 3; i++) {
                list.add(new SingleItem(stringArr[idx], stringArr[idx + 1], stringArr[idx + 2], num++));
                idx += 3;
            }

            quetisonCntText.setText(String.valueOf(questionCnt));
            String condition = "";
            if ((answerCnt / questionCnt) * 100.0f >= 100){
                condition = String.valueOf((answerCnt / (float)questionCnt) * 100.0f).substring(0, 3);
            }else{
                condition = String.valueOf((answerCnt / (float)questionCnt) * 100.0f).substring(0, 2);
            }

            pr.setProgress(Integer.parseInt(condition));
            answerPercent.setText(Integer.parseInt(condition) + "%");
//          recyclerView, adapter연결
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new RecyclerViewAdapter(list, this);
            recyclerView.setAdapter(adapter);

            stringBuffer.setLength(0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClickedToWrite(View view){
        Intent toWrite = new Intent(this, WriteQA.class);
        toWrite.putExtra("username", username);

        startActivity(toWrite);
    }

    public void onClickedToBookMark(View view){
        Intent toBookMark = new Intent(this, BookMarkActivity.class);
        toBookMark.putExtra("username", username);
        toBookMark.putExtra("user", this.userid);

        startActivity(toBookMark);
    }

    public void onClickedToPackList(View view){
        Intent packList = new Intent(this, PackageActivity.class);
        packList.putExtra("username", username);
        packList.putExtra("user", this.userid);

        startActivity(packList);
    }

    public void onClickedToOneByOne(View view){
        Intent toOneByOne = new Intent(this, OneByOne.class);
        toOneByOne.putExtra("username", username);
        toOneByOne.putExtra("user", this.userid);

        startActivity(toOneByOne);
    }

    public void onClickedLogout(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }
}
