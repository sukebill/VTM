package com.threemenstudio.activities;

import android.content.Intent;
import android.database.SQLException;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.threemenstudio.database.DbHelper;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.ActivityMainpageBinding;

public class MainPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityMainpageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mainpage);
        if (binding.toolbarLayout != null) {
            binding.toolbarLayout.setTitle("Vampire: The Masquerade");
        }
        if (binding.toolbarLayout != null) {
            binding.toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
        setSupportActionBar(binding.toolbar);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateCharacterPlayerInfo.class);
                startActivity(intent);
            }
        });*/
        DbHelper dbHelper = new DbHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        try {
            dbHelper.openDataBase();
           dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sheet, menu);
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

    public void openClans(View v){
        Intent intent = new Intent(this, Clans.class);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    public void openDisciplines(View v){
        Intent intent = new Intent(this, Disciplines.class);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    public void openSects(View v){

    }

    public void openMyCharacters(View v){

    }

    public void openNewCharacter(View v){

    }

    public void openComboDisciplines(View v){

    }

    public void openMeritsAndFlaws(View v){

    }

    public void openDerangements(View v){

    }
}
