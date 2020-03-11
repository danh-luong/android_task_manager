package com.hfad.taskmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class HomeManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_manager);
        SectionsAdapter sectionsAdapter =
                new SectionsAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager_manger);
        pager.setAdapter(sectionsAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_manager);
        tabLayout.setupWithViewPager(pager);
    }

    private class SectionsAdapter extends FragmentPagerAdapter {

        public SectionsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.current_task);
                case 1:
                    return getResources().getText(R.string.profile);
            }
            return super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CurrentTaskManagerFragment();
                case 1:
                    return new ProfileFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
