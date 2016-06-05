package com.threemenstudio.activities;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.vampire.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.threemenstudio.data.Ritual;
import com.threemenstudio.data.Sorcery;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;

public class SetiteSorceryRituals extends AppCompatActivity {

    private static List<Sorcery> sorceries;
    private static Context context;
    private static LinkedHashMap<String,List<Boolean>> opened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setite_sorcery_rituals);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String discipline = extras.getString(VTMApplication.getExtraTitle());

        getSupportActionBar().setTitle(discipline);

        opened = new LinkedHashMap<>();

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        sorceries = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            sorceries = vtmDb.getSetiteSorceries(db);
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /*
      The {@link android.support.v4.view.PagerAdapter} that will provide
      fragments for each of the sections. We use a
      {@link FragmentPagerAdapter} derivative, which will keep every
      loaded fragment in memory. If this becomes too memory intensive, it
      may be best to switch to a
      {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*
      The {@link ViewPager} that will host the section contents.
     */
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.Accent));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setite_sorcery_rituals, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_setite_sorcery_rituals, container, false);
            final int message = getArguments().getInt(ARG_SECTION_NUMBER);
            ((TextView)rootView.findViewById(R.id.desc)).setText(sorceries.get(message).getDesc());
            if(sorceries.get(message).getRituals() == null){
                rootView.findViewById(R.id.ritual).setVisibility(View.GONE);
            }
            else{
                ((TextView)rootView.findViewById(R.id.desc)).setText(sorceries.get(message).getRituals());
            }
            if(sorceries.get(message).getRitualPractice() == null){
                rootView.findViewById(R.id.ritual_practice).setVisibility(View.GONE);
            }
            else{
                ((TextView)rootView.findViewById(R.id.practice_desc)).setText(sorceries.get(message).getRitualPractice());
            }

            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db;
            LinkedHashMap<Integer, List<Ritual>> hashMap = new LinkedHashMap<>();
            try {
                dbHelper.openDataBase();
                db = dbHelper.getReadableDatabase();
                VtmDb vtmDb = new VtmDb();
                int maxLevel = vtmDb.getMaxLevelOfRitualsForSorcery(db, sorceries.get(message).getId());
                for(int i = 0; i < maxLevel + 1; i++){
                    //hashMap.put(i, vtmDb.getRitualOfSetiteSorceryByLevel(db, sorceries.get(message).getId(), i));
                    List<Ritual> rituals = vtmDb.getRitualOfSetiteSorceryByLevel(db, sorceries.get(message).getId(), i);
                    if(rituals.size() > 0){
                        //lets create a layout
                        setManyRituals(rituals, rootView, message, String.valueOf(i));
                    }
                }
                dbHelper.close();
            }catch(SQLException sqle){
                sqle.printStackTrace();
            }

            return rootView;
        }

        private void setManyRituals(List<Ritual> rituals, View rootView, final int message, final String j){
            final List<Boolean> list = new ArrayList<>();
            LinearLayout level = (LinearLayout) LinearLayout.inflate(context, R.layout.level, null);
            for(int i = 0; i < rituals.size(); i++) {
                list.add(false);
                final String key = message + j;
                final LinearLayout ritual = (LinearLayout) LinearLayout.inflate(context, R.layout.ritual_of_sorcery, null);
                TextView title = (TextView) ritual.findViewById(R.id.title);
                title.setText(rituals.get(i).getName());
                final LinearLayout child = (LinearLayout) ritual.findViewById(R.id.child);
                if(opened.containsKey(key)){
                    if(opened.get(key).get(i)){
                        child.setVisibility(View.VISIBLE);
                    }
                    else{
                        child.setVisibility(View.GONE);
                    }
                }
                else{
                    child.setVisibility(View.GONE);
                }
                TextView desc = (TextView) ritual.findViewById(R.id.desc);
                desc.setText(rituals.get(i).getDescription());
                TextView systemDesc = (TextView) ritual.findViewById(R.id.system_desc);
                systemDesc.setText(rituals.get(i).getSystem());
                if(rituals.get(i).getSystem() == null){
                    (ritual.findViewById(R.id.system)).setVisibility(View.GONE);
                    (ritual.findViewById(R.id.system_desc)).setVisibility(View.GONE);
                }
                LinearLayout header = (LinearLayout) ritual.findViewById(R.id.header);
                final int finalI = i;
                header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (opened.get(key).get(finalI)) {
                            child.setVisibility(View.GONE);
                            opened.get(key).set(finalI, false);
                            ((ImageView) ritual.findViewById(R.id.arrow)).setImageResource(android.R.drawable.arrow_down_float);
                        } else {
                            child.setVisibility(View.VISIBLE);
                            opened.get(key).set(finalI, true);
                            ((ImageView) ritual.findViewById(R.id.arrow)).setImageResource(android.R.drawable.arrow_up_float);
                        }
                    }
                });
                level.addView(ritual);
            }
            if(Integer.parseInt(j) == 0){
                ((TextView)level.findViewById(R.id.level)).setText("Variable Level");
            }
            else{
                ((TextView)level.findViewById(R.id.level)).setText("Level " + j);
            }
            LinearLayout scrollView = (LinearLayout) rootView.findViewById(R.id.scroller);
            scrollView.addView(level);
            if(!opened.containsKey(message + j)){
                opened.put(message + j, list);
            }
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
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
}
