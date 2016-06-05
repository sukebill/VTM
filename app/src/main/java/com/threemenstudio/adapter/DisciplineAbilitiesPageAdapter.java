package com.threemenstudio.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.fragments.DisciplineAbilitiesFragment;

/**
 * Created by Shitman on 5/6/2016.
 */
public class DisciplineAbilitiesPageAdapter  extends FragmentPagerAdapter {

    private int count;
    private String disciplineId;
    private String discipline;
    private String official;
    private Context context;
    private int minLevel;

    public DisciplineAbilitiesPageAdapter(FragmentManager fm, int count, String disciplineId,
                                          String discipline, String official, Context context,
                                          int minLevel ) {
        super(fm);
        this.count = count;
        this.discipline = discipline;
        this.disciplineId = disciplineId;
        this.official = official;
        this.context = context;
        this.minLevel = minLevel;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return DisciplineAbilitiesFragment.newInstance(position, disciplineId, discipline, official,
                VTMApplication.getOpened(), context, minLevel);
    }

    @Override
    public int getCount() {

        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}