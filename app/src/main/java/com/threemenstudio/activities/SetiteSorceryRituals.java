package com.threemenstudio.activities;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.adapter.SetiteSorceryRitualsAdapter;
import com.threemenstudio.data.Sorcery;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.utilities.DepthPageTransformer;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.ActivitySetiteSorceryRitualsBinding;

import java.util.ArrayList;
import java.util.List;

public class SetiteSorceryRituals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivitySetiteSorceryRitualsBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_setite_sorcery_rituals);

        setSupportActionBar(binding.toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String discipline = extras.getString(VTMApplication.getExtraTitle());

        getSupportActionBar().setTitle(discipline);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        List<Sorcery> sorceries = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            sorceries = vtmDb.getSetiteSorceries(db);
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        SetiteSorceryRitualsAdapter mSectionsPagerAdapter =
                new SetiteSorceryRitualsAdapter(getSupportFragmentManager(), sorceries, this);

        binding.container.setAdapter(mSectionsPagerAdapter);
        binding.container.setPageTransformer(true, new DepthPageTransformer());
        binding.tabs.setupWithViewPager(binding.container);
        binding.tabs.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.Accent));

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_setite_sorcery_rituals, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

}
