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
import com.threemenstudio.activities.DisciplineAbilities;
import com.threemenstudio.activities.DisciplinePaths;
import com.threemenstudio.activities.DisciplineRituals;
import com.threemenstudio.activities.SetiteSorceryPaths;
import com.threemenstudio.activities.SetiteSorceryRituals;
import com.threemenstudio.data.Ability;
import com.threemenstudio.data.Clan;
import com.threemenstudio.data.Discipline;
import com.threemenstudio.data.Path;
import com.threemenstudio.data.Ritual;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.FragmentDisciplineInfoBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shitman on 5/6/2016.
 */
public class DisciplineInfoFragment extends Fragment {

    private static List<Discipline> disciplines;
    private static Context context;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public DisciplineInfoFragment() {
    }

    public static DisciplineInfoFragment newInstance(int sectionNumber, List<Discipline> disciplines,
                                                     Context context) {

        DisciplineInfoFragment fragment = new DisciplineInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        DisciplineInfoFragment.disciplines = disciplines;
        DisciplineInfoFragment.context = context;
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentDisciplineInfoBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_discipline_info, container, false);
        final int message = getArguments().getInt(ARG_SECTION_NUMBER);
        final Discipline discipline = disciplines.get(message);
        binding.discipline.setText(discipline.getTitle());
        binding.desc.setText(discipline.getDescription());
        if (discipline.getBonusDescription() != null) {
            binding.bonusDesc.setText(discipline.getBonusDescription());
        } else {
            binding.bonusDesc.setVisibility(View.GONE);
        }

        DbHelper dbHelper = new DbHelper(VTMApplication.getContext());
        SQLiteDatabase db;
        List<Ability> abilities = new ArrayList<>();
        List<Clan> clans = new ArrayList<>();
        List<Path> paths = new ArrayList<>();
        List<Ritual> rituals = new ArrayList<>();
        try {
            dbHelper.openDataBase();
            db = dbHelper.getReadableDatabase();
            VtmDb vtmDb = new VtmDb();
            clans = vtmDb.getInclan(db, discipline.getId());
            abilities = vtmDb.getAbilitiesOfDiscipline(db, discipline.getId());
            paths = vtmDb.getPathsOfDiscipline(db, discipline.getId());
            rituals = vtmDb.getRitualsOfDiscipline(db, discipline.getId());
            dbHelper.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        for (int i = 1; i < clans.size() + 1; i++) {
            String clanDiscipline = "clan_" + i;
            TextView inClan = (TextView) binding.getRoot().findViewById(context.getResources().getIdentifier(clanDiscipline
                    , "id", context.getPackageName()));
            if (clans.get(i - 1).getCaste() != null) {
                inClan.setText(clans.get(i - 1).getCaste());
            } else {
                inClan.setText(clans.get(i - 1).getClan());
            }
            inClan.setVisibility(View.VISIBLE);
        }
        if (clans.size() == 0) {
            binding.clan.setVisibility(View.GONE);
        }

        if (abilities.size() > 0) {

            binding.fabAbilities.setVisibility(View.VISIBLE);
            binding.fabAbilities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DisciplineAbilities.class);
                    intent.putExtra(VTMApplication.getExtraDiscipline(), String.valueOf(discipline.getId()));
                    intent.putExtra(VTMApplication.getExtraTitle(), discipline.getTitle());
                    intent.putExtra(VTMApplication.getExtraOfficial(), discipline.getOfficialAbilities());
                    startActivity(intent);

                }
            });

        }
        if (paths.size() > 0) {

            binding.fabPaths.setVisibility(View.VISIBLE);
            binding.fabPaths.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (discipline.getTitle().equals("Setite Sorcery")) {

                        Intent intent = new Intent(context, SetiteSorceryPaths.class);
                        intent.putExtra(VTMApplication.getExtraDiscipline(), String.valueOf(discipline.getId()));
                        intent.putExtra(VTMApplication.getExtraTitle(), discipline.getTitle());
                        startActivity(intent);

                    } else {

                        Intent intent = new Intent(context, DisciplinePaths.class);
                        intent.putExtra(VTMApplication.getExtraDiscipline(), String.valueOf(discipline.getId()));
                        intent.putExtra(VTMApplication.getExtraTitle(), discipline.getTitle());
                        intent.putExtra(VTMApplication.getExtraOfficial(), discipline.getOfficialAbilities());
                        startActivity(intent);

                    }

                }

            });

        }

        if (rituals.size() > 0) {

            binding.fabRituals.setVisibility(View.VISIBLE);
            binding.fabRituals.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (discipline.getTitle().equals("Setite Sorcery")) {

                        Intent intent = new Intent(context, SetiteSorceryRituals.class);
                        intent.putExtra(VTMApplication.getExtraDiscipline(), String.valueOf(discipline.getId()));
                        intent.putExtra(VTMApplication.getExtraTitle(), discipline.getTitle());
                        startActivity(intent);

                    } else {

                        Intent intent = new Intent(context, DisciplineRituals.class);
                        intent.putExtra(VTMApplication.getExtraDiscipline(), String.valueOf(discipline.getId()));
                        intent.putExtra(VTMApplication.getExtraTitle(), discipline.getTitle());
                        intent.putExtra(VTMApplication.getExtraRitual(), discipline.getRituals());
                        intent.putExtra(VTMApplication.getExtraRitualSystem(), discipline.getRitualsSystem());
                        startActivity(intent);

                    }
                }

            });

        }

        return binding.getRoot();
    }
}