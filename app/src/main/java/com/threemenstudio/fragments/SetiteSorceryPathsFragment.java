package com.threemenstudio.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.activities.PathInfo;
import com.threemenstudio.data.Path;
import com.threemenstudio.data.Sorcery;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.FragmentSetiteSorceryBinding;
import com.threemenstudio.vampire.databinding.PathBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vassili Alexantrov on 12/6/2016.
 */
public class SetiteSorceryPathsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static List<Sorcery> sorceries;
    private static Context context;

    public SetiteSorceryPathsFragment() {
    }


    public static SetiteSorceryPathsFragment newInstance(int sectionNumber, List<Sorcery> sorceries,
                                                         Context context) {

        SetiteSorceryPathsFragment fragment = new SetiteSorceryPathsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        SetiteSorceryPathsFragment.context = context;
        SetiteSorceryPathsFragment.sorceries = sorceries;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentSetiteSorceryBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_setite_sorcery, container, false);
        final int message = getArguments().getInt(ARG_SECTION_NUMBER);
        binding.desc.setText(sorceries.get(message).getDesc());
        if(sorceries.get(message).getSocial() == null){
            binding.social.setVisibility(View.GONE);
        }
        else{
            binding.socialDesc.setText(sorceries.get(message).getSocial());
        }

        setPaths(binding, message, inflater);

        return binding.getRoot();
    }

    private void setPaths(FragmentSetiteSorceryBinding binding, int message, LayoutInflater inflater){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db;
        List<Path> paths = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            paths = vtmDb.getPathsOfSorcery(db, sorceries.get(message).getId());
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        for(int i = 0; i < paths.size(); i++){

            PathBinding pathBinding = DataBindingUtil.inflate(inflater, R.layout.path, null, false);
            String[] name = paths.get(i).getName().split(" - ");
            if(message == 0){
                setAkhu(name, pathBinding.title);
            }
            if(message == 1){
                setSadhana(name, pathBinding.title);
            }
            if(message == 2){
                setWanga(name, pathBinding.title);
            }
            final int id = paths.get(i).getId();
            pathBinding.header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PathInfo.class);
                    intent.putExtra(VTMApplication.getExtraPath(), String.valueOf(id));
                    startActivity(intent);

                }
            });
            binding.scroller.addView(pathBinding.getRoot());

        }
    }

    private void setAkhu(String[] name , TextView title){
        switch (name[0]) {
            case "Jinn's Gift":
                title.setText(name[1]);
                break;
            case "Echo of Nirvana":
                title.setText(name[2]);
                break;
            case "Suleiman's Laws":
                title.setText(name[2]);
                break;
            case "Weather Control":
                title.setText(name[1]);
                break;
            default:
                title.setText(name[0]);
                break;
        }
    }

    private void setSadhana(String[] name , TextView title){
        switch (name[0]) {
            case "Alchemy":
                title.setText(name[1]);
                break;
            case "Hands of Destruction":
                title.setText(name[1]);
                break;
            case "The Movement of the Mind":
                title.setText(name[1]);
                break;
            case "The Snake Inside":
                title.setText(name[1]);
                break;
            case "Suleiman's Laws":
                title.setText(name[3]);
                break;
            case "Weather Control":
                title.setText(name[1]);
                break;
            case "Jinn's Gift":
                title.setText(name[1]);
                break;
            default:
                title.setText(name[0]);
                break;
        }
    }

    private void setWanga(String[] name , TextView title){
        switch (name[0]) {
            case "Jinn's Gift":
                title.setText(name[1]);
                break;
            case "Life's Water":
                title.setText(name[1]);
                break;
            case "The False Heart":
                title.setText(name[1]);
                break;
            case "Suleiman's Laws":
                title.setText(name[1]);
                break;
            case "Sebau's Touch":
                title.setText(name[1]);
                break;
            default:
                title.setText(name[0]);
                break;
        }
    }
}