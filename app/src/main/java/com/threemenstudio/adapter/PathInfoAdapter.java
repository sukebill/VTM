package com.threemenstudio.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.threemenstudio.data.Ability;
import com.threemenstudio.data.Path;
import com.threemenstudio.fragments.PathInfoFragment;

import java.util.List;

/**
 * Created by Shitman on 11/6/2016.
 */
public class PathInfoAdapter extends FragmentPagerAdapter {

    private List<Ability> abilities;
    private Context context;
    private Path path;

    public PathInfoAdapter(FragmentManager fm, List<Ability> abilities, Context context, Path path) {
        super(fm);
        this.abilities = abilities;
        this.path = path;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PathInfoFragment.newInstance(position, context, path, abilities);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return abilities.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return abilities.get(position).getTitle();
    }
}