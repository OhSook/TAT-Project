package com.example.tatproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class QADetail extends AppCompatActivity {
    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;
    private String username;

    private static int goodCnt;
    private static int badCnt;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_qa_detail);

        String title = getIntent().getStringExtra("title");

        TextView textTitle = findViewById(R.id.QATitle);
        TextView textWriter = findViewById(R.id.QAWriter);
        TextView textWriteDate = findViewById(R.id.QAWriteDate);
        TextView textContents = findViewById(R.id.QAMainContents);
        TextView textAnswer = findViewById(R.id.QAAnswered);

        try{
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT TITLE, USER_WRITER, TO_CHAR(WRITE_DATE," +
                    " 'YYYY-MM-DD'), CONTENT, ANSWER_CONTENT FROM QA WHERE TITLE='" + title +"'");
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer answer = new StringBuffer();

            while(resultSet.next()){
                stringBuffer.append(resultSet.getString(1) + ",");
                stringBuffer.append(resultSet.getString(2) + ",");
                stringBuffer.append(resultSet.getString(3) + ",");
                stringBuffer.append(resultSet.getString(4) + ",");
                answer.append(resultSet.getString(5));
            }

            String[] contents = stringBuffer.toString().split(",");
            textTitle.setText(contents[0]);
            textWriter.setText("작성자 :" + contents[1]);
            textWriteDate.setText(" " + contents[2]);
            textContents.setText(contents[3]);

            if (answer.toString().equals("null")){
                textAnswer.setText("아직 등록된 답변이 없습니다.");
            }else textAnswer.setText(answer.toString());

            stringBuffer.setLength(0);
            answer.setLength(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onClickedGood(View view){
        TextView text = findViewById(R.id.goodCnt);

        goodCnt = Integer.parseInt(text.getText().toString());
        goodCnt++;

        text.setText(String.valueOf(goodCnt));
    }

    public void onClickedBad(View view){
        TextView text = findViewById(R.id.badCnt);

        badCnt = Integer.parseInt(text.getText().toString());
        badCnt++;

        text.setText(String.valueOf(badCnt));
    }

    public void onClickedLogout(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }
}
