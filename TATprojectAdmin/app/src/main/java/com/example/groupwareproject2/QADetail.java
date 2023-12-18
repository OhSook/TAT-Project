package com.example.groupwareproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView textAnswer;
    String writer;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qadetail);

        //보안정책

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        String title = getIntent().getStringExtra("title");

        TextView textTitle = findViewById(R.id.QATitle);
        TextView textWriter = findViewById(R.id.QAWriter);
        TextView textWriteDate = findViewById(R.id.QAWriteDate);
        TextView textContents = findViewById(R.id.QAMainContents);
        textAnswer = findViewById(R.id.QAAnswered);
        Spinner spin = findViewById(R.id.spinner);


        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT TITLE, USER_WRITER, TO_CHAR(WRITE_DATE," +
                    " 'YYYY-MM-DD'), CONTENT, ANSWER_CONTENT FROM QA WHERE TITLE='" + title + "'");
            StringBuffer stringBuffer = new StringBuffer(); // 질문 가져오기

            while (resultSet.next()) {
                stringBuffer.append(resultSet.getString(1) + ",");
                stringBuffer.append(resultSet.getString(2) + ",");
                stringBuffer.append(resultSet.getString(3) + ",");
                stringBuffer.append(resultSet.getString(4) + ",");
            }

            // spinner에 직원 이름 넣기
            StringBuffer sb = new StringBuffer();


            StringBuffer stringBuffer2 = new StringBuffer(); // 답변 작성 직원 버퍼
            ResultSet resultSet2 = statement.executeQuery("SELECT USERNAME FROM COMPANY_USER ");

            while(resultSet2.next()){
                stringBuffer2.append(resultSet2.getString(1) + ",") ;
            }
            String[] employeeArr = stringBuffer2.toString().split(",");
            connection.close();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,employeeArr);

            spin.setAdapter(adapter);
            writer = spin.getSelectedItem().toString(); // 작성자 가져오기

            String[] contents = stringBuffer.toString().split(",");
            textTitle.setText(contents[0]);
            textWriter.setText("작성자 :" + contents[1]);
            textWriteDate.setText(" " + contents[2]);
            textContents.setText(contents[3]);

            stringBuffer.setLength(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onClickAns(View view){

        try{
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Intent intent = new Intent(this, QnAActivity.class);
            Intent getIntent = getIntent();
            String Qtitle = getIntent.getStringExtra("title");
            answer = "\n"+textAnswer.getText().toString();

            Statement statement = connection.createStatement();
            ResultSet resultSet3 = statement.executeQuery("UPDATE QA SET ANSWER_CONTENT = '"+answer+"'," +
                    "COMP_WRITER = '"+writer+"', ANSWER_WRITE_DATE = TO_DATE(TO_CHAR(SYSDATE, 'YYYYMMDD'), 'YYYYMMDD') WHERE TITLE = '"+Qtitle+"'");
            Toast.makeText(this,"등록이 완료되었습니다.",Toast.LENGTH_LONG).show();
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
        }
    }
}