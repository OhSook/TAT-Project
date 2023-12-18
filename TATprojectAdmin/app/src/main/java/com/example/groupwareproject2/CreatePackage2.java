package com.example.groupwareproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.util.Calendar;

public class CreatePackage2 extends AppCompatActivity {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";

    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";

    private Connection connection;

    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_package2);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        TextView tv = findViewById(R.id.textView6);
        Intent getUser = getIntent();

        username = getUser.getStringExtra("username");
        tv.setText(username + "님 접속중");

    }

    public void onClickedBtn(View view) {

        TextView name = findViewById(R.id.pname);
        TextView start = findViewById(R.id.pstart);
        TextView end = findViewById(R.id.pend);
        TextView price = findViewById(R.id.pprice);

        String un = name.getText().toString();
        String us = start.getText().toString();
        String ue = end.getText().toString();
        String up = price.getText().toString();

        if (un.equals("")||us.equals("")||ue.equals("")||up.equals("")){

            Toast.makeText(getApplicationContext(), "빈 값은 허용하지 않습니다.", Toast.LENGTH_LONG).show();

        } else {

            Intent create = new Intent(this, CreatePackage.class);
            create.putExtra("name", un);
            create.putExtra("start", us);
            create.putExtra("end",ue);
            create.putExtra("price",up);
            create.putExtra("username", username);
            startActivity(create);

        }

    }

    public void onClickStart(View view) {
        TextView start = findViewById(R.id.pstart);
        Calendar c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);
        int Month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                start.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, Year, Month, day);
        datePickerDialog.show();
    }

    public void onClickEnd(View view) {
        TextView end = findViewById(R.id.pend);
        Calendar c = Calendar.getInstance();
        int Year = c.get(Calendar.YEAR);
        int Month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                end.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, Year, Month, day);
        datePickerDialog.show();
    }
    public void onClickOut(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
    }
}