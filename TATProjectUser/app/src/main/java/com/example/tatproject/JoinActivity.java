package com.example.tatproject;

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
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class JoinActivity extends AppCompatActivity {
    // 본인 DB계정으로 변경
    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //보안정책

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }

    public void onClicked(View view){

        TextView id_tv = findViewById(R.id.idText);
        TextView pass_tv = findViewById(R.id.passText);
        TextView name_tv = findViewById(R.id.nameText);
        TextView tell_tv = findViewById(R.id.tellText);
        TextView mail_tv = findViewById(R.id.mailText);


        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);

            Statement statement = connection.createStatement();
            Intent intent = new Intent(this,LoginActivity.class);

            String id = id_tv.getText().toString();
            String pass = pass_tv.getText().toString();
            String name = name_tv.getText().toString();
            String tell = tell_tv.getText().toString();
            String mail = mail_tv.getText().toString();

            ResultSet join = statement.executeQuery("INSERT INTO CUS_USER VALUES('"+name+"','"+id+"','"+pass+"',"+
                    0+",'bronze','"+tell+"','"+mail+"', NULL, NULL, NULL)"); // 회원가입 쿼리문

            Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다. 로그인을 해주세요!",Toast.LENGTH_LONG).show();
            startActivity(intent);


        }catch (SQLIntegrityConstraintViolationException e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}