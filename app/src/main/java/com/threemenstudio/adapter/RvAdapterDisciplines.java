package com.threemenstudio.adapter;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.threemenstudio.VTMApplication;
import com.threemenstudio.activities.DisciplineInfo;
import com.threemenstudio.data.Discipline;
import com.threemenstudio.vampire.R;
import com.threemenstudio.vampire.databinding.RvItemBinding;

import java.util.List;

public class RvAdapterDisciplines extends RecyclerView.Adapter<RvAdapterDisciplines.ViewHolder> {

    private List<Discipline> disciplines;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RvItemBinding rvItemBinding;

        public ViewHolder(RvItemBinding rvItemBinding) {
            super(rvItemBinding.getRoot());
            this.rvItemBinding = rvItemBinding;
        }
    }

    public RvAdapterDisciplines(Context context, List<Discipline> disciplines) {
        this.context = context;
        this.disciplines = disciplines;
    }

    @Override
    public RvAdapterDisciplines.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        RvItemBinding rvItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.rv_item, parent, false);
        return new ViewHolder(rvItemBinding);
    }

    @Override
    public void onBindViewHolder(RvAdapterDisciplines.ViewHolder holder, final int position) {

        holder.rvItemBinding.clanText.setText(disciplines.get(position).getTitle());
        holder.rvItemBinding.caste.setText(disciplines.get(position).getSubtitle());
        holder.rvItemBinding.clan.setOnClickListener(new View.OnClickListener() {
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