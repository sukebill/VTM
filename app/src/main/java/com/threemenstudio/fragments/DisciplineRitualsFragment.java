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
import android.widget.TextView;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.data.Ritual;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.interfaces.PagerRadioButtonInterface;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.FragmentDisciplineRitualsBinding;
import com.threemenstudio.vampire.databinding.RitualBinding;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Shitman on 11/6/2016.
 */
public class DisciplineRitualsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static String discipline;
    private static String ritual;
    private static String ritualSystem;
    private static String disciplineId;
    private static List<Ritual> variable;
    private static LinkedHashMap<Integer,List<Boolean>> opened = new LinkedHashMap<>();
    private static Context context;

    public DisciplineRitualsFragment() {
    }

    public static DisciplineRitualsFragment newInstance(int sectionNumber, Context context,
                                                        String disciplineId, String discipline,
                                                        String ritual, String ritualSystem,
                                                        List<Ritual> variable) {

        DisciplineRitualsFragment fragment = new DisciplineRitualsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        DisciplineRitualsFragment.context = context;
        DisciplineRitualsFragment.disciplineId = disciplineId;
        DisciplineRitualsFragment.discipline = discipline;
        DisciplineRitualsFragment.ritual = ritual;
        DisciplineRitualsFragment.ritualSystem = ritualSystem;
        DisciplineRitualsFragment.variable = variable;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentDisciplineRitualsBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_discipline_rituals, container, false);
        int message = getArguments().getInt(ARG_SECTION_NUMBER);

        if(discipline.equals("Koldunic Sorcery")){
            if(message == 6){
                message = message + 2;
            }
            ((PagerRadioButtonInterface)context).setRadioKoldunic(message, binding.getRoot());
        }
        else{
            ((PagerRadioButtonInterface)context).setRadio(message, binding.getRoot());
        }

        binding.ritualDesc.setText(ritual);
        binding.systemDesc.setText(ritualSystem);

        if(ritual == null){
            binding.llDesc.setVisibility(View.GONE);
        }
        if(ritualSystem == null){
            binding.system.setVisibility(View.GONE);
        }
        DbHelper dbHelper = new DbHelper(VTMApplication.getContext());
        SQLiteDatabase db;
        List<Ritual> rituals = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            rituals = vtmDb.getRitualsOfDisciplineByLevel(db, Integer.parseInt(disciplineId), message + 1);
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        if(rituals.size() == 0){
            //variable time
            if(variable.size() == 1){
                setOneRitual(variable, binding, inflater);
            }
            else{
                setManyRituals(variable, binding, message, inflater);
            }
        }
        else if(rituals.size() == 1){
            setOneRitual(rituals, binding, inflater);
        }
        else{
            setManyRituals(rituals, binding, message, inflater);
        }
        return binding.getRoot();
    }

    private void setOneRitual(List<Ritual> rituals, FragmentDisciplineRitualsBinding binding,
                              LayoutInflater inflater){

        RitualBinding ritualBinding = DataBindingUtil.inflate(inflater, R.layout.ritual, null, false);
        String[] name = rituals.get(0).getName().split(" - ");
        if(discipline.equals("Assamite Sorcery")){
            ritualBinding.title.setText(name[0]);
        }
        else{
            ritualBinding.title.setText(rituals.get(0).getName());
        }
        ritualBinding.desc.setText(rituals.get(0).getDescription());
        ritualBinding.systemDesc.setText(rituals.get(0).getSystem());
        if(rituals.get(0).getSystem() == null){
            ritualBinding.system.setVisibility(View.GONE);
            ritualBinding.systemDesc.setVisibility(View.GONE);
        }
        ritualBinding.arrow.setVisibility(View.GONE);
        ritualBinding.child.setVisibility(View.VISIBLE);
        ritualBinding.child.setPadding(0,0,0,0);
        binding.rituals.addView(ritualBinding.getRoot());

    }

    private void setManyRituals(List<Ritual> rituals, FragmentDisciplineRitualsBinding rootView,
                                final int message, LayoutInflater inflater){

        final List<Boolean> list = new ArrayList<>();
        for(int i = 0; i < rituals.size(); i++) {

            list.add(false);
            final RitualBinding ritualBinding = DataBindingUtil.inflate(inflater, R.layout.ritual, null, false);
            String[] name = rituals.get(i).getName().split(" - ");
            switch (discipline) {
                case "Assamite Sorcery":
                    ritualBinding.title.setText(name[0]);
                    break;
                case "Dark Thaumaturgy":
                    ritualBinding.title.setText(name[0]);
                    break;
                case ("Thaumaturgy"):
                    setThaumaturgy(name, ritualBinding.title);
                    break;
                default:
                    ritualBinding.title.setText(rituals.get(i).getName());
                    break;
            }
            if(opened.containsKey(message)){

                if(opened.get(message).get(i)){

                    ritualBinding.child.setVisibility(View.VISIBLE);

                }
                else{

                    ritualBinding.child.setVisibility(View.GONE);

                }
            }
            else{

                ritualBinding.child.setVisibility(View.GONE);

            }
            ritualBinding.desc.setText(rituals.get(i).getDescription());
            ritualBinding.systemDesc.setText(rituals.get(i).getSystem());
            if(rituals.get(i).getSystem() == null){
                ritualBinding.system.setVisibility(View.GONE);
                ritualBinding.systemDesc.setVisibility(View.GONE);
            }
            final int finalI = i;
            ritualBinding.header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (opened.get(message).get(finalI)) {
                        ritualBinding.child.setVisibility(View.GONE);
                        opened.get(message).set(finalI, false);
                        ritualBinding.arrow.setImageResource(android.R.drawable.arrow_down_float);
                    } else {
                        ritualBinding.child.setVisibility(View.VISIBLE);
                        opened.get(message).set(finalI, true);
                        ritualBinding.arrow.setImageResource(android.R.drawable.arrow_up_float);
                    }
                }
            });
            rootView.rituals.addView(ritualBinding.getRoot());
        }
        if(!opened.containsKey(message)){

            opened.put(message,list);

        }
    }

    private void setThaumaturgy(String[] name, TextView title){

        switch (name[0]){

            case "Black Sunrise":
                title.setText(name[1]);
                break;
            case "Curtain of Will":
                title.setText(name[1]);
                break;
            case "Falsely Sealed Vessel":
                title.setText(name[1]);
                break;
            case "Loyal Eyes":
                title.setText(name[1]);
                break;
            case "Shepherd's Silent Vigil":
                title.setText(name[1]);
                break;
            case "Speak with Sire":
                title.setText(name[1]);
                break;
            case "Draught of the Pebble":
                title.setText(name[1]);
                break;
            case "Follow the Lie":
                title.setText(name[1]);
                break;
            case "Run to Judgment":
                title.setText(name[1]);
                break;
            case "Haqim's Disfavor":
                title.setText(name[1]);
                break;
            case "Stain of Guilt":
                title.setText(name[1]);
                break;
            default:
                title.setText(name[0]);

        }

    }

}