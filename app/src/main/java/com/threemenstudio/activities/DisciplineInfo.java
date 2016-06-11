package com.threemenstudio.activities;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.adapter.DisciplineInfoPageAdapter;
import com.threemenstudio.data.Discipline;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.utilities.DepthPageTransformer;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.ActivityDisciplineInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class DisciplineInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityDisciplineInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_discipline_info);

        setSupportActionBar(binding.toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String disciplineId = extras.getString(VTMApplication.getExtraDiscipline());

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        List<Discipline> disciplines = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            disciplines = vtmDb.getDisciplinesInfo(db);
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        DisciplineInfoPageAdapter mSectionsPagerAdapter =
                new DisciplineInfoPageAdapter(getSupportFragmentManager(), disciplines, this);

        binding.container.setAdapter(mSectionsPagerAdapter);
        binding.container.setPageTransformer(true, new DepthPageTransformer());
        if (disciplineId != null) {
            binding.container.setCurrentItem(Integer.parseInt(disciplineId));
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

}
