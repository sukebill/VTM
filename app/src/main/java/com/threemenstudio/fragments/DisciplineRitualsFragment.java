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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.data.Ritual;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.interfaces.PagerRadioButtonInterface;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.FragmentDisciplineRitualsBinding;

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
                setOneRitual(variable, binding);
            }
            else{
                setManyRituals(variable, binding, message);
            }
        }
        else if(rituals.size() == 1){
            setOneRitual(rituals, binding);
        }
        else{
            setManyRituals(rituals, binding, message);
        }
        return binding.getRoot();
    }

    private void setOneRitual(List<Ritual> rituals, FragmentDisciplineRitualsBinding rootView){

        LinearLayout ritual = (LinearLayout) LinearLayout.inflate(context, R.layout.ritual, null);
        TextView title = (TextView) ritual.findViewById(R.id.title);
        String[] name = rituals.get(0).getName().split(" - ");
        if(discipline.equals("Assamite Sorcery")){
            title.setText(name[0]);
        }
        else{
            title.setText(rituals.get(0).getName());
        }
        TextView desc = (TextView) ritual.findViewById(R.id.desc);
        desc.setText(rituals.get(0).getDescription());
        TextView systemDesc = (TextView) ritual.findViewById(R.id.system_desc);
        systemDesc.setText(rituals.get(0).getSystem());
        if(rituals.get(0).getSystem() == null){
            (ritual.findViewById(R.id.system)).setVisibility(View.GONE);
            (ritual.findViewById(R.id.system_desc)).setVisibility(View.GONE);
        }
        ritual.findViewById(R.id.arrow).setVisibility(View.GONE);
        ritual.findViewById(R.id.child).setVisibility(View.VISIBLE);
        ritual.findViewById(R.id.child).setPadding(0,0,0,0);
        rootView.rituals.addView(ritual);

    }

    private void setManyRituals(List<Ritual> rituals, FragmentDisciplineRitualsBinding rootView, final int message){

        final List<Boolean> list = new ArrayList<>();
        for(int i = 0; i < rituals.size(); i++) {
            list.add(false);
            final LinearLayout ritual = (LinearLayout) LinearLayout.inflate(context, R.layout.ritual, null);
            TextView title = (TextView) ritual.findViewById(R.id.title);
            String[] name = rituals.get(i).getName().split(" - ");
            switch (discipline) {
                case "Assamite Sorcery":
                    title.setText(name[0]);
                    break;
                case "Dark Thaumaturgy":
                    title.setText(name[0]);
                    break;
                case ("Thaumaturgy"):
                    setThaumaturgy(name, title);
                    break;
                default:
                    title.setText(rituals.get(i).getName());
                    break;
            }
            final LinearLayout child = (LinearLayout) ritual.findViewById(R.id.child);
            if(opened.containsKey(message)){

                if(opened.get(message).get(i)){

                    child.setVisibility(View.VISIBLE);

                }
                else{

                    child.setVisibility(View.GONE);

                }
            }
            else{

                child.setVisibility(View.GONE);

            }
            TextView desc = (TextView) ritual.findViewById(R.id.desc);
            desc.setText(rituals.get(i).getDescription());
            TextView systemDesc = (TextView) ritual.findViewById(R.id.system_desc);
            systemDesc.setText(rituals.get(i).getSystem());
            if(rituals.get(i).getSystem() == null){
                (ritual.findViewById(R.id.system)).setVisibility(View.GONE);
                (ritual.findViewById(R.id.system_desc)).setVisibility(View.GONE);
            }
            LinearLayout header = (LinearLayout) ritual.findViewById(R.id.header);
            final int finalI = i;
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (opened.get(message).get(finalI)) {
                        child.setVisibility(View.GONE);
                        opened.get(message).set(finalI, false);
                        ((ImageView)ritual.findViewById(R.id.arrow)).setImageResource(android.R.drawable.arrow_down_float);
                    } else {
                        child.setVisibility(View.VISIBLE);
                        opened.get(message).set(finalI, true);
                        ((ImageView)ritual.findViewById(R.id.arrow)).setImageResource(android.R.drawable.arrow_up_float);
                    }
                }
            });
            rootView.rituals.addView(ritual);
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