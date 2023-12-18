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
import java.sql.Statement;

public class BookActivity extends AppCompatActivity {

    // 본인 DB계정으로 변경
    private static final String DRIVER = "oracle.jdbc.OracleDriver";

    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;
    String book_id;
    String pack_price;
    String user_id;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        //보안정책
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        TextView start = findViewById(R.id.startDate);
        TextView end = findViewById(R.id.endDate);
        TextView title = findViewById(R.id.packName);
        TextView name = findViewById(R.id.userName);
        TextView tell = findViewById(R.id.userTell);
        TextView mail = findViewById(R.id.userEmail);
        TextView price = findViewById(R.id.price);

        try{

            Class.forName(DRIVER); // DRIVER
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();

            Intent getIntent = getIntent();
            user_id = getIntent.getStringExtra("user");
            username = getIntent.getStringExtra("username");

            String user_query = "SELECT USERNAME,PHONE,EMAIL,BOOK_ID FROM CUS_USER WHERE USERID = '"+ user_id+"'";

            ResultSet resultSet = statement.executeQuery(user_query);
            StringBuffer nameBuffer = new StringBuffer();
            StringBuffer phoneBuffer = new StringBuffer();
            StringBuffer emailBuffer = new StringBuffer();
            StringBuffer idBuffer = new StringBuffer();
            StringBuffer packnameBuffer = new StringBuffer();
            StringBuffer startBuffer = new StringBuffer();
            StringBuffer endBuffer = new StringBuffer();
            StringBuffer priceBuffer = new StringBuffer();

            while(resultSet.next()){
                nameBuffer.append(resultSet.getString(1));
                phoneBuffer.append(resultSet.getString(2));
                emailBuffer.append(resultSet.getString(3));
                idBuffer.append(resultSet.getString(4));
            }

            name.setText(nameBuffer.toString());
            tell.setText(phoneBuffer.toString());
            mail.setText(emailBuffer.toString());
            book_id = idBuffer.toString();
            String pack_query = "SELECT PACK_NAME,to_char(JOURNEY_START,'yyyy-mm-dd'),PRICE,to_char(JOURNEY_END,'yyyy-mm-dd') FROM CREATE_PACK WHERE PACK_ID = "+book_id;
            ResultSet resultSet2 = statement.executeQuery(pack_query);

            while(resultSet2.next()){
                packnameBuffer.append(resultSet2.getString(1));
                startBuffer.append(resultSet2.getString(2));
                priceBuffer.append(resultSet2.getString(3));
                endBuffer.append(resultSet2.getString(4));
            }

            title.setText(packnameBuffer.toString());
            start.setText(startBuffer.toString());
            end.setText(endBuffer.toString());

            pack_price = priceBuffer.toString();
            price.setText(pack_price+"￦");


        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickedToMyPage(View view){
        Intent toMypage = new Intent(this, MyPageActivity.class);

        toMypage.putExtra("user", this.user_id);
        toMypage.putExtra("username", username);
        startActivity(toMypage);
    }
    public void onClickedToPayment(View view){
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra("price",pack_price);
        intent.putExtra("packID",book_id);
        intent.putExtra("user",user_id);
        intent.putExtra("user", this.user_id);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    public void onClickedLogout(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }

    public void onClickedToMain(View view){
        Intent toMain = new Intent(this, MainActivity.class);
        toMain.putExtra("username", username);
        toMain.putExtra("user", user_id);
        startActivity(toMain);
    }
}