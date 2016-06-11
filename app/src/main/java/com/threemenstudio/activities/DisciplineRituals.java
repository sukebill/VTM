package com.threemenstudio.activities;

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
import com.threemenstudio.adapter.DisciplineRitualsAdapter;
import com.threemenstudio.data.Ritual;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.interfaces.PagerRadioButtonInterface;
import com.threemenstudio.utilities.DepthPageTransformer;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.ActivityDisciplineRitualsBinding;

import java.util.ArrayList;
import java.util.List;

public class DisciplineRituals extends AppCompatActivity implements PagerRadioButtonInterface{

    private static int maxLevel;
    private static List<Ritual> variable = new ArrayList<>();
    ActivityDisciplineRitualsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_discipline_rituals);
        setSupportActionBar(binding.toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String disciplineId = extras.getString(VTMApplication.getExtraDiscipline());
        String discipline = extras.getString(VTMApplication.getExtraTitle());
        String ritual = extras.getString(VTMApplication.getExtraRitual());
        String ritualSystem = extras.getString(VTMApplication.getExtraRitualSystem());

        getSupportActionBar().setTitle(discipline);

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
            if(discipline != null && discipline.equals("Koldunic Sorcery")){
                maxLevel = maxLevel - 2;
            }
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        DisciplineRitualsAdapter mSectionsPagerAdapter =
                new DisciplineRitualsAdapter(getSupportFragmentManager(), maxLevel, this, disciplineId,
                        discipline, ritual, ritualSystem, variable);
        binding.container.setAdapter(mSectionsPagerAdapter);
        binding.container.setPageTransformer(true, new DepthPageTransformer());

    }

    public void setPage(int position){
        binding.container.setCurrentItem(position);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_discipline_rituals2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    public void setRadio(int message, View rootView){

        for(int i = 0; i < maxLevel; i++){

            final int item = i + 1;
            String radio = "radioButton" + item;
            String relativeLayout = "click" + item;

            if(i < message +1){
                ((RadioButton) rootView.findViewById(getResources().getIdentifier(radio
                        , "id", getPackageName()))).setChecked(true);
            }
            RelativeLayout click = (RelativeLayout) rootView.findViewById(getResources()
                    .getIdentifier(relativeLayout, "id", getPackageName()));
            click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPage(item - 1);
                }
            });
        }
    }

    public void setRadioKoldunic(int message, View rootView){

        for(int i = 0; i < maxLevel + 2; i++){
            final int item = i + 1;
            String radio = "radioButton" + item;
            String relativeLayout = "click" + item;

            if(i < message + 1){

                ((RadioButton) rootView.findViewById(getResources().getIdentifier(radio
                        , "id", getPackageName()))).setChecked(true);

            }
            RelativeLayout click = (RelativeLayout) rootView.findViewById(getResources()
                    .getIdentifier(relativeLayout, "id", getPackageName()));
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
