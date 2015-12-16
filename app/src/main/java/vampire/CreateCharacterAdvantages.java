package vampire;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threemenstudio.vampire.R;
import data.Radio;
import utilities.RadioUtils;

import java.util.List;

public class CreateCharacterAdvantages extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private RadioUtils radioUtils = new RadioUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character_advantages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(getResources().getString(R.string.subtitle_activity_create_character_advantages));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setDisciplines();
    }

    private void setDisciplines() {
        String[] disciplines = getResources().getStringArray(R.array.string_array_disciplines);
        SharedPreferences sharedPref = this
                .getSharedPreferences("com.threemenstudio.vtm.PREFERENCE_FILE_KEY",
                        Context.MODE_PRIVATE);
        for (String discipline : disciplines) {
            if(discipline.equals(getResources().getString(R.string.koldunic))){
                String[] koldunics = getResources().getStringArray(R.array.koldunic);
                for(String koldunic : koldunics){
                    int level = Integer.parseInt(sharedPref.getString(koldunic.toUpperCase(), "0"));
                    if (level > 0) {
                        createKoldunicLayout(koldunic);
                    }
                }
            }
            else{
                int level = Integer.parseInt(sharedPref.getString(discipline.toUpperCase(), "0"));
                if (level > 0) {
                    createDisciplineLayout(discipline);
                }
            }
        }

    }

    public void createDiscipline(View view){
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompt_disciplines, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        String[] disciplines = getResources().getStringArray(R.array.string_array_disciplines);
        for(int i = 0; i< disciplines.length; i++){
            int item = i + 1;
            String id = "diciplineText" + item;
            final TextView textView1 = (TextView) promptsView.findViewById(
                    this.getResources().getIdentifier(id, "id", this.getPackageName()));
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(textView1.getText().toString().equals(getResources().getString(R.string.koldunic))){
                        openKoldunicMenu(alertDialog);
                    }
                    else{
                        createDisciplineLayout(textView1.getText().toString());
                        alertDialog.cancel();
                    }
                }
            });
        }

    }

    private void openKoldunicMenu(final AlertDialog parent) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompt_koldunic, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder
                .setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        String[] koldunics = getResources().getStringArray(R.array.koldunic);
        for(int i = 0; i< koldunics.length; i++){
            int item = i + 1;
            String id = "koldunicText" + item;
            final TextView textView1 = (TextView) promptsView.findViewById(
                    this.getResources().getIdentifier(id, "id", this.getPackageName()));
            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createKoldunicLayout(textView1.getText().toString());
                    alertDialog.cancel();
                    parent.cancel();
                }
            });
        }
    }

    private void createKoldunicLayout(final String koldunic) {
        LinearLayout content = (LinearLayout) findViewById(R.id.content);
        LayoutInflater li = LayoutInflater.from(this);
        final View promptsView = li.inflate(R.layout.koldunic, null);
        TextView textView = (TextView) promptsView.findViewById(R.id.discipline);
        String text = "Koldunic Sorcery: " + koldunic;
        textView.setText(text);
        content.addView(promptsView);
        setLayout("disciplineRadioButton", koldunic.toUpperCase(), promptsView);
        ImageButton delete = (ImageButton) promptsView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewManager)promptsView.getParent()).removeView(promptsView);
                SharedPreferences sharedPref = getApplicationContext()
                        .getSharedPreferences("com.threemenstudio.vtm.PREFERENCE_FILE_KEY",
                                Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(koldunic.toUpperCase(), String.valueOf(0));
                editor.apply();
            }
        });
    }

    private void createDisciplineLayout(final String discipline){
        LinearLayout content = (LinearLayout) findViewById(R.id.content);
        LayoutInflater li = LayoutInflater.from(this);
        final View promptsView = li.inflate(R.layout.discipline,null);
        TextView textView = (TextView) promptsView.findViewById(R.id.discipline);
        textView.setText(discipline);
        content.addView(promptsView);
        setLayout("disciplineRadioButton", discipline.toUpperCase(), promptsView);
        ImageButton delete = (ImageButton) promptsView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewManager)promptsView.getParent()).removeView(promptsView);
                SharedPreferences sharedPref = getApplicationContext()
                        .getSharedPreferences("com.threemenstudio.vtm.PREFERENCE_FILE_KEY",
                                Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(discipline.toUpperCase(), String.valueOf(0));
                editor.apply();
            }
        });
    }

    private void setLayout(String radioId, String stat, View v){
        List<Radio> list = radioUtils.createList(radioId,
                v, this);
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
            Intent intent = new Intent(getApplicationContext(), CreateCharacterAbilities.class);
            startActivity(intent);
        }
        else if (id == R.id.advantages) {
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
