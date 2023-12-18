package com.example.groupwareproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ComAnsDetailActivity extends AppCompatActivity {
    private static final String DRIVER = "oracle.jdbc.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@IPv4Address:port:xe";
    private static final String USERNAME = "dbName";
    private static final String PASSWORD = "password";
    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_ans_detail);

        //보안정책

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        String title = getIntent().getStringExtra("title");

        TextView textTitle = findViewById(R.id.QATitle);
        TextView textWriter = findViewById(R.id.QAWriter);
        TextView textWriteDate = findViewById(R.id.QAWriteDate);
        TextView textContents = findViewById(R.id.QAMainContents);
        TextView textAnswer = findViewById(R.id.QAAnswered);
        TextView textAnswerDate = findViewById(R.id.Adate);
        TextView textAnswerWriter = findViewById(R.id.Awriter);

        try{
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT TITLE, USER_WRITER, TO_CHAR(WRITE_DATE," +
                    " 'YYYY-MM-DD'), CONTENT, ANSWER_CONTENT,TO_CHAR(ANSWER_WRITE_DATE,'YYYY-MM-DD'),COMP_WRITER FROM QA WHERE TITLE='" + title +"'");
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer answer = new StringBuffer();

            while(resultSet.next()){
                stringBuffer.append(resultSet.getString(1) + ",");
                stringBuffer.append(resultSet.getString(2) + ",");
                stringBuffer.append(resultSet.getString(3) + ",");
                stringBuffer.append(resultSet.getString(4) + ",");
                stringBuffer.append(resultSet.getString(5)+",");
                stringBuffer.append(resultSet.getString(6)+",");
                stringBuffer.append(resultSet.getString(7)+",");
            }

            String[] contents = stringBuffer.toString().split(",");
            textTitle.setText(contents[0]);
            textWriter.setText("작성자 :" + contents[1]);
            textWriteDate.setText(" " + contents[2]);
            textContents.setText("\n"+contents[3]);
            textAnswer.setText("\n"+contents[4]);
            textAnswerDate.setText("답변 작성일 :"+ contents[5]);
            textAnswerWriter.setText("답변자 :"+contents[6]);

            stringBuffer.setLength(0);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}