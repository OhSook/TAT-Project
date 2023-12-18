package com.example.tatproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tatproject.ForRecyclerView.PackRecyclerViewAdapter;
import com.example.tatproject.ForRecyclerView.PackageSingleItem;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PackageActivity extends AppCompatActivity {
    // 본인 DB계정으로 변경
    private static final String DRIVER = "oracle.jdbc.OracleDriver";

    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;
    private String username;
    private ArrayList<PackageSingleItem> list = new ArrayList<>();
    private ArrayList<PackageSingleItem> search_list = new ArrayList<>();
    private PackRecyclerViewAdapter adapter;
    private PackRecyclerViewAdapter searchAdapter;
    private PackRecyclerViewAdapter pva;
    private String userid;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        //보안정책
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        username = getIntent().getStringExtra("username");
        userid = getIntent().getStringExtra("user");

        TextView logedout = findViewById(R.id.logedout);
        TextView login = findViewById(R.id.logined);
        TextView logout = findViewById(R.id.logout8);

        if (username.equals("")){
            logedout.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            logout.setVisibility(View.INVISIBLE);
        }else {
            logedout.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
        }

        EditText editText;

        try{
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT PACK_ID, PACK_NAME, PRICE FROM CREATE_PACK ORDER BY PACK_ID");
            StringBuffer stringBuffer = new StringBuffer();

            while(resultSet.next()){
                stringBuffer.append(resultSet.getString(1) + ",");
                stringBuffer.append(resultSet.getString(2) + ",");
                stringBuffer.append(resultSet.getString(3) + ",");
            }

            int idx = 0;
            String[] stringArr = stringBuffer.toString().split(",");

            for (int i = 0; i < stringArr.length / 3; i++) {
                list.add(new PackageSingleItem(stringArr[idx + 1], stringArr[idx], stringArr[idx + 2],
                        R.drawable.bookmark_non_background, R.drawable.bookmark));
                idx += 3;
            }
            editText = findViewById(R.id.searchKeyword);

            // editText 리스터 작성
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String searchText = editText.getText().toString();
                    search_list.clear();

                    if(searchText.equals("")){
                        adapter.setItems(list);
                    }
                    else {
                        // 검색 단어를 포함하는지 확인
                        for (int a = 0; a < list.size(); a++) {
                            if (list.get(a).title.toLowerCase().contains(searchText.toLowerCase())) {
                                search_list.add(list.get(a));
                            }
                            adapter.setItems(search_list);
                        }
                    }
                }
            });

            // 리사이클러뷰, 어댑터 연결
            RecyclerView recyclerView = findViewById(R.id.recyclerView2);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new PackRecyclerViewAdapter(list, this, username);
            recyclerView.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClickedToMain(View view){
        Intent toMain = new Intent(this, MainActivity.class);
        toMain.putExtra("username", username);
        toMain.putExtra("user", userid);

        startActivity(toMain);
    }

    public void onClickedLogout(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }

    public void onClickedToMyPage(View view){
        Intent toMypage = new Intent(this, MyPageActivity.class);

        toMypage.putExtra("user", this.userid);
        toMypage.putExtra("username", username);
        startActivity(toMypage);
    }

    public void onClickedToLogin(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);

        startActivity(toLogin);
    }

    public void onClickedToQA(View view){
        if (this.username.equals("")){
            Snackbar.make(view, "로그인을 진행해주세요.", BaseTransientBottomBar.LENGTH_LONG).show();
        }else{
            Intent toQA = new Intent(this, ComplainOrQA.class);
            toQA.putExtra("username", username);

            startActivity(toQA);
        }

    }

    public void onClickedToBookmark(View view){
        if (this.username.equals("")){
            Snackbar.make(view, "로그인을 진행해주세요.", BaseTransientBottomBar.LENGTH_LONG).show();
        }else {
            Intent toBookMark = new Intent(this, BookMarkActivity.class);
            toBookMark.putExtra("username", username);
            toBookMark.putExtra("user", this.userid);

            startActivity(toBookMark);
        }
    }

    public void onClickedToOneByOne(View view){
        Intent toOneByOne = new Intent(this, OneByOne.class);
        toOneByOne.putExtra("username", username);
        toOneByOne.putExtra("user", this.userid);

        startActivity(toOneByOne);
    }
}
