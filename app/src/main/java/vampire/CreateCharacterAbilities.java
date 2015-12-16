package vampire;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.threemenstudio.vampire.R;
import data.Radio;
import utilities.RadioUtils;

import java.util.List;

public class CreateCharacterAbilities extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RadioUtils radioUtils = new RadioUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character_abilities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(getResources().getString(R.string.subtitle_activity_create_character_abilities));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CreateCharacterAbilities2.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setLayout("alertnessRadioButton", "ALERTNESS");
        setLayout("athleticsRadioButton", "ATHLETICS");
        setLayout("brawlRadioButton", "BRAWL");
        setLayout("dodgeRadioButton", "DODGE");
        setLayout("expressionRadioButton", "EXPRESSION");
        setLayout("empathyRadioButton", "EMPATHY");
        setLayout("intimidationRadioButton", "INTIMIDATION");
        setLayout("leadershipRadioButton", "LEADERSHIP");
        setLayout("streetwiseRadioButton", "STREETWISE");
        setLayout("subterfugeRadioButton", "SUBTERFUGE");

    }

    private void setLayout(String radioId, String stat){
        List<Radio> list = radioUtils.createList(radioId,
                this.findViewById(android.R.id.content).getRootView(), this);
        radioUtils.createRadio(list, this, stat);
        radioUtils.setRadioButton(list, this, stat);
    }

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
        getMenuInflater().inflate(R.menu.create_character_attributes, menu);
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
            Intent intent = new Intent(getApplicationContext(), CreateCharacterPlayerInfo.class);
            startActivity(intent);
        }
        else if (id == R.id.attributes) {
            Intent intent = new Intent(getApplicationContext(), CreateCharacterAttributes.class);
            startActivity(intent);
        }
        else if (id == R.id.abilities) {
        }
        else if (id == R.id.advantages) {
            Intent intent = new Intent(getApplicationContext(), CreateCharacterAdvantages.class);
            startActivity(intent);
        }
        else if (id == R.id.other_traits) {
        }
        else if (id == R.id.night_stats) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
