package com.threemenstudio.activities;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.adapter.PathInfoAdapter;
import com.threemenstudio.data.Ability;
import com.threemenstudio.data.Path;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.interfaces.PagerRadioButtonInterface;
import com.threemenstudio.utilities.DepthPageTransformer;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.ActivityPathInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class PathInfo extends AppCompatActivity implements PagerRadioButtonInterface{

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ActivityPathInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_path_info);

        setSupportActionBar(binding.toolbar);

        getSupportActionBar().setTitle("Path Information");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String pathId = extras.getString(VTMApplication.getExtraPath());

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        Path path = new Path();
        List<Ability> abilities = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            path = vtmDb.getPathById(db, Integer.parseInt(pathId));
            abilities = vtmDb.getAbilitiesOfPath(db, Integer.parseInt(pathId));
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        PathInfoAdapter mSectionsPagerAdapter = new PathInfoAdapter(getSupportFragmentManager(), abilities, this, path);

        // Set up the ViewPager with the sections adapter.
        binding.container.setAdapter(mSectionsPagerAdapter);
        binding.container.setPageTransformer(true, new DepthPageTransformer());

    }

    public void setPage(int position){
        binding.container.setCurrentItem(position);
    }

    public void setRadio(int message, View rootView){

        for(int i = 0; i < 5; i++){

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

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void setRadioKoldunic(int message, View rootView) {
        //do nothing
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_path_info, menu);
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
