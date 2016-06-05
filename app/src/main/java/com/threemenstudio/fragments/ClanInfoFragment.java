package com.threemenstudio.fragments;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.data.Clan;
import com.threemenstudio.data.Discipline;
import com.threemenstudio.database.DbHelper;
import com.threemenstudio.database.VtmDb;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.FragmentClanInfoBinding;

import java.util.List;

/**
 * Created by Shitman on 5/6/2016.
 */
public class ClanInfoFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static List<Clan> clans;

    public ClanInfoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ClanInfoFragment newInstance(int sectionNumber, List<Clan> list) {
        ClanInfoFragment fragment = new ClanInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        clans = list;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentClanInfoBinding binding = FragmentClanInfoBinding.inflate(inflater, container, false);
        final int message = getArguments().getInt(ARG_SECTION_NUMBER);
        final Clan clan = clans.get(message);
        DbHelper dbHelper = new DbHelper(VTMApplication.getContext());
        SQLiteDatabase db;
        List<Discipline> clanDisciplines;
        try {
            dbHelper.openDataBase();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        db = dbHelper.getReadableDatabase();
        VtmDb vtmDb = new VtmDb();
        clanDisciplines = vtmDb.getClanDisciplines(db, clan.getId());
        binding.setClan(clan);

        for(int i = 1; i < clanDisciplines.size() + 1; i++){

            String clanDiscipline = "clan_discipline_" + i;
            TextView textView = (TextView) binding.getRoot()
                    .findViewById(VTMApplication.getContext().getResources().getIdentifier(clanDiscipline
                    , "id", VTMApplication.getContext().getPackageName()));
            textView.setText(clanDisciplines.get(i - 1).getTitle());
            textView.setVisibility(View.VISIBLE);

        }
        if(clanDisciplines.size() == 0){

            binding.getRoot().findViewById(R.id.clan_disciplines).setVisibility(View.GONE);

        }

        return binding.getRoot();
    }

}
