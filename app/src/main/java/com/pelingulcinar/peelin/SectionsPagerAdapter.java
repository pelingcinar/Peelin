package com.pelingulcinar.peelin;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;




/**
 * Created by Pelin on 5.05.2018.
 */

class SectionsPagerAdapter extends FragmentPagerAdapter{

    public SectionsPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {


        switch(position){
            case 0:
                RequestFragment requestFragment = new RequestFragment();
                return requestFragment;

            case 1:
                ChatsFragment chatsFragment = new ChatsFragment();
                return chatsFragment;

            case 2:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

            default:
                    return null;

        }
    }

    @Override
    public int getCount(){

        return 3;
    }

    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "REQUESTS";
            case 1:
                return "CHATS";
            case 2:
                return "FRIENDS";
            default:
                return null;
        }

    }
}
