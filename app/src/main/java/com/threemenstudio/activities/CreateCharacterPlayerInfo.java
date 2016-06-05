package com.threemenstudio.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.threemenstudio.vampire.R;
import com.threemenstudio.adapter.StringAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateCharacterPlayerInfo extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Spinner natSpinner;
    private Spinner demSpinner;
    private Spinner claSpinner;
    private Spinner genSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character_player_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(getResources().getString(R.string.subtitle_activity_create_character));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveState();
                Snackbar.make(view, "Saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), CreateCharacterAttributes.class);
                startActivity(intent);
            }
        });

        natSpinner = (Spinner) findViewById(R.id.nature_sp);
        List<String> naturesList = new ArrayList<>();
        String[] natures = getResources().getStringArray(R.array.string_array_nature);
        Collections.addAll(naturesList, natures);
        StringAdapter natureAdapter = new StringAdapter(this, R.layout.spiner_item, naturesList);
        natureAdapter.setDropDownViewResource(R.layout.spiner_drop_down);
        natSpinner.setAdapter(natureAdapter);
        demSpinner = (Spinner) findViewById(R.id.demeanor_sp);
        demSpinner.setAdapter(natureAdapter);
        claSpinner = (Spinner) findViewById(R.id.clan_sp);
        String[] clans = getResources().getStringArray(R.array.string_array_clan);
        List<String> clanList = new ArrayList<>();
        Collections.addAll(clanList, clans);
        StringAdapter clanAdapter = new StringAdapter(this, R.layout.spiner_item, clanList);
        clanAdapter.setDropDownViewResource(R.layout.spiner_drop_down);
        claSpinner.setAdapter(clanAdapter);
        genSpinner = (Spinner) findViewById(R.id.generation_sp);
        String[] gens = getResources().getStringArray(R.array.string_array_generation);
        List<String> genList = new ArrayList<>();
        Collections.addAll(genList, gens);
        StringAdapter genAdapter = new StringAdapter(this, R.layout.spiner_item, genList);
        genAdapter.setDropDownViewResource(R.layout.spiner_drop_down);
        genSpinner.setAdapter(genAdapter);

        setData();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_character, menu);
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

        if(id == R.id.player){
            //we are here, do nothing
            saveState();
        }
        else if (id == R.id.attributes) {
            saveState();
            Intent intent = new Intent(getApplicationContext(), CreateCharacterAttributes.class);
            startActivity(intent);
        }
        else if (id == R.id.abilities) {
            saveState();
            Intent intent = new Intent(getApplicationContext(), CreateCharacterAbilities.class);
            startActivity(intent);
        }
        else if (id == R.id.advantages) {
            saveState();
            Intent intent = new Intent(getApplicationContext(), CreateCharacterAdvantages.class);
            startActivity(intent);
        }
        else if (id == R.id.other_traits) {
            saveState();
        }
        else if (id == R.id.night_stats) {
            saveState();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setData(){
        SharedPreferences sharedPref = this
                .getSharedPreferences("com.threemenstudio.vtm.PREFERENCE_FILE_KEY",
                        Context.MODE_PRIVATE);
        EditText nameText = (EditText) findViewById(R.id.Name);
        nameText.setText(sharedPref.getString("NAME",""));
        EditText playerText = (EditText) findViewById(R.id.Player);
        playerText.setText(sharedPref.getString("PLAYER",""));
        EditText chronicleText = (EditText) findViewById(R.id.Chronicle);
        chronicleText.setText(sharedPref.getString("CHRONICLE",""));
        String nature = sharedPref.getString("NATURE","");
        if(!nature.equals("")){
            String[] natures = getResources().getStringArray(R.array.string_array_nature);
            for(int i = 0; i < natures.length; i++){
                if(natures[i].equals(nature)){
                    natSpinner.setSelection(i);
                    break;
                }
            }
        }
        String demeanor = sharedPref.getString("DEMEANOR","");
        if(!demeanor.equals("")){
            String[] natures = getResources().getStringArray(R.array.string_array_nature);
            for(int i = 0; i < natures.length; i++){
                if(natures[i].equals(demeanor)){
                    demSpinner.setSelection(i);
                    break;
                }
            }
        }
        EditText conceptText = (EditText) findViewById(R.id.Concept);
        conceptText.setText(sharedPref.getString("CONCEPT",""));
        String clan = sharedPref.getString("CLAN","");
        if(!clan.equals("")){
            String[] clans = getResources().getStringArray(R.array.string_array_clan);
            for(int i = 0; i < clans.length; i++){
                if(clans[i].equals(clan)){
                    claSpinner.setSelection(i);
                    break;
                }
            }
        }
        String generation = sharedPref.getString("GENERATION","");
        if(!generation.equals("")){
            String[] gens = getResources().getStringArray(R.array.string_array_generation);
            for(int i = 0; i < gens.length; i++){
                if(gens[i].equals(generation)){
                    genSpinner.setSelection(i);
                    break;
                }
            }
        }
        EditText sireText = (EditText) findViewById(R.id.Sire);
        sireText.setText(sharedPref.getString("SIRE",""));
    }

    private void saveState(){
        SharedPreferences sharedPref = this
                .getSharedPreferences("com.threemenstudio.vtm.PREFERENCE_FILE_KEY",
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        EditText nameText = (EditText) findViewById(R.id.Name);
        String name = nameText.getText().toString();
        EditText playerText = (EditText) findViewById(R.id.Player);
        String player = playerText.getText().toString();
        EditText chronicleText = (EditText) findViewById(R.id.Chronicle);
        String chronicle = chronicleText.getText().toString();
        String nature = natSpinner.getSelectedItem().toString();
        String demeanor = demSpinner.getSelectedItem().toString();
        EditText conceptText = (EditText) findViewById(R.id.Concept);
        String concept = conceptText.getText().toString();
        String clan = claSpinner.getSelectedItem().toString();
        String generation = genSpinner.getSelectedItem().toString();
        EditText sireText = (EditText) findViewById(R.id.Sire);
        String sire = sireText.getText().toString();
        editor.putString("NAME", name);
        editor.putString("PLAYER", player);
        editor.putString("CHRONICLE",chronicle);
        editor.putString("NATURE", nature);
        editor.putString("DEMEANOR", demeanor);
        editor.putString("CONCEPT", concept);
        editor.putString("CLAN", clan);
        editor.putString("GENERATION", generation);
        editor.putString("SIRE",sire);
        editor.apply();
    }
}
