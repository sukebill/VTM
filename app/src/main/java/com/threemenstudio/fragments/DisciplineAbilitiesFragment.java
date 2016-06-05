package com.threemenstudio.fragments;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.threemenstudio.data.Ability;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.interfaces.DisciplineAbilitiesInterface;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.AbilityBinding;
import com.threemenstudio.vampire.databinding.FragmentDisciplineAbilitiesBinding;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Shitman on 5/6/2016.
 */
public class DisciplineAbilitiesFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static String disciplineId;
    private static String discipline;
    private static String official;
    private static LinkedHashMap<Integer, List<Boolean>> opened;
    private static Context context;
    private static int minLevel;

    public DisciplineAbilitiesFragment() {
    }

    public static DisciplineAbilitiesFragment newInstance(int sectionNumber, String disciplineId,
                                                          String discipline, String official,
                                                          LinkedHashMap<Integer, List<Boolean>> opened,
                                                          Context context, int minLevel) {

        DisciplineAbilitiesFragment fragment = new DisciplineAbilitiesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        DisciplineAbilitiesFragment.discipline = discipline;
        DisciplineAbilitiesFragment.disciplineId = disciplineId;
        DisciplineAbilitiesFragment.official = official;
        DisciplineAbilitiesFragment.opened = opened;
        DisciplineAbilitiesFragment.context = context;
        DisciplineAbilitiesFragment.minLevel = minLevel;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentDisciplineAbilitiesBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_discipline_abilities, container, false);
        int message;
        if(discipline.equals("Celerity") | discipline.equals("Fortitude") | discipline.equals("Potence")){
            message = getArguments().getInt(ARG_SECTION_NUMBER) + minLevel - 1;
        }
        else{
            message = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        final int trueMessage = message;

        ((DisciplineAbilitiesInterface)context).setRadio(message, binding.getRoot());

        List<Ability> abilities = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db;
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            abilities = vtmDb.getAAbilitiesOfDisciplineByLevel(db, Integer.parseInt(disciplineId), message + 1);
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        if(abilities.size() == 1){

            AbilityBinding abilityBinding = DataBindingUtil.inflate(inflater, R.layout.ability, container, false);
            abilityBinding.setAbility(abilities.get(0));
            abilityBinding.arrow.setVisibility(View.GONE);
            binding.abilities.addView(abilityBinding.getRoot());

        }
        else{

            final List<Boolean> list = new ArrayList<>();

            for(int i = 0; i < abilities.size(); i++){

                list.add(false);
                final AbilityBinding abilityBinding = DataBindingUtil.inflate(inflater, R.layout.ability, container, false);
                abilityBinding.setAbility(abilities.get(i));
                if(opened.containsKey(message)){

                    if(opened.get(message).get(i)){

                        abilityBinding.child.setVisibility(View.VISIBLE);

                    }
                    else{

                        abilityBinding.child.setVisibility(View.GONE);

                    }

                }
                else{

                    abilityBinding.child.setVisibility(View.GONE);

                }
                abilityBinding.child.setPadding(20, 0, 0, 0);
                final int finalI = i;
                abilityBinding.header.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (opened.get(trueMessage).get(finalI)) {

                            abilityBinding.child.setVisibility(View.GONE);
                            opened.get(trueMessage).set(finalI, false);
                            abilityBinding.arrow.setImageResource(android.R.drawable.arrow_down_float);

                        } else {

                            abilityBinding.child.setVisibility(View.VISIBLE);
                            opened.get(trueMessage).set(finalI, true);
                            abilityBinding.arrow.setImageResource(android.R.drawable.arrow_up_float);

                        }

                    }

                });
                binding.abilities.addView(abilityBinding.getRoot());
            }
            if(!opened.containsKey(message)){

                opened.put(message,list);

            }
        }
        return binding.getRoot();
    }
}