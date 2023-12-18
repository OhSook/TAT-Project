package com.example.groupwareproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EditInfo extends AppCompatActivity {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";

    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";

    private Connection connection;

    private String username = "";

    private String username2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        TextView tv = findViewById(R.id.textView34);
        Intent getUser = getIntent();

        username = getUser.getStringExtra("name");
        tv.setText(username + "님 접속중");
    }

    public void onClickBtn(View view){

        Intent info = new Intent(this, Main.class);
        TextView name = findViewById(R.id.username);
        TextView id = findViewById(R.id.uid);
        TextView mail = findViewById(R.id.usermail);
        Spinner rank = findViewById(R.id.spinner);
        Spinner depart = findViewById(R.id.spinner3);

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();

            String[] columns = {"USERNAME", "USERID", "RANK", "DEPT", "EMAIL"};

            String un = name.getText().toString();
            String ui = id.getText().toString();
            String um = mail.getText().toString();
            String ur = rank.getSelectedItem().toString();
            String ud = depart.getSelectedItem().toString();

            String[] fieldValues = {un, ui, ur, ud, um};

            String query = "UPDATE COMPANY_USER SET ";
            for (int i=0; i < columns.length; i++){
                if (! fieldValues[i].equals("")){
                    query += (i < columns.length - 1) ? columns[i]+"='"+fieldValues[i]+"'," :
                            columns[i]+"='"+fieldValues[i]+"'";
                }
            }

            if (query.endsWith(",")){
                query = query.substring(0, query.lastIndexOf(","));
            }

            query += " WHERE USERNAME='" + username + "'";

            statement.executeQuery(query);

        if (un.equals("")) {
            username2 = username;
        } else{
            username2 = un;
        }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "정보수정이 완료되었습니다.", Toast.LENGTH_LONG).show();
        info.putExtra("name", username2);
        startActivity(info);
    }

    public void onClickOut(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
    }

}

