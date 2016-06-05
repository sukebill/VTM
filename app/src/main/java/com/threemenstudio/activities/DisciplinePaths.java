package com.threemenstudio.activities;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.vampire.R;

import java.util.ArrayList;
import java.util.List;

import com.threemenstudio.data.Path;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;

public class DisciplinePaths extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline_paths);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String disciplineId = extras.getString(VTMApplication.getExtraDiscipline());
        String discipline = extras.getString(VTMApplication.getExtraTitle());
        String official = extras.getString(VTMApplication.getExtraOfficial());

        getSupportActionBar().setTitle(discipline);

        TextView off = (TextView) findViewById(R.id.official_desc);
        off.setText(official);

        if(official == null){
            findViewById(R.id.ll_official).setVisibility(View.GONE);
        }

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db;
        List<Path> paths = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            paths = vtmDb.getPathsOfDiscipline(db, Integer.parseInt(disciplineId));
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        for(int i = 0; i < paths.size(); i++){
            LinearLayout path = (LinearLayout) LinearLayout.inflate(context, R.layout.path, null);
            TextView title = (TextView) path.findViewById(R.id.title);
            String[] name = paths.get(i).getName().split(" - ");
            switch (discipline) {
                case "Assamite Sorcery":
                    title.setText(name[0]);
                    break;
                case "Thaumaturgy":
                    switch (name[0]) {
                        case "Yaksha-Vidya":
                            title.setText(name[1]);
                            break;
                        case "Echo of Nirvana":
                            title.setText(name[1]);
                            break;
                        case "Hand of the Magi":
                            title.setText(name[1]);
                            break;
                        case "Vengeance of Khnum":
                            title.setText(name[1]);
                            break;
                        case "Covenant of Enki":
                            title.setText(name[1]);
                            break;
                        case "Lakshimi's Wishes":
                            title.setText(name[1]);
                            break;
                        case "Life's Water":
                            title.setText(name[1]);
                            break;
                        case "Jinn's Gift":
                            title.setText(name[1]);
                            break;
                        case "The False Heart":
                            title.setText(name[1]);
                            break;
                        case "Sebau's Touch":
                            title.setText(name[1]);
                            break;
                        case "Path of the Ailing Jackal":
                            title.setText(name[1]);
                            break;
                        case "Valour of Sutekh":
                            title.setText(name[1]);
                            break;
                        case "Suleiman's Laws":
                            title.setText(name[1]);
                            break;
                        default:
                            title.setText(name[0]);
                            break;
                    }
                    break;
                default:
                    title.setText(paths.get(i).getName());
                    break;
            }
            LinearLayout header = (LinearLayout) path.findViewById(R.id.header);
            final int id = paths.get(i).getId();
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PathInfo.class);
                    intent.putExtra(VTMApplication.getExtraPath(), String.valueOf(id));
                    startActivity(intent);
                }
            });
            LinearLayout scrollView = (LinearLayout) findViewById(R.id.paths);
            scrollView.addView(path);
        }
    }

}
