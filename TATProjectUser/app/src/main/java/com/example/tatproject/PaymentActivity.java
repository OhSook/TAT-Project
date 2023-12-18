package com.example.tatproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.xml.transform.Result;

public class PaymentActivity extends AppCompatActivity {

    // 본인 DB계정으로 변경
    private static final String DRIVER = "oracle.jdbc.OracleDriver";

    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;

    private Spinner sp1;
    String book_id;
    String user_id;
    String pack_price;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment);
        sp1 = findViewById(R.id.spinner);
        sp1.setVisibility(View.INVISIBLE);
        //보안정책

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        try {

            Intent getIntent = getIntent();
            book_id = getIntent.getStringExtra("packID");
            user_id = getIntent.getStringExtra("user");
            pack_price = getIntent.getStringExtra("pack_price");
            username = getIntent.getStringExtra("username");

            TextView packidtv = findViewById(R.id.packID);
            TextView price = findViewById(R.id.priceTV);

            // price.setText(user_id);
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            StringBuffer idBuffer = new StringBuffer();
            StringBuffer priceBuffer = new StringBuffer();

            ResultSet resultSet = statement.executeQuery("select pack_id, price from create_pack where pack_id = "+book_id);

            while(resultSet.next()){
                idBuffer.append(resultSet.getString(1));
                priceBuffer.append(resultSet.getString(2));
            }
            packidtv.setText(idBuffer.toString());
            price.setText(priceBuffer.toString());


        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickedCard(View view){
        sp1 = findViewById(R.id.spinner);
        sp1.setVisibility(View.VISIBLE);
        ImageView iv = findViewById(R.id.kakao);
        iv.setVisibility(View.VISIBLE);

        ImageView kb = findViewById(R.id.kb);
        TextView account = findViewById(R.id.num);

        kb.setVisibility(View.INVISIBLE);
        account.setVisibility(View.INVISIBLE);


        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sp1.getSelectedItem().toString().equals("KB국민카드"))
                    iv.setImageResource(R.drawable.kbcard);
                else if (sp1.getSelectedItem().toString().equals("신한카드"))
                    iv.setImageResource(R.drawable.shinhan);
                else if (sp1.getSelectedItem().toString().equals("롯데카드"))
                    iv.setImageResource(R.drawable.lottecard);
                else if (sp1.getSelectedItem().toString().equals("현대카드"))
                    iv.setImageResource(R.drawable.hyundaicard);
                else if (sp1.getSelectedItem().toString().equals("카카오카드"))
                    iv.setImageResource(R.drawable.kakao);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                iv.setImageResource(R.drawable.main);
            }
        });
    }

    public void onClickedLogout(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }

    public void onClickedWire(View view){
        sp1 = findViewById(R.id.spinner);
        ImageView iv = findViewById(R.id.kakao);

        iv.setVisibility(View.INVISIBLE);
        ImageView kb = findViewById(R.id.kb);
        TextView account = findViewById(R.id.num);

        sp1.setVisibility(View.INVISIBLE);
        kb.setVisibility(View.VISIBLE);
        account.setVisibility(View.VISIBLE);
    }

    public void onClickedToMyPage(View view){
        Intent toMypage = new Intent(this, MyPageActivity.class);

        toMypage.putExtra("user", this.user_id);
        toMypage.putExtra("username", this.username);
        startActivity(toMypage);
    }

    public void onClickedPay(View view){
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            StringBuffer idBuffer = new StringBuffer();

            ResultSet resultSet = statement.executeQuery("UPDATE CUS_USER SET PAY = '결제완료' " +
                    "WHERE USERID = '"+user_id+"'");

        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this,"결제가 완료되었습니다.",Toast.LENGTH_LONG).show();

    }
}