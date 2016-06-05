package com.threemenstudio.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.threemenstudio.data.Discipline;
import com.threemenstudio.fragments.DisciplineInfoFragment;

import java.util.List;

/**
 * Created by Shitman on 5/6/2016.
 */
public class DisciplineInfoPageAdapter  extends FragmentPagerAdapter {

    private List<Discipline> disciplines;
    private Context context;

    public DisciplineInfoPageAdapter(FragmentManager fm, List<Discipline> disciplines, Context context) {
        super(fm);
        this.disciplines = disciplines;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return DisciplineInfoFragment.newInstance(position, disciplines, context);
    }

    @Override
    public int getCount() {
        return disciplines.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return disciplines.get(position).getTitle();
    }
}
