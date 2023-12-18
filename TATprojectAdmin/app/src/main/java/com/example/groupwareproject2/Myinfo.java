package com.example.groupwareproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Myinfo extends AppCompatActivity {
    private static final String DRIVER = "oracle.jdbc.OracleDriver";

    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";

    private Connection connection;

    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        TextView login = findViewById(R.id.textView14);
        Intent intent = new Intent(this, Main.class);
        Intent getUser = getIntent();

        username = getUser.getStringExtra("name");
        login.setText(username + "님 접속중");

        TextView name = findViewById(R.id.name);
        TextView id = findViewById(R.id.userid2);
        TextView rank = findViewById(R.id.rank);
        TextView dept = findViewById(R.id.dept);
        TextView mail = findViewById(R.id.mail);
        TextView num = findViewById(R.id.num);

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            StringBuffer stringBuffer = new StringBuffer();
            String un = username;
            String check = "SELECT * FROM COMPANY_USER WHERE USERNAME = '" +un+ "'";
            ResultSet resultSet = statement.executeQuery(check);

            if(resultSet.next()) {
                name.setText(resultSet.getString(1));
                id.setText(resultSet.getString(2));
                rank.setText(resultSet.getString(5));
                dept.setText(resultSet.getString(6));
                mail.setText(resultSet.getString(7));
                num.setText(resultSet.getString(8));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickOut(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
    }

    public void onClickEdit(View view){
        Intent intent = new Intent(this, EditInfo.class);
        intent.putExtra("name", username);
        startActivity(intent);

    }
}