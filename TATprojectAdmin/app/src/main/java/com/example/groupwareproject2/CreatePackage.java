package com.example.groupwareproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.nio.IntBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreatePackage extends AppCompatActivity {

    private static final String DRIVER = "oracle.jdbc.OracleDriver";

    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";

    private Connection connection;

    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_package);

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        Intent intent = getIntent();
        TextView tv = findViewById(R.id.textView31);
        TextView writer = findViewById(R.id.pwriter);

        username = intent.getStringExtra("username");

        tv.setText(username + "님 접속중");

        writer.setText(username);
    }
    public void onClickedBtn(View view){

        Intent gomain = new Intent(this,Main.class);
        Intent intent = getIntent();
        TextView main = findViewById(R.id.pmain);
        TextView url = findViewById(R.id.purl);

        try{
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);

            int a;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(PACK_ID) FROM CREATE_PACK");

            StringBuffer stringBuffer = new StringBuffer();
            while(resultSet.next()){
                stringBuffer.append(resultSet.getString(1));
            }
            a = Integer.parseInt(stringBuffer.toString());
            a++;

            String um = main.getText().toString();
            String uu = url.getText().toString();
            String uw = intent.getStringExtra("username");
            String name = intent.getStringExtra("name");
            String start = intent.getStringExtra("start");
            String end = intent.getStringExtra("end");
            String price = intent.getStringExtra("price");
            String check = "INSERT INTO CREATE_PACK VALUES('"+name+"', TO_DATE('"+start+"', 'YYYY/MM/DD'), " +
                    "'"+um+"', '"+a+"', '"+price+"', '"+uu+"', '"+uw+"', TO_DATE('"+end+"', 'YYYY/MM/DD'))";
            ResultSet resultSet2 = statement.executeQuery(check);

            if(resultSet2.next()) {
                Toast.makeText(this, "패키지 등록완료!", Toast.LENGTH_LONG).show();
                gomain.putExtra("username", uw);
                startActivity(gomain);

            } else {
                Toast.makeText(this, "목록을 다 작성해주세요", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e){
            main.setText(e.toString());
        }
    }

    public void onClickOut(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();
    }
}