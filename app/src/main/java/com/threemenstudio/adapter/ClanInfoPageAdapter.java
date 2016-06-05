package com.threemenstudio.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.threemenstudio.data.Clan;
import com.threemenstudio.fragments.ClanInfoFragment;

import java.util.List;

/**
 * Created by Shitman on 5/6/2016.
 */
public class ClanInfoPageAdapter  extends FragmentPagerAdapter {

    private List<Clan> clans;

    public ClanInfoPageAdapter(FragmentManager fm, List<Clan> clans) {
        super(fm);
        this.clans = clans;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return ClanInfoFragment.newInstance(position, clans);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return clans.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return clans.get(position).getClan();
    }
}