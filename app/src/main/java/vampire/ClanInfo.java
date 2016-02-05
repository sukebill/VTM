package vampire;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.threemenstudio.vampire.R;

import java.util.ArrayList;
import java.util.List;

import data.Clan;
import data.Constants;
import data.Discipline;
import database.DbHelper;
import database.VtmDb;

public class ClanInfo extends AppCompatActivity {

    private static List<Clan> clans;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clan_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String clanId = extras.getString(Constants.EXTRA_CLAN);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        clans = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            clans = vtmDb.getClansInfo(db);
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
        if (clanId != null) {
            mViewPager.setCurrentItem(Integer.parseInt(clanId));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clan_info, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_clan_info, container, false);
            final int message = getArguments().getInt(ARG_SECTION_NUMBER);
            final Clan clan = clans.get(message);
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db;
            List<Discipline> clanDisciplines;
            try {
                dbHelper.openDataBase();
            }catch(SQLException sqle){
                sqle.printStackTrace();
            }
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            clanDisciplines = vtmDb.getClanDisciplines(db, clan.getId());

            TextView clanText = (TextView) rootView.findViewById(R.id.clan);
            if(clan.getCaste() != null){
                clanText.setText(clan.getCaste());
            }
            else{
                clanText.setText(clan.getClan());
            }

            TextView littleDesc = (TextView) rootView.findViewById(R.id.little_desc);
            if(clan.getLittleDesc() != null){
                littleDesc.setText(clan.getLittleDesc());
            }
            else{
                littleDesc.setVisibility(View.GONE);
            }

            TextView nickname = (TextView) rootView.findViewById(R.id.nickname);
            if(clan.getNickname() != null){
                nickname.setText(clan.getNickname());
            }
            else{
                nickname.setVisibility(View.GONE);
                rootView.findViewById(R.id.nick).setVisibility(View.GONE);
            }

            for(int i = 1; i < clanDisciplines.size() + 1; i++){
                String clanDiscipline = "clan_discipline_" + i;
                TextView textView = (TextView) rootView.findViewById(context.getResources().getIdentifier(clanDiscipline
                        , "id", context.getPackageName()));
                textView.setText(clanDisciplines.get(i - 1).getTitle());
                textView.setVisibility(View.VISIBLE);
            }
            if(clanDisciplines.size() == 0){
                rootView.findViewById(R.id.clan_disciplines).setVisibility(View.GONE);
            }

            TextView description = (TextView) rootView.findViewById(R.id.desc);
            description.setText(clan.getDesc());

            TextView sect = (TextView) rootView.findViewById(R.id.sect);
            if(clan.getSect() != null){
                sect.setText(clan.getSect());
            }
            else{
                sect.setVisibility(View.GONE);
                rootView.findViewById(R.id.sect_layout).setVisibility(View.GONE);
            }

            TextView appearance = (TextView) rootView.findViewById(R.id.appearance);
            if(clan.getAppearance() != null){
                appearance.setText(clan.getAppearance());
            }
            else{
                appearance.setVisibility(View.GONE);
                rootView.findViewById(R.id.app_layout).setVisibility(View.GONE);
            }

            TextView haven = (TextView) rootView.findViewById(R.id.haven);
            if(clan.getHaven() != null){
                haven.setText(clan.getHaven());
            }
            else{
                haven.setVisibility(View.GONE);
                rootView.findViewById(R.id.haven_layout).setVisibility(View.GONE);
            }

            TextView background = (TextView) rootView.findViewById(R.id.background);
            if(clan.getBackground() != null){
                background.setText(clan.getBackground());
            }
            else{
                background.setVisibility(View.GONE);
                rootView.findViewById(R.id.background_layout).setVisibility(View.GONE);
            }

            TextView cc = (TextView) rootView.findViewById(R.id.cc);
            if(clan.getCharacterCreation() != null){
                cc.setText(clan.getCharacterCreation());
            }
            else{
                cc.setVisibility(View.GONE);
                rootView.findViewById(R.id.cc_layout).setVisibility(View.GONE);
            }

            TextView weakness = (TextView) rootView.findViewById(R.id.weakness);
            if(clan.getWeakness() != null){
                weakness.setText(clan.getWeakness());
            }
            else{
                weakness.setVisibility(View.GONE);
                rootView.findViewById(R.id.weakness_layout).setVisibility(View.GONE);
            }

            TextView organization = (TextView) rootView.findViewById(R.id.organization);
            if(clan.getOrganization() != null){
                organization.setText(clan.getOrganization());
            }
            else{
                organization.setVisibility(View.GONE);
                rootView.findViewById(R.id.organization_layout).setVisibility(View.GONE);
            }

            TextView bloodlines = (TextView) rootView.findViewById(R.id.bloodlines);
            if(clan.getBloodlines() != null){
                bloodlines.setText(clan.getBloodlines());
            }
            else{
                bloodlines.setVisibility(View.GONE);
                rootView.findViewById(R.id.bloodlines_layout).setVisibility(View.GONE);
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
            // Show 3 total pages.
            return clans.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return clans.get(position).getClan();
        }
    }
}
