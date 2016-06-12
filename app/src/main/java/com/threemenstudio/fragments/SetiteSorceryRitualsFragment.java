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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threemenstudio.data.Ritual;
import com.threemenstudio.data.Sorcery;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.FragmentSetiteSorceryRitualsBinding;
import com.threemenstudio.vampire.databinding.LevelBinding;
import com.threemenstudio.vampire.databinding.RitualOfSorceryBinding;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Vassili Alexantrov on 12/6/2016.
 */
public class SetiteSorceryRitualsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static List<Sorcery> sorceries;
    private static Context context;
    private static LinkedHashMap<String,List<Boolean>> opened =  new LinkedHashMap<>();

    public SetiteSorceryRitualsFragment() {
    }

    public static SetiteSorceryRitualsFragment newInstance(int sectionNumber, List<Sorcery> sorceries,
                                                           Context context) {

        SetiteSorceryRitualsFragment fragment = new SetiteSorceryRitualsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        SetiteSorceryRitualsFragment.sorceries = sorceries;
        SetiteSorceryRitualsFragment.context = context;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentSetiteSorceryRitualsBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_setite_sorcery_rituals, container, false);
        final int message = getArguments().getInt(ARG_SECTION_NUMBER);
        binding.desc.setText(sorceries.get(message).getDesc());
        if(sorceries.get(message).getRituals() == null){
            binding.ritual.setVisibility(View.GONE);
        }
        else{
            binding.desc.setText(sorceries.get(message).getRituals());
        }
        if(sorceries.get(message).getRitualPractice() == null){
            binding.ritualPractice.setVisibility(View.GONE);
        }
        else{
            binding.practiceDesc.setText(sorceries.get(message).getRitualPractice());
        }

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db;
        LinkedHashMap<Integer, List<Ritual>> hashMap = new LinkedHashMap<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            int maxLevel = vtmDb.getMaxLevelOfRitualsForSorcery(db, sorceries.get(message).getId());
            for(int i = 0; i < maxLevel + 1; i++){
                //hashMap.put(i, vtmDb.getRitualOfSetiteSorceryByLevel(db, sorceries.get(message).getId(), i));
                List<Ritual> rituals = vtmDb.getRitualOfSetiteSorceryByLevel(db, sorceries.get(message).getId(), i);
                if(rituals.size() > 0){
                    //lets create a layout
                    setManyRituals(rituals, binding, message, String.valueOf(i), inflater);
                }
            }
            dbHelper.close();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        return binding.getRoot();
    }

    private void setManyRituals(List<Ritual> rituals, FragmentSetiteSorceryRitualsBinding binding,
                                final int message, final String j, LayoutInflater inflater){

        final List<Boolean> list = new ArrayList<>();
        LinearLayout level = (LinearLayout) LinearLayout.inflate(context, R.layout.level, null);
        for(int i = 0; i < rituals.size(); i++) {

            list.add(false);
            final String key = message + j;
            final RitualOfSorceryBinding ritualOfSorceryBinding =
                    DataBindingUtil.inflate(inflater, R.layout.ritual_of_sorcery, null, false);
            ritualOfSorceryBinding.title.setText(rituals.get(i).getName());
            if(opened.containsKey(key)){

                if(opened.get(key).get(i)){
                    ritualOfSorceryBinding.child.setVisibility(View.VISIBLE);
                }
                else{
                    ritualOfSorceryBinding.child.setVisibility(View.GONE);
                }

            }
            else{
                ritualOfSorceryBinding.child.setVisibility(View.GONE);
            }
            ritualOfSorceryBinding.desc.setText(rituals.get(i).getDescription());
            ritualOfSorceryBinding.systemDesc.setText(rituals.get(i).getSystem());
            if(rituals.get(i).getSystem() == null){

                ritualOfSorceryBinding.system.setVisibility(View.GONE);
                ritualOfSorceryBinding.systemDesc.setVisibility(View.GONE);

            }
            final int finalI = i;
            ritualOfSorceryBinding.header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (opened.get(key).get(finalI)) {

                        ritualOfSorceryBinding.child.setVisibility(View.GONE);
                        opened.get(key).set(finalI, false);
                        ritualOfSorceryBinding.arrow.setImageResource(android.R.drawable.arrow_down_float);

                    } else {

                        ritualOfSorceryBinding.child.setVisibility(View.VISIBLE);
                        opened.get(key).set(finalI, true);
                        ritualOfSorceryBinding.arrow.setImageResource(android.R.drawable.arrow_up_float);

                    }
                }
            });
            level.addView(ritualOfSorceryBinding.getRoot());

        }
        if(Integer.parseInt(j) == 0){
            ((TextView)level.findViewById(R.id.level)).setText("Variable Level");
        }
        else{
            ((TextView)level.findViewById(R.id.level)).setText("Level " + j);
        }
        binding.scroller.addView(level);
        if(!opened.containsKey(message + j)){
            opened.put(message + j, list);
        }
    }

}
