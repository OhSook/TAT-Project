package com.example.tatproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WriteQA extends AppCompatActivity {
    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;
    private String username;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_write_question);

        Intent fromComp = getIntent();
        username = fromComp.getStringExtra("username");
    }

    public void onClickedWrite(View view){
        Spinner categori = findViewById(R.id.spinner);
        EditText title = findViewById(R.id.textTitle);
        TextView main = findViewById(R.id.textMainContents);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String textTitle = title.getText().toString();
        String mainComtents = main.getText().toString();
        String cat = categori.getSelectedItem().toString();
        String time = now.format(dateTimeFormatter);

        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();

            statement.executeQuery("INSERT INTO QA VALUES('"+ mainComtents + "', '" + textTitle +
                    "', '" + time + "', '" + username + "', null, null, '" + cat + "', null)");

            Snackbar.make(view, "작성이 완료되었습니다.", BaseTransientBottomBar.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClickedLogout(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }
}
