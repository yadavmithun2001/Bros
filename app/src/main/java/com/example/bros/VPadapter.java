package com.example.bros;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VPadapter extends FragmentPagerAdapter {

    ArrayList<Fragment> fragmentarrayList = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();


    public VPadapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentarrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentarrayList.size();
    }

    public void addfragment(Fragment fragment,String title){
        fragmentarrayList.add(fragment);
        arrayList.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayList.get(position);
    }
}
