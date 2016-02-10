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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.threemenstudio.vampire.R;

import java.util.ArrayList;
import java.util.List;

import data.Ability;
import data.Clan;
import data.Constants;
import data.Discipline;
import data.Path;
import data.Ritual;
import database.DbHelper;
import database.VtmDb;

public class DisciplineInfo extends AppCompatActivity {

    private static Context context;
    private static List<Discipline> disciplines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String disciplineId = extras.getString(Constants.EXTRA_DISCIPLINE);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        disciplines = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            disciplines = vtmDb.getDisciplinesInfo(db);
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
        if (disciplineId != null) {
            mViewPager.setCurrentItem(Integer.parseInt(disciplineId));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_discipline_info, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_discipline_info, container, false);
            final int message = getArguments().getInt(ARG_SECTION_NUMBER);
            final Discipline discipline = disciplines.get(message);
            TextView textView = (TextView) rootView.findViewById(R.id.discipline);
            textView.setText(discipline.getTitle());
            TextView desc = (TextView) rootView.findViewById(R.id.desc);
            desc.setText(discipline.getDescription());
            TextView bonusDesc = (TextView) rootView.findViewById(R.id.bonus_desc);
            if(discipline.getBonusDescription() != null){
                bonusDesc.setText(discipline.getBonusDescription());
            }
            else{
                bonusDesc.setVisibility(View.GONE);
            }

            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db;
            List<Ability> abilities = new ArrayList<>();
            List<Clan> clans = new ArrayList<>();
            List<Path> paths = new ArrayList<>();
            List<Ritual> rituals = new ArrayList<>();
            try {
                dbHelper.openDataBase();
                db = dbHelper.getReadableDatabase();
                VtmDb vtmDb = new VtmDb();
                clans = vtmDb.getInclan(db, discipline.getId());
                abilities = vtmDb.getAbilitiesOfDiscipline(db, discipline.getId());
                paths = vtmDb.getPathsOfDiscipline(db, discipline.getId());
                rituals = vtmDb.getRitualsOfDiscipline(db, discipline.getId());
                dbHelper.close();
            }catch(SQLException sqle){
                sqle.printStackTrace();
            }

            for(int i = 1; i < clans.size() + 1; i++){
                String clanDiscipline = "clan_" + i;
                TextView inClan  = (TextView) rootView.findViewById(context.getResources().getIdentifier(clanDiscipline
                        , "id", context.getPackageName()));
                if(clans.get(i -1).getCaste() != null){
                    inClan.setText(clans.get(i - 1).getCaste());
                }
                else{
                    inClan.setText(clans.get(i - 1).getClan());
                }
                inClan.setVisibility(View.VISIBLE);
            }
            if(clans.size() == 0){
                rootView.findViewById(R.id.clan).setVisibility(View.GONE);
            }

            if(abilities.size() > 0){
                Button fabAbilities = (Button) rootView.findViewById(R.id.fab_abilities);
                fabAbilities.setVisibility(View.VISIBLE);
                fabAbilities.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DisciplineAbilities.class);
                        intent.putExtra(Constants.EXTRA_DISCIPLINE, String.valueOf(discipline.getId()));
                        intent.putExtra(Constants.EXTRA_TITLE, discipline.getTitle());
                        intent.putExtra(Constants.EXTRA_OFFICIAL, discipline.getOfficialAbilities());
                        startActivity(intent);
                    }
                });
            }

            if(paths.size() > 0){
                Button fabPaths = (Button) rootView.findViewById(R.id.fab_paths);
                fabPaths.setVisibility(View.VISIBLE);
                fabPaths.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DisciplinePaths.class);
                        intent.putExtra(Constants.EXTRA_DISCIPLINE, String.valueOf(discipline.getId()));
                        intent.putExtra(Constants.EXTRA_TITLE, discipline.getTitle());
                        intent.putExtra(Constants.EXTRA_OFFICIAL, discipline.getOfficialAbilities());
                        startActivity(intent);
                    }
                });
            }

            if(rituals.size() > 0){
                Button fabRituals = (Button) rootView.findViewById(R.id.fab_rituals);
                fabRituals.setVisibility(View.VISIBLE);
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
            return disciplines.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return disciplines.get(position).getTitle();
        }
    }
}
