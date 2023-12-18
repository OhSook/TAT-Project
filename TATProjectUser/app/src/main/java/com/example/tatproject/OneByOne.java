package com.example.tatproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OneByOne extends AppCompatActivity {
    LinearLayout.LayoutParams whparams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, // width를 parent에 맞게 설정
            ViewGroup.LayoutParams.WRAP_CONTENT  // height는 필요에 따라 조절
    );

    private String username = "";
    private String userid = "";

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_one_by_one);

        username = getIntent().getStringExtra("username");
        userid = getIntent().getStringExtra("user");

        TextView logedout = findViewById(R.id.logedout2);
        TextView login = findViewById(R.id.logined2);
        TextView logout = findViewById(R.id.logout3);

        if (username.equals("")){
            logedout.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            logout.setVisibility(View.INVISIBLE);
        }else {
            logedout.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
        }

        LinearLayout chat1 = findViewById(R.id.chat1);
        LinearLayout chat2 = findViewById(R.id.chat2);
        LinearLayout chat3 = findViewById(R.id.chat3);
        LinearLayout chat4 = findViewById(R.id.chat4);

        AlphaAnimation fadeIn1 = new AlphaAnimation(0.0f, 1.0f);
        fadeIn1.setDuration(1000);

        AlphaAnimation fadeIn2 = new AlphaAnimation(0.0f, 1.0f);
        fadeIn2.setStartOffset(1000);
        fadeIn2.setDuration(1000);

        AlphaAnimation fadeIn3 = new AlphaAnimation(0.0f, 1.0f);
        fadeIn3.setStartOffset(2000);
        fadeIn3.setDuration(1000);

        AlphaAnimation fadeIn4 = new AlphaAnimation(0.0f, 1.0f);
        fadeIn4.setStartOffset(3000);
        fadeIn4.setDuration(1000);

        chat1.setAnimation(fadeIn1);
        chat2.setAnimation(fadeIn2);
        chat3.setAnimation(fadeIn3);
        chat4.setAnimation(fadeIn4);
    }

    public void onClickedRefundSchedule(View view){
        int sizeInDp = 59;
        int sizeWH = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                sizeInDp,
                getResources().getDisplayMetrics()
        );

        TextView refund = new TextView(this);
        TextView refundContents = new TextView(this);
        ImageView user = new ImageView(this);
        ImageView admin = new ImageView(this);
        LinearLayout horizontal = new LinearLayout(this);
        LinearLayout horizontal2 = new LinearLayout(this);
        LinearLayout layout = findViewById(R.id.chatWrapper);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/crab.ttf");

        // TextView에 Margin-Right 옵션을 주기 위함
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 8, 0);

        // TextView 관련 설정
        refund.setText("\n환불 일정 확인하기");
//        refund.setBackgroundColor(R.color.main_theme);
        refund.setTypeface(typeface);
        refund.setTextSize(20);
        refund.setLayoutParams(params);
        refund.setLayoutParams(whparams);

        // ImageView관련 설정
        user.setImageResource(R.drawable.user);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(sizeWH, sizeWH);
        user.setLayoutParams(layoutParams);

        AlphaAnimation fadeIn1 = new AlphaAnimation(0.0f, 1.0f);
//        fadeIn1.setDuration(1000); // 최초 시작
        // LinearLayout 관련 설정
        horizontal.setOrientation(LinearLayout.HORIZONTAL);
        horizontal.addView(refund);
        horizontal.addView(user);

        // animation 설정
//        horizontal.setAnimation(fadeIn1);

        layout.addView(horizontal);

        // content 메세지 설정
        AlphaAnimation fadeIn2 = new AlphaAnimation(0.0f, 1.0f);
        fadeIn2.setStartOffset(1000);
        fadeIn2.setDuration(1000); // 최초 시작 이후 시작

        admin.setImageResource(R.drawable.administrator);
        admin.setLayoutParams(layoutParams);

        refundContents.setText("\n환불 일정 안내해 드립니다. " +
                "\n환불 요청이 접수된 후 최대 5 영업일\n" +
                "이내에 환불이 처리됩니다. 주말 또는 공휴일에는 " +
                "처리가 지연될 수 있으며, 추가적인 문의 사항을 직원과 " +
                "직접 컨택하시려면 support@example.com으로 이메일을 " +
                "보내주시거나 123-456-7890으로 전화해 주세요.");

        refundContents.setTypeface(typeface);
        refundContents.setTextSize(20);

        horizontal2.addView(admin);
        horizontal2.addView(refundContents);
        horizontal2.setAnimation(fadeIn2);

        layout.setAnimation(fadeIn2);
        layout.addView(horizontal2);
    }

    public void onClickedConnect(View view){
        int sizeInDp = 59;
        int sizeWH = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                sizeInDp,
                getResources().getDisplayMetrics()
        );

        TextView refund = new TextView(this);
        TextView refundContents = new TextView(this);
        ImageView user = new ImageView(this);
        ImageView admin = new ImageView(this);
        LinearLayout horizontal = new LinearLayout(this);
        LinearLayout horizontal2 = new LinearLayout(this);
        LinearLayout layout = findViewById(R.id.chatWrapper);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/crab.ttf");

        // TextView에 Margin-Right 옵션을 주기 위함
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 8, 0);

        // TextView 관련 설정
        refund.setText("\n상담원 연결");
//        refund.setBackgroundColor(R.color.main_theme);
        refund.setTypeface(typeface);
        refund.setTextSize(20);
        refund.setLayoutParams(params);
        refund.setLayoutParams(whparams);

        // ImageView관련 설정
        user.setImageResource(R.drawable.user);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(sizeWH, sizeWH);
        user.setLayoutParams(layoutParams);

        AlphaAnimation fadeIn1 = new AlphaAnimation(0.0f, 1.0f);
        fadeIn1.setDuration(1000); // 최초 시작
        // LinearLayout 관련 설정
        horizontal.setOrientation(LinearLayout.HORIZONTAL);
        horizontal.addView(refund);
        horizontal.addView(user);

        // animation 설정
        horizontal.setAnimation(fadeIn1);

        layout.addView(horizontal);

        // content 메세지 설정
        AlphaAnimation fadeIn2 = new AlphaAnimation(0.0f, 1.0f);
        fadeIn2.setStartOffset(1000);
        fadeIn2.setDuration(1000); // 최초 시작 이후 시작

        admin.setImageResource(R.drawable.administrator);
        admin.setLayoutParams(layoutParams);

        refundContents.setText("상담원과 연결을 시작합니다.");

        refundContents.setTypeface(typeface);
        refundContents.setTextSize(20);

        horizontal2.addView(admin);
        horizontal2.addView(refundContents);
        horizontal2.setAnimation(fadeIn2);

        layout.setAnimation(fadeIn2);
        layout.addView(horizontal2);
    }

    public void onClickedToLogin(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }

    public void onClickedToMyPage(View view){
        Intent toMypage = new Intent(this, MyPageActivity.class);

        toMypage.putExtra("user", this.userid);
        toMypage.putExtra("username", username);
        startActivity(toMypage);
    }

    public void onClickedLogout(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }

    public void onClickedToMain(View view){
        Intent toMain = new Intent(this, MainActivity.class);
        toMain.putExtra("username", username);
        toMain.putExtra("user", userid);
        startActivity(toMain);
    }
}
