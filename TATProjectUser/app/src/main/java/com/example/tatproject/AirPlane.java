package com.example.tatproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

public class AirPlane extends AppCompatActivity {
    private String username = "";
    Handler handler;
    String testText = "";
    private String x;
    private String y;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airplane);

        TextView isLogined = findViewById(R.id.logedout);

        Intent fromMain = getIntent();
        username = fromMain.getStringExtra("username");

        TextView test = findViewById(R.id.textView);

        if (username.equals("")) isLogined.setText("로그인/회원가입");
        else isLogined.setText("마이페이지");

//        String str = "";
//        try {
//            InputStream r = getBaseContext().getResources().getAssets().open("airplane_schedule.CSV");
//
//            while(true){
//                int data = r.read();
//
//                if (data == -1) break;
//                str += (char)data;
//            }
//
//            test.setText(str);
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    // contents_no = 2 : 국내선, contents_no = 1 : 국제선
                    String URL = "https://www.airport.co.kr/gimpo/cms/frCon/index.do?MENU_ID=1060&CONTENTS_NO=2";
                    Document doc = Jsoup.connect(URL).get();
                    Elements table = doc.select("table > tr");
//                    Elements trs = table.getElementById("inTbody").getElementsByTag("tr");
//                    Element trs = tbody.getAllElements().get(0);
//                    Element trs = table.get(0).getElementById("outTbody");

                    Bundle bundle = new Bundle();
                    bundle.putString("Test td", table.toString()); // tables.toString()

                    Message msg = handler.obtainMessage();
                    msg.setData(bundle);
                    handler.sendMessage(msg);

//                    boolean isEmpty = trs.isEmpty();
//                    Log.d("Tag", "isNull? : " + isEmpty);
////
//                    if (!isEmpty){
//                        StringBuffer stringBuffer = new StringBuffer();
//                        for (Element ele : trs) {
//                            Elements tds = ele.getElementsByTag("td");
//                            for (Element td : tds) {
//                                stringBuffer.append(td.text());
//                            }
//                        }
//                        Bundle bundle = new Bundle();
//                        bundle.putString("Test td", stringBuffer.toString());
//
//                        // Send the message to the main thread
//                        Message msg = handler.obtainMessage();
//                        msg.setData(bundle);
//                        handler.sendMessage(msg);
//                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();

        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg){
                Bundle bundle = msg.getData();
                if (Objects.requireNonNull(bundle.getString("Test td")).length() != 0){
                    Toast.makeText(getApplicationContext(), "운항 정보를 불러오는 중입니다..", Toast.LENGTH_SHORT).show();
                    test.setText(bundle.getString("Test td"));
                }else test.setText("오류가 발생했거나 요청한 자료를 찾을 수 없습니다.");
            }
        };
    }
}
