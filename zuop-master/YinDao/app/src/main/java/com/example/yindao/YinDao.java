package com.example.yindao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.yindao.yindao.BlankFragment;
import com.example.yindao.yindao.BlankFragment2;
import com.example.yindao.yindao.BlankFragment3;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class YinDao extends AppCompatActivity {
    List<Fragment> fragments;
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yin_dao);
        vp = findViewById(R.id.vp);
        fragments = new ArrayList<>();
        fragments.add(new BlankFragment());
        fragments.add(new BlankFragment2());
        fragments.add(new BlankFragment3());
        vp.setAdapter(new Adapter(getSupportFragmentManager()));
    }
    public class Adapter extends FragmentStatePagerAdapter{

        public Adapter(@NonNull @NotNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}