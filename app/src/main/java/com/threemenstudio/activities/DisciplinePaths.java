package com.threemenstudio.activities;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.data.Path;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.ActivityDisciplinePathsBinding;
import com.threemenstudio.vampire.databinding.PathBinding;

import java.util.ArrayList;
import java.util.List;

public class DisciplinePaths extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ActivityDisciplinePathsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_discipline_paths);
        setSupportActionBar(binding.toolbar);

        final Context context = this;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String disciplineId = extras.getString(VTMApplication.getExtraDiscipline());
        String discipline = extras.getString(VTMApplication.getExtraTitle());
        String official = extras.getString(VTMApplication.getExtraOfficial());

        getSupportActionBar().setTitle(discipline);
        binding.content.officialDesc.setText(official);
        if(official == null){
            binding.content.llOfficial.setVisibility(View.GONE);
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

            PathBinding pathBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.path, null, false);
            String[] name = paths.get(i).getName().split(" - ");
            if (discipline != null) {
                switch (discipline) {
                    case "Assamite Sorcery":
                        pathBinding.title.setText(name[0]);
                        break;
                    case "Thaumaturgy":
                        switch (name[0]) {
                            case "Yaksha-Vidya":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "Echo of Nirvana":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "Hand of the Magi":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "Vengeance of Khnum":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "Covenant of Enki":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "Lakshimi's Wishes":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "Life's Water":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "Jinn's Gift":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "The False Heart":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "Sebau's Touch":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "Path of the Ailing Jackal":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "Valour of Sutekh":
                                pathBinding.title.setText(name[1]);
                                break;
                            case "Suleiman's Laws":
                                pathBinding.title.setText(name[1]);
                                break;
                            default:
                                pathBinding.title.setText(name[0]);
                                break;
                        }
                        break;
                    default:
                        pathBinding.title.setText(paths.get(i).getName());
                        break;
                }
            }
            final int id = paths.get(i).getId();
            pathBinding.header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PathInfo.class);
                    intent.putExtra(VTMApplication.getExtraPath(), String.valueOf(id));
                    startActivity(intent);
                    overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

                }
            });
            binding.content.paths.addView(pathBinding.getRoot());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
