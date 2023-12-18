package com.example.tatproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 4;
    private CircleIndicator3 mIndicator;
    private String username = "";
    private String userid = "";
    private static int renderCnt = 0;

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (! (renderCnt == 0)){
            Intent fromLogin = getIntent();

            this.userid = fromLogin.getStringExtra("user");
            this.username = fromLogin.getStringExtra("username");
        }

        TextView logedout = findViewById(R.id.logedout);
        TextView login = findViewById(R.id.logined);
        TextView logout = findViewById(R.id.logout);

        if (username.equals("")){
            logedout.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            logout.setVisibility(View.INVISIBLE);
        }else {
            logedout.setVisibility(View.INVISIBLE);
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
        }

//      ViewPager2
        mPager = findViewById(R.id.viewpager);
        // ViewPager와 Fragment를 연결할 Adapter
        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);

        // Fragment가 이동할 때 위치를 표시하는 Indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page, 0);

        // ViewPager에 수평 방향을 세팅한다.
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        // 내가 총 준비한 이미지 장수의 배수로 설정해야함
        // 4 -> 1000, 6 -> 1002
        mPager.setCurrentItem(1000);
        mPager.setOffscreenPageLimit(4);
        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0){
                    mPager.setCurrentItem(position);
                }
            }
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position%num_page);
            }
        });
        this.renderCnt++;
    }

    public void onClickedLogin(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }

    public void onClickedComplain(View view){
        if (username.equals("")){
            Snackbar.make(view, "로그인을 먼저 진행해주세요.", Snackbar.LENGTH_LONG).show();
        }else{
            Intent toComplain = new Intent(this, ComplainOrQA.class);
            toComplain.putExtra("username", this.username);
            toComplain.putExtra("user", this.userid);

            startActivity(toComplain);
        }
    }

    public void onClickedPackList(View view){
        Intent toPackList = new Intent(this, PackageActivity.class);

        toPackList.putExtra("username", this.username);
        toPackList.putExtra("user", this.userid);
        startActivity(toPackList);
    }

    public void onClickedAirplane(View view){
        Intent toAirplane = new Intent(this, AirPlane.class);

        toAirplane.putExtra("username",this.username);
        startActivity(toAirplane);
    }

    public void onClickedToMyPage(View view){
        Intent toMypage = new Intent(this, MyPageActivity.class);

        toMypage.putExtra("user", this.userid);
        toMypage.putExtra("username", username);
        startActivity(toMypage);
    }

    public void onClickedToBookmark(View view){
        if (username.equals("")){
            Snackbar.make(view, "로그인을 먼저 진행해주세요.", Snackbar.LENGTH_LONG).show();
        }else{
            Intent toBookmark = new Intent(this, BookMarkActivity.class);
            toBookmark.putExtra("username", username);
            toBookmark.putExtra("user", userid);

            startActivity(toBookmark);
        }
    }

    public void onClickedToOneByOne(View view){
        Intent toOne = new Intent(this, OneByOne.class);
        toOne.putExtra("username", username);
        toOne.putExtra("user", userid);

        startActivity(toOne);
    }

    public void onClickedLogout(View view){
        Intent toLogin = new Intent(this, LoginActivity.class);
        startActivity(toLogin);
    }
}