package com.example.tatproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tatproject.Fragments.Fragment_1;
import com.example.tatproject.Fragments.Fragment_2;
import com.example.tatproject.Fragments.Fragment_3;
import com.example.tatproject.Fragments.Fragment_4;

public class MyAdapter extends FragmentStateAdapter {
    public int mCount;

    public MyAdapter(FragmentActivity fa, int count){
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        int index = getRealPosition(position);

        // 위치에 따라 Fragment 객체를 return
        if (index==0) return new Fragment_1();
        else if (index==1) return new Fragment_2();
        else if (index==2) return new Fragment_3();
        else return new Fragment_4();
    }

    @Override
    public int getItemCount(){
        return 2000;
    }

    public int getRealPosition(int position){return position % mCount;}
}
