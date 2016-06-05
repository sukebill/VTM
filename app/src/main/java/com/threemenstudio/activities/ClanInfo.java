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
import com.threemenstudio.adapter.ClanInfoPageAdapter;
import com.threemenstudio.data.Clan;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.utilities.DepthPageTransformer;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.ActivityClanInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class ClanInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityClanInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_clan_info);

        setSupportActionBar(binding.toolbar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String clanId = extras.getString(VTMApplication.getExtraClan());

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        List<Clan> clans = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            clans = vtmDb.getClansInfo(db);
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        ClanInfoPageAdapter mSectionsPagerAdapter = new ClanInfoPageAdapter(getSupportFragmentManager(), clans);

        binding.container.setAdapter(mSectionsPagerAdapter);
        binding.container.setPageTransformer(true, new DepthPageTransformer());
        if (clanId != null) {

            binding.container.setCurrentItem(Integer.parseInt(clanId));

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
