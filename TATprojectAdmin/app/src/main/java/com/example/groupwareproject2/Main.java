package com.example.groupwareproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends AppCompatActivity {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";

    private Connection connection;
    private String username="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        TextView login = findViewById(R.id.textView18);
        Intent getUser = getIntent();

        username = getUser.getStringExtra("name");

        login.setText(username + "님 접속중");

        }

        public void onClickOut(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
        }

        public void onClickCreate(View view) {
        Intent create = new Intent(this,CreatePackage2.class);
        create.putExtra("username", username);
        startActivity(create);
        }

        public void onClickMy(View view) {
        Intent info = new Intent(this, Myinfo.class);
        info.putExtra("name", username);
        startActivity(info);
        }

        public void onClickQA(View view){
        Intent Answer = new Intent(this, AnswerListAcitivity.class);
        Answer.putExtra("user_name", username);
        startActivity(Answer);
        }

    }
