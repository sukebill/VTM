package com.threemenstudio.activities;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.adapter.DisciplineAbilitiesPageAdapter;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.interfaces.DisciplineAbilitiesInterface;
import com.threemenstudio.utilities.DepthPageTransformer;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.ActivityDisciplineAbilitiesBinding;

import java.util.LinkedHashMap;
import java.util.List;

public class DisciplineAbilities extends AppCompatActivity implements DisciplineAbilitiesInterface{

    private static Context context;
    private static int maxLevel;
    private static int minLevel;
    private  ActivityDisciplineAbilitiesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_discipline_abilities);

        setSupportActionBar(binding.toolbar);

        context = this;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String disciplineId = extras.getString(VTMApplication.getExtraDiscipline());
        String discipline = extras.getString(VTMApplication.getExtraTitle());
        String official = extras.getString(VTMApplication.getExtraOfficial());

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

        VTMApplication.setOpened(new LinkedHashMap<Integer, List<Boolean>>());
        DisciplineAbilitiesPageAdapter mSectionsPagerAdapter
                = new DisciplineAbilitiesPageAdapter(getSupportFragmentManager(), maxLevel - minLevel +1,
                disciplineId, discipline, official, this, minLevel);


        binding.container.setAdapter(mSectionsPagerAdapter);
        binding.container.setPageTransformer(true, new DepthPageTransformer());

    }

    private void setPage(int position){
        binding.container.setCurrentItem(position);
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

    @Override
    public void setRadio(int message, View rootView){

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
