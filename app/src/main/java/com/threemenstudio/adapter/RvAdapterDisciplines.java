package com.threemenstudio.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.activities.DisciplineInfo;
import com.threemenstudio.vampire.R;

import java.util.List;

import com.threemenstudio.data.Discipline;

public class RvAdapterDisciplines extends RecyclerView.Adapter<RvAdapterDisciplines.ViewHolder> {

    private List<Discipline> disciplines;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtTitle;
        public LinearLayout parent;
        public TextView caste;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.clan_text);
            parent = (LinearLayout) v.findViewById(R.id.clan);
            caste = (TextView) v.findViewById(R.id.caste);
        }
    }

    public RvAdapterDisciplines(Context context, List<Discipline> disciplines) {
        this.context = context;
        this.disciplines = disciplines;
    }

    @Override
    public RvAdapterDisciplines.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RvAdapterDisciplines.ViewHolder holder, final int position) {
        holder.txtTitle.setText(disciplines.get(position).getTitle());
        holder.caste.setText(disciplines.get(position).getSubtitle());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DisciplineInfo.class);
                intent.putExtra(VTMApplication.getExtraDiscipline(), String.valueOf(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return disciplines.size();
    }
}