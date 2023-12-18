package com.example.tatproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tatproject.ForRecyclerView.BookRecyclerViewAdapter;
import com.example.tatproject.ForRecyclerView.BookSingleItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BookMarkActivity extends AppCompatActivity {
    private static final String DRIVER = "oracle.jdbc.OracleDriver";

    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;
    private String username;
    private ArrayList<BookSingleItem> list = new ArrayList<>();
    private BookRecyclerViewAdapter adapter;
    private String userid;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_bookmark);

        Intent fromMain = getIntent();
        this.username = fromMain.getStringExtra("username");
        this.userid = fromMain.getStringExtra("user");

        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT BOOK_ID FROM BOOK_TABLE WHERE USERNAME='"+username+"'");
            StringBuffer result = new StringBuffer();

            while(resultSet.next()){
                result.append(resultSet.getString(1) + ",");
            }

            String[] bookNumArr = result.toString().split(",");
            for (int i = 0; i < bookNumArr.length; i++) {
                ResultSet resultSet1 = statement.executeQuery("SELECT PRICE, TO_CHAR(JOURNEY_START, 'YYYY-MM-DD'), " +
                        "TO_CHAR(JOURNEY_END, 'YYYY-MM-DD'), PACK_NAME FROM CREATE_PACK WHERE PACK_ID="+Integer.parseInt(bookNumArr[i]));
                StringBuffer resultEach = new StringBuffer();
                while (resultSet1.next()){
                    resultEach.append(resultSet1.getString(1) + ",");
                    resultEach.append(resultSet1.getString(2) + ",");
                    resultEach.append(resultSet1.getString(3) + ",");
                    resultEach.append(resultSet1.getString(4) + ",");
                }

                String[] eachLine = resultEach.toString().split(",");
                list.add(new BookSingleItem(eachLine[3], eachLine[1] + "~" + eachLine[2], eachLine[0], Integer.parseInt(bookNumArr[i])));

                resultEach.setLength(0);

            }
            RecyclerView recyclerView = findViewById(R.id.recyclerViewBook);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new BookRecyclerViewAdapter(list, this, username);
            recyclerView.setAdapter(adapter);

            result.setLength(0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClickedToQA(View view){
        Intent toQA = new Intent(this, ComplainOrQA.class);
        toQA.putExtra("username", username);
        toQA.putExtra("user", userid);

        startActivity(toQA);

    }

    public void onClickedToPackList(View view){
        Intent packList = new Intent(this, PackageActivity.class);
        packList.putExtra("username", username);
        packList.putExtra("user", this.userid);

        startActivity(packList);
    }

    public void onClickedToOneByOne(View view){
        Intent toOneByOne = new Intent(this, OneByOne.class);
        toOneByOne.putExtra("username", username);
        toOneByOne.putExtra("user", this.userid);

        startActivity(toOneByOne);
    }

    public void onClickedLogout(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }
}
