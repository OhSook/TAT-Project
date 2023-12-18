package com.example.tatproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.log4j.chainsaw.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
    // 본인 DB계정으로 변경
    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //보안정책
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

    }

    public void onClickedLogin(View view){

        TextView id_tv = findViewById(R.id.idText);
        TextView pass_tv = findViewById(R.id.passText);

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);

            Statement statement = connection.createStatement();
            StringBuffer sb = new StringBuffer();
            StringBuffer sb2 = new StringBuffer();
            StringBuffer sb3 = new StringBuffer();
            StringBuffer sb4 = new StringBuffer();

            String id = id_tv.getText().toString();
            String ps = pass_tv.getText().toString();

            ResultSet idResult = statement.executeQuery("SELECT * FROM CUS_USER WHERE USERID = '"+id
                    +"'");

            while(idResult.next()){
                sb.append(idResult.getString(1)); // 이름
                sb2.append(idResult.getString(2)); // id
                sb3.append(idResult.getString(3)); // pass
            }


            if(sb2.toString().equals(id) && sb3.toString().equals(ps)){
                Intent intent = new Intent(this, MainActivity.class);

                intent.putExtra("user",sb2.toString());
                intent.putExtra("username", sb.toString());
                Toast.makeText(this,sb.toString()+"님 로그인이 완료되었습니다!", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }else
                Toast.makeText(this,"ID혹은 비밀번호를 확인하세요", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
//            id_tv.setText(e.toString());
        }
    }

    public void onClicked2(View view){
        Intent intent = new Intent(this,JoinActivity.class);
        startActivity(intent);
    }
}
