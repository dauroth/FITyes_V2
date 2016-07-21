package com.mattlab.gym.fityes_v2.Activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattlab.gym.fityes_v2.R;

import java.util.List;

/**
 * Created by EXT on 2016. 07. 05..
 */

public class RVAdapter_Group extends RecyclerView.Adapter<RVAdapter_Group.PersonViewHolder> implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        v.setOnClickListener(this);
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv_group;
        TextView groupName;
        TextView groupDesc;
        ImageView groupPhoto;


        PersonViewHolder(View itemView) {
            super(itemView);
            cv_group = (CardView) itemView.findViewById(R.id.cv_group);
            groupName = (TextView) itemView.findViewById(R.id.group_name);
            groupDesc = (TextView) itemView.findViewById(R.id.group_desc);
            groupPhoto = (ImageView) itemView.findViewById(R.id.group_photo);
        }

    }

    List<GroupAdapter> persons;

    public RVAdapter_Group(List<GroupAdapter> persons) {
        this.persons = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group, viewGroup, false);
        final PersonViewHolder pvh = new PersonViewHolder(v);

        pvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                Intent myIntent = new Intent(context, inGroup.class);
                myIntent.putExtra("group_name", persons.get(pvh.getAdapterPosition()).name);
                myIntent.putExtra("group_id", persons.get(pvh.getAdapterPosition()).group_id); //Optional parameters
                context.startActivity(myIntent);

                Log.d("Teszt", "position = " + persons.get(pvh.getAdapterPosition()).group_id);
            }
        });
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.groupName.setText(persons.get(i).name);
        personViewHolder.groupDesc.setText(persons.get(i).desc);
        personViewHolder.groupPhoto.setImageResource(persons.get(i).photoId);

    }

    @Override
    public int getItemCount() {
        return persons.size();
    }


}