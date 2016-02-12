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
import data.Path;
import database.DbHelper;
import database.VtmDb;

public class PathInfo extends AppCompatActivity {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;
    private static Context context;
    private static Path path;
    private static List<Ability> abilities;
    private static LinkedHashMap<Integer, Boolean> opened = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Path Information");

        context = this;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String pathId = extras.getString(Constants.EXTRA_PATH);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        path = new Path();
        abilities = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            path = vtmDb.getPathById(db, Integer.parseInt(pathId));
            abilities = vtmDb.getAbilitiesOfPath(db, Integer.parseInt(pathId));
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
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    public static void setPage(int position){
        mViewPager.setCurrentItem(position);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_path_info, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_path_info, container, false);
            final int message = getArguments().getInt(ARG_SECTION_NUMBER);
            setRadio(message, rootView);

            setStaticInfo(rootView, message);

            setDynamicInfo(rootView, message);
            return rootView;
        }

        private void setRadio(int message, View rootView){
            for(int i = 0; i < 5; i++){
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

        private void setStaticInfo(final View rootView, final int message){
            TextView name = (TextView) rootView.findViewById(R.id.name);
            name.setText(path.getName());
            TextView desc = (TextView) rootView.findViewById(R.id.desc);
            desc.setText(path.getDescription());
            if(path.getAttribute() == null){
                rootView.findViewById(R.id.attribute).setVisibility(View.GONE);
            }
            else{
                ((TextView)rootView.findViewById(R.id.attribute_desc)).setText(path.getAttribute());
            }

            if(path.getSystem() == null){
                rootView.findViewById(R.id.system).setVisibility(View.GONE);
            }
            else{
                ((TextView)rootView.findViewById(R.id.system_desc)).setText(path.getSystem());
            }

            if(path.getPrice() == null){
                rootView.findViewById(R.id.price).setVisibility(View.GONE);
            }
            else{
                ((TextView)rootView.findViewById(R.id.price_desc)).setText(path.getPrice());
            }

            if(path.getOfficial() == null){
                rootView.findViewById(R.id.official).setVisibility(View.GONE);
            }
            else{
                ((TextView)rootView.findViewById(R.id.official_desc)).setText(path.getOfficial());
            }

            LinearLayout header = (LinearLayout) rootView.findViewById(R.id.header_description);
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(opened.get(message)){
                        rootView.findViewById(R.id.description).setVisibility(View.GONE);
                        ((ImageView)rootView.findViewById(R.id.arrow)).setImageResource(android.R.drawable.arrow_down_float);
                        opened.put(message, false);
                    }
                    else{
                        rootView.findViewById(R.id.description).setVisibility(View.VISIBLE);
                        ((ImageView)rootView.findViewById(R.id.arrow)).setImageResource(android.R.drawable.arrow_up_float);
                        opened.put(message, true);
                    }
                }
            });
            if(!opened.containsKey(message)){
                opened.put(message,false);
            }
        }

        private void setDynamicInfo(View rootView, int message) {
            ((TextView)rootView.findViewById(R.id.ability_name)).setText(abilities.get(message).getTitle());
            ((TextView)rootView.findViewById(R.id.ability_desc)).setText(abilities.get(message).getDescription());
            ((TextView)rootView.findViewById(R.id.ability_system_desc)).setText(abilities.get(message).getSystem());
            if(abilities.get(message).getSystem() == null){
                rootView.findViewById(R.id.ability_system_desc).setVisibility(View.GONE);
                rootView.findViewById(R.id.ability_system).setVisibility(View.GONE);
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
            return abilities.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return abilities.get(position).getTitle();
        }
    }


}
