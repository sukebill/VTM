package adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import vampire.ClanInfo;
import com.threemenstudio.vampire.R;

import java.util.List;

import data.Clan;
import data.Constants;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private List<Clan> clans;
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

    public RvAdapter(Context context, List<Clan> clans){
        this.context = context;
        this.clans = clans;
    }

    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RvAdapter.ViewHolder holder, final int position) {
        if(clans.get(position).getCaste() != null){
            holder.txtTitle.setText(clans.get(position).getCaste());
        }
        else{
            holder.txtTitle.setText(clans.get(position).getClan());
        }
        holder.caste.setText(clans.get(position).getSubtitle());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClanInfo.class);
                intent.putExtra(Constants.EXTRA_CLAN, String.valueOf(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clans.size();
    }
}
