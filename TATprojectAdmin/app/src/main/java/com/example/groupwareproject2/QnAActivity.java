package com.example.groupwareproject2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class QnAActivity extends AppCompatActivity {
    // 본인 DB계정으로 변경
    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;

    private String username;
    private String writer;
    private String title;
    private String writeDate;
    private RecyclerViewAdapterCom adapter;
    ArrayList<SingleItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_aactivity);

        //보안정책

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        Intent getIntent = getIntent();
        username = getIntent.getStringExtra("user_name");

        try{
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT TITLE, WRITE_DATE, USER_WRITER, ANSWER_CONTENT FROM QA WHERE ANSWER_CONTENT IS NOT NULL");

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
            int questionCnt = stringArr.length / 3;
            String[] answerArr = answered.toString().split(",");
            int answerCnt = 0;
            for(String i : answerArr){
                if (i.equals(null)){
                    answerCnt += 1;
                }
            }

            int num = 1;
            for (int i = 0; i < stringArr.length / 3; i++) {
                list.add(new SingleItem(stringArr[idx], stringArr[idx + 1], stringArr[idx + 2], num++));
                idx += 3;
            }

            //          recyclerView, adapter연결
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new RecyclerViewAdapterCom(list, this);
            recyclerView.setAdapter(adapter);

        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClicked(View view){
        Intent intent = new Intent(this,AnswerListAcitivity.class);
        startActivity(intent);
    }
}