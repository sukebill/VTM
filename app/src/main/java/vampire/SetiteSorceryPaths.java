package vampire;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threemenstudio.vampire.R;

import java.util.ArrayList;
import java.util.List;

import data.Constants;
import data.Path;
import data.Sorcery;
import database.DbHelper;
import database.VtmDb;

public class SetiteSorceryPaths extends AppCompatActivity {

    private static List<Sorcery> sorceries;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setite_sorcery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String discipline = extras.getString(Constants.EXTRA_TITLE);

        getSupportActionBar().setTitle(discipline);

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
        getMenuInflater().inflate(R.menu.menu_setite_sorcery, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_setite_sorcery, container, false);
            final int message = getArguments().getInt(ARG_SECTION_NUMBER);
            ((TextView)rootView.findViewById(R.id.desc)).setText(sorceries.get(message).getDesc());
            if(sorceries.get(message).getSocial() == null){
                rootView.findViewById(R.id.social).setVisibility(View.GONE);
            }
            else{
                ((TextView)rootView.findViewById(R.id.social_desc)).setText(sorceries.get(message).getSocial());
            }

            setPaths(rootView, message);

            return rootView;
        }

        private void setPaths(View rootView, int message){
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db;
            List<Path> paths = new ArrayList<>();
            try {
                dbHelper.openDataBase();
                db = dbHelper.getReadableDatabase();
                VtmDb vtmDb = new VtmDb();
                paths = vtmDb.getPathsOfSorcery(db, sorceries.get(message).getId());
                dbHelper.close();
            }catch(SQLException sqle){
                sqle.printStackTrace();
            }
            for(int i = 0; i < paths.size(); i++){
                LinearLayout path = (LinearLayout) LinearLayout.inflate(context, R.layout.path, null);
                TextView title = (TextView) path.findViewById(R.id.title);
                String[] name = paths.get(i).getName().split(" - ");
                if(message == 0){
                    setAkhu(name, title);
                }
                if(message == 1){
                    setSadhana(name, title);
                }
                if(message == 2){
                    setWanga(name, title);
                }
                LinearLayout header = (LinearLayout) path.findViewById(R.id.header);
                final int id = paths.get(i).getId();
                header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PathInfo.class);
                        intent.putExtra(Constants.EXTRA_PATH, String.valueOf(id));
                        startActivity(intent);
                    }
                });
                LinearLayout scrollView = (LinearLayout) rootView.findViewById(R.id.scroller);
                scrollView.addView(path);
            }
        }

        private void setAkhu(String[] name , TextView title){
            switch (name[0]) {
                case "Jinn's Gift":
                    title.setText(name[1]);
                    break;
                case "Echo of Nirvana":
                    title.setText(name[2]);
                    break;
                case "Suleiman's Laws":
                    title.setText(name[2]);
                    break;
                case "Weather Control":
                    title.setText(name[1]);
                    break;
                default:
                    title.setText(name[0]);
                    break;
            }
        }

        private void setSadhana(String[] name , TextView title){
            switch (name[0]) {
                case "Alchemy":
                    title.setText(name[1]);
                    break;
                case "Hands of Destruction":
                    title.setText(name[1]);
                    break;
                case "The Movement of the Mind":
                    title.setText(name[1]);
                    break;
                case "The Snake Inside":
                    title.setText(name[1]);
                    break;
                case "Suleiman's Laws":
                    title.setText(name[3]);
                    break;
                case "Weather Control":
                    title.setText(name[1]);
                    break;
                case "Jinn's Gift":
                    title.setText(name[1]);
                    break;
                default:
                    title.setText(name[0]);
                    break;
            }
        }

        private void setWanga(String[] name , TextView title){
            switch (name[0]) {
                case "Jinn's Gift":
                    title.setText(name[1]);
                    break;
                case "Life's Water":
                    title.setText(name[1]);
                    break;
                case "The False Heart":
                    title.setText(name[1]);
                    break;
                case "Suleiman's Laws":
                    title.setText(name[1]);
                    break;
                case "Sebau's Touch":
                    title.setText(name[1]);
                    break;
                default:
                    title.setText(name[0]);
                    break;
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
