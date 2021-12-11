package com.example.converse.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.converse.Fragments.CallFragment;
import com.example.converse.Fragments.ChatsFragment;
import com.example.converse.Fragments.StatusFragement;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ChatsFragment();
            case 1: return new StatusFragement();
            case 2: return new CallFragment();
            default:return new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0){
            title = "CHATS";
        }
        if (position == 1){
            title = "Status";
        }
        if (position == 2){
            title = "Call";
        }
        return title;
    }
}
