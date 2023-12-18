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

public class Login extends AppCompatActivity {
    private static final String DRIVER = "oracle.jdbc.OracleDriver";

    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";

    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
    }

    public void onClickBtn(View view) {
        Intent main = new Intent(this, Main.class);

        TextView id = findViewById(R.id.uname);
        TextView pw = findViewById(R.id.upw);


        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            StringBuffer stringBuffer = new StringBuffer();

            String ui = id.getText().toString();
            String up = pw.getText().toString();

            String check = "SELECT * FROM COMPANY_USER WHERE USERNAME = '"+ui+"'";
            ResultSet resultSet = statement.executeQuery(check);

            while (resultSet.next()) {
                stringBuffer.append(resultSet.getString(1));
                stringBuffer.append(resultSet.getString(3));
            }

            if(stringBuffer.toString().contains(up)) {
                Toast.makeText(this, "로그인완료!", Toast.LENGTH_LONG).show();
                main.putExtra("name", ui);
                startActivity(main);
            } else {
                Toast.makeText(this, "로그인실패 아이디와 비밀번호를 다시 확인해주세요", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

        }

    }
}