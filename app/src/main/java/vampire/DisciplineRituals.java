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

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.threemenstudio.vampire.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import data.Constants;
import data.Ritual;
import database.DbHelper;
import database.VtmDb;

public class DisciplineRituals extends AppCompatActivity {

    private static int maxLevel;
    private static Context context;
    private static String disciplineId;
    private static String ritual;
    private static String ritualSystem;
    private static ViewPager mViewPager;
    private static String discipline;
    private static LinkedHashMap<Integer,List<Boolean>> opened;
    private static List<Ritual> variable = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline_rituals);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        disciplineId = extras.getString(Constants.EXTRA_DISCIPLINE);
        discipline = extras.getString(Constants.EXTRA_TITLE);
        ritual = extras.getString(Constants.EXTRA_RITUAL);
        ritualSystem = extras.getString(Constants.EXTRA_RITUAL_SYSTEM);

        getSupportActionBar().setTitle(discipline);

        opened = new LinkedHashMap<>();

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            maxLevel = vtmDb.getMaxLevelOfRitualsForDiscipline(db, Integer.parseInt(disciplineId));
            variable = vtmDb.getRitualsOfDisciplineByLevel(db, Integer.parseInt(disciplineId), 0);
            if(variable.size() > 0){
                maxLevel++;
            }
            if(discipline.equals("Koldunic Sorcery")){
                maxLevel = maxLevel - 2;
            }
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
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    public static void setPage(int position){
        mViewPager.setCurrentItem(position);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_discipline_rituals2, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_discipline_rituals, container, false);
            int message = getArguments().getInt(ARG_SECTION_NUMBER);

            if(discipline.equals("Koldunic Sorcery")){
                if(message == 6){
                    message = message + 2;
                }
                setRadioKoldunic(message, rootView);
            }
            else{
                setRadio(message, rootView);
            }

            TextView ri = (TextView) rootView.findViewById(R.id.ritual_desc);
            ri.setText(ritual);
            TextView system = (TextView) rootView.findViewById(R.id.system_desc);
            system.setText(ritualSystem);

            if(ritual == null){
                rootView.findViewById(R.id.ll_desc).setVisibility(View.GONE);
            }
            if(ritualSystem == null){
                rootView.findViewById(R.id.system).setVisibility(View.GONE);
            }
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db;
            List<Ritual> rituals = new ArrayList<>();
            try {
                dbHelper.openDataBase();
                db = dbHelper.getReadableDatabase();
                VtmDb vtmDb = new VtmDb();
                rituals = vtmDb.getRitualsOfDisciplineByLevel(db, Integer.parseInt(disciplineId), message + 1);
                dbHelper.close();
            }catch(SQLException sqle){
                sqle.printStackTrace();
            }
            if(rituals.size() == 0){
                //variable time
                if(variable.size() == 1){
                    setOneRitual(variable, rootView);
                }
                else{
                    setManyRituals(variable, rootView, message);
                }
            }
            else if(rituals.size() == 1){
                setOneRitual(rituals, rootView);
            }
            else{
                setManyRituals(rituals, rootView, message);
            }
            return rootView;
        }

        private void setOneRitual(List<Ritual> rituals, View rootView){
            LinearLayout ritual = (LinearLayout) LinearLayout.inflate(context, R.layout.ritual, null);
            TextView title = (TextView) ritual.findViewById(R.id.title);
            String[] name = rituals.get(0).getName().split(" - ");
            if(discipline.equals("Assamite Sorcery")){
                title.setText(name[0]);
            }
            else{
                title.setText(rituals.get(0).getName());
            }
            TextView desc = (TextView) ritual.findViewById(R.id.desc);
            desc.setText(rituals.get(0).getDescription());
            TextView systemDesc = (TextView) ritual.findViewById(R.id.system_desc);
            systemDesc.setText(rituals.get(0).getSystem());
            if(rituals.get(0).getSystem() == null){
                (ritual.findViewById(R.id.system)).setVisibility(View.GONE);
                (ritual.findViewById(R.id.system_desc)).setVisibility(View.GONE);
            }
            ritual.findViewById(R.id.arrow).setVisibility(View.GONE);
            ritual.findViewById(R.id.child).setVisibility(View.VISIBLE);
            ritual.findViewById(R.id.child).setPadding(0,0,0,0);
            LinearLayout scrollView = (LinearLayout) rootView.findViewById(R.id.rituals);
            scrollView.addView(ritual);
        }

        private void setManyRituals(List<Ritual> rituals, View rootView, final int message){
            final List<Boolean> list = new ArrayList<>();
            for(int i = 0; i < rituals.size(); i++) {
                list.add(false);
                final LinearLayout ritual = (LinearLayout) LinearLayout.inflate(context, R.layout.ritual, null);
                TextView title = (TextView) ritual.findViewById(R.id.title);
                String[] name = rituals.get(i).getName().split(" - ");
                switch (discipline) {
                    case "Assamite Sorcery":
                        title.setText(name[0]);
                        break;
                    case "Dark Thaumaturgy":
                        title.setText(name[0]);
                        break;
                    case ("Thaumaturgy"):
                        setThaumaturgy(name, title);
                        break;
                    default:
                        title.setText(rituals.get(i).getName());
                        break;
                }
                final LinearLayout child = (LinearLayout) ritual.findViewById(R.id.child);
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
                        if (opened.get(message).get(finalI)) {
                            child.setVisibility(View.GONE);
                            opened.get(message).set(finalI, false);
                            ((ImageView)ritual.findViewById(R.id.arrow)).setImageResource(android.R.drawable.arrow_down_float);
                        } else {
                            child.setVisibility(View.VISIBLE);
                            opened.get(message).set(finalI, true);
                            ((ImageView)ritual.findViewById(R.id.arrow)).setImageResource(android.R.drawable.arrow_up_float);
                        }
                    }
                });
                LinearLayout scrollView = (LinearLayout) rootView.findViewById(R.id.rituals);
                scrollView.addView(ritual);
            }
            if(!opened.containsKey(message)){
                opened.put(message,list);
            }
        }

        private void setThaumaturgy(String[] name, TextView title){
            switch (name[0]){
                case "Black Sunrise":
                    title.setText(name[1]);
                    break;
                case "Curtain of Will":
                    title.setText(name[1]);
                    break;
                case "Falsely Sealed Vessel":
                    title.setText(name[1]);
                    break;
                case "Loyal Eyes":
                    title.setText(name[1]);
                    break;
                case "Shepherd's Silent Vigil":
                    title.setText(name[1]);
                    break;
                case "Speak with Sire":
                    title.setText(name[1]);
                    break;
                case "Draught of the Pebble":
                    title.setText(name[1]);
                    break;
                case "Follow the Lie":
                    title.setText(name[1]);
                    break;
                case "Run to Judgment":
                    title.setText(name[1]);
                    break;
                case "Haqim's Disfavor":
                    title.setText(name[1]);
                    break;
                case "Stain of Guilt":
                    title.setText(name[1]);
                    break;
                default:
                    title.setText(name[0]);
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
            return maxLevel;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    private static void setRadio(int message, View rootView){
        for(int i = 0; i < maxLevel; i++){
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

    private static void setRadioKoldunic(int message, View rootView){
        for(int i = 0; i < maxLevel + 2; i++){
            final int item = i + 1;
            String radio = "radioButton" + item;
            String relativeLayout = "click" + item;

            if(i < message + 1){
                ((RadioButton) rootView.findViewById(context.getResources().getIdentifier(radio
                        , "id", context.getPackageName()))).setChecked(true);
            }
            RelativeLayout click = (RelativeLayout) rootView.findViewById(context.getResources()
                    .getIdentifier(relativeLayout, "id", context.getPackageName()));
            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item != 7 | item != 8){
                        setPage(item - 1);
                    }
                    else if(item == 7){
                        setPage(item - 2);
                    }
                    else if(item == 8){
                        setPage(item - 3);
                    }
                }
            });
        }
    }
}
