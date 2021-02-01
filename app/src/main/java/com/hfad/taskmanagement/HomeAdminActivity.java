package com.hfad.taskmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.hfad.taskmanagement.server.ServerConfig;

public class HomeAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        SectionsAdapter sectionsAdapter =
                new SectionsAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager_admin);
        pager.setAdapter(sectionsAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_admin);
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
                case 2:
                    return getResources().getText(R.string.manage_employee);
                case 3:
                    return getResources().getText(R.string.current_employee);
                case 4:
                    return getResources().getText(R.string.manage_group);
            }
            return super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CurrentTaskAdminFragment();
                case 1:
                    return new ProfileFragment();
                case 2:
                    return new CurrentEmployeeAdminFragment();
                case 3:
                    return new ManageEmployeeFragment();
                case 4:
                    return new ManageGroupFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
