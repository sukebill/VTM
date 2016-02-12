package vampire;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.threemenstudio.vampire.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import data.Ability;
import data.Constants;
import database.DbHelper;
import database.VtmDb;

public class DisciplineAbilities extends AppCompatActivity {

    private static Context context;
    private static int maxLevel;
    private static int minLevel;
    private static String disciplineId;
    private static LinkedHashMap<Integer,List<Boolean>> opened;
    private static ViewPager mViewPager;
    private static String official;
    private static String discipline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline_abilities);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        disciplineId = extras.getString(Constants.EXTRA_DISCIPLINE);
        discipline = extras.getString(Constants.EXTRA_TITLE);
        official = extras.getString(Constants.EXTRA_OFFICIAL);

        getSupportActionBar().setTitle(discipline);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            maxLevel = vtmDb.getMaxLevelOfAbilitiesForDiscipline(db, Integer.parseInt(disciplineId));
            minLevel = vtmDb.getMinLevelOfAbilitiesForDiscipline(db, Integer.parseInt(disciplineId));
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        opened = new LinkedHashMap<>();

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
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    public static void setPage(int position){
        mViewPager.setCurrentItem(position);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_discipline_abilities, menu);
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
            final View rootView = inflater.inflate(R.layout.fragment_discipline_abilities, container, false);
            int message;
            if(discipline.equals("Celerity") | discipline.equals("Fortitude") | discipline.equals("Potence")){
                message = getArguments().getInt(ARG_SECTION_NUMBER) + minLevel - 1;
            }
            else{
                message = getArguments().getInt(ARG_SECTION_NUMBER);
            }
            final int trueMessage = message;

            setRadio(message,rootView);

            if(official != null){
                ((TextView)rootView.findViewById(R.id.official_desc)).setText(official);
            }
            else{
                rootView.findViewById(R.id.ll_official).setVisibility(View.GONE);
            }
            List<Ability> abilities = new ArrayList<>();
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db;
            try {
                dbHelper.openDataBase();
                db = dbHelper.getReadableDatabase();
                VtmDb vtmDb = new VtmDb();
                abilities = vtmDb.getAAbilitiesOfDisciplineByLevel(db, Integer.parseInt(disciplineId), message + 1);
                dbHelper.close();
            }catch(SQLException sqle){
                sqle.printStackTrace();
            }
            if(abilities.size() == 1){
                LinearLayout ability = (LinearLayout) LinearLayout.inflate(context, R.layout.ability, null);
                TextView title = (TextView) ability.findViewById(R.id.title);
                title.setText(abilities.get(0).getTitle());
                TextView desc = (TextView) ability.findViewById(R.id.desc);
                desc.setText(abilities.get(0).getDescription());
                TextView system = (TextView) ability.findViewById(R.id.system_desc);
                system.setText(abilities.get(0).getSystem());
                if(abilities.get(0).getSystem() == null){
                    (ability.findViewById(R.id.system)).setVisibility(View.GONE);
                }
                ability.findViewById(R.id.arrow).setVisibility(View.GONE);
                LinearLayout scrollView = (LinearLayout) rootView.findViewById(R.id.abilities);
                scrollView.addView(ability);
            }
            else{
                final List<Boolean> list = new ArrayList<>();
                for(int i = 0; i < abilities.size(); i++){
                    list.add(false);
                    final LinearLayout ability = (LinearLayout) LinearLayout.inflate(context, R.layout.ability, null);
                    TextView title = (TextView) ability.findViewById(R.id.title);
                    title.setText(abilities.get(i).getTitle());
                    final LinearLayout child = (LinearLayout) ability.findViewById(R.id.child);
                    if(opened.containsKey(message)){
                        if(opened.get(message).get(i)){
                            child.setVisibility(View.VISIBLE);
                        }
                        else{
                            child.setVisibility(View.GONE);
                        }
                    }
                    else{
                        child.setVisibility(View.GONE);
                    }
                    child.setPadding(20, 0, 0, 0);
                    TextView desc = (TextView) ability.findViewById(R.id.desc);
                    desc.setText(abilities.get(i).getDescription());
                    TextView system = (TextView) ability.findViewById(R.id.system_desc);
                    system.setText(abilities.get(i).getSystem());
                    if(abilities.get(i).getSystem() == null){
                        (ability.findViewById(R.id.system)).setVisibility(View.GONE);
                    }
                    LinearLayout header = (LinearLayout) ability.findViewById(R.id.header);
                    final int finalI = i;
                    header.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (opened.get(trueMessage).get(finalI)) {
                                child.setVisibility(View.GONE);
                                opened.get(trueMessage).set(finalI, false);
                                ((ImageView)ability.findViewById(R.id.arrow)).setImageResource(android.R.drawable.arrow_down_float);
                            } else {
                                child.setVisibility(View.VISIBLE);
                                opened.get(trueMessage).set(finalI, true);
                                ((ImageView)ability.findViewById(R.id.arrow)).setImageResource(android.R.drawable.arrow_up_float);
                            }
                        }
                    });
                    LinearLayout scrollView = (LinearLayout) rootView.findViewById(R.id.abilities);
                    scrollView.addView(ability);
                }
                if(!opened.containsKey(message)){
                    opened.put(message,list);
                }
            }
            return rootView;
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

            return maxLevel - minLevel +1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    private static void setRadio(int message, View rootView){
        for(int i = minLevel - 1; i < maxLevel; i++){
            final int item = i + 1;
            String radio = "radioButton" + item;
            String relativeLayout = "click" + item;

            if(i < message +1){
                ((RadioButton) rootView.findViewById(context.getResources().getIdentifier(radio
                        , "id", context.getPackageName()))).setChecked(true);
            }
            RelativeLayout click = (RelativeLayout) rootView.findViewById(context.getResources()
                    .getIdentifier(relativeLayout, "id", context.getPackageName()));
            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPage(item - 1);
                }
            });
        }
    }
}
