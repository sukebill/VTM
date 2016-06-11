package com.threemenstudio.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.threemenstudio.data.Ritual;
import com.threemenstudio.fragments.DisciplineRitualsFragment;

import java.util.List;

/**
 * Created by Shitman on 11/6/2016.
 */
public class DisciplineRitualsAdapter  extends FragmentPagerAdapter {

    private int maxLevel;
    private String discipline;
    private String ritual;
    private String ritualSystem;
    private String disciplineId;
    private List<Ritual> variable;
    private Context context;

    public DisciplineRitualsAdapter(FragmentManager fm, int maxLevel, Context context,
                                    String disciplineId, String discipline,
                                    String ritual, String ritualSystem,
                                    List<Ritual> variable) {
        super(fm);
        this.maxLevel = maxLevel;
        this.context = context;
        this.discipline = discipline;
        this.disciplineId = disciplineId;
        this.ritual = ritual;
        this.ritualSystem = ritualSystem;
        this.variable = variable;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return DisciplineRitualsFragment.newInstance(position, context, disciplineId, discipline, ritual, ritualSystem, variable);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return maxLevel;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}