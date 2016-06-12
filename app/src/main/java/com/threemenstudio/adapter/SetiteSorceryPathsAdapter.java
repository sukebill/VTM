package com.threemenstudio.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.threemenstudio.data.Sorcery;
import com.threemenstudio.fragments.SetiteSorceryPathsFragment;

import java.util.List;

/**
 * Created by Shitman on 12/6/2016.
 */
public class SetiteSorceryPathsAdapter extends FragmentPagerAdapter {

    private List<Sorcery> sorceries;
    private Context context;

    public SetiteSorceryPathsAdapter(FragmentManager fm, List<Sorcery> sorceries, Context context) {
        super(fm);
        this.sorceries = sorceries;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return SetiteSorceryPathsFragment.newInstance(position, sorceries, context);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return sorceries.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sorceries.get(position).getName();
    }
}