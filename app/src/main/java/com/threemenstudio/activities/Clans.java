package com.threemenstudio.activities;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.threemenstudio.adapter.RvAdapter;
import com.threemenstudio.data.Clan;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.ActivityClansBinding;

import java.util.ArrayList;
import java.util.List;

public class Clans extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityClansBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_clans);
        setSupportActionBar(binding.appBar.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.appBar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        List<Clan> clans = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            clans = vtmDb.getClans(db);
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.appBar.content.rv.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new RvAdapter(this,clans);
        binding.appBar.content.rv.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.clans, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
