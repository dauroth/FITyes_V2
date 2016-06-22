package com.mattlab.gym.fityes_v2.Utilities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mattlab.gym.fityes_v2.R;
import com.mattlab.gym.fityes_v2.Utilities.GroupSetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EXT on 2016. 06. 21..
 */

public class GroupAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public GroupAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        GroupHolder groupHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            groupHolder = new GroupHolder();

            groupHolder.tx_name = (TextView) row.findViewById(R.id.tx_name);
            groupHolder.tx_desc = (TextView) row.findViewById(R.id.tx_desc);
            groupHolder.tx_number = (TextView) row.findViewById(R.id.tx_number);

            row.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) row.getTag();
        }

        GroupSetter groupSetter = (GroupSetter) this.getItem(position);
        groupHolder.tx_name.setText(groupSetter.getName());
        groupHolder.tx_desc.setText(groupSetter.getDesc());
        groupHolder.tx_number.setText(groupSetter.getNumber());

        return row;
    }

    static class GroupHolder {
        TextView tx_name, tx_desc, tx_number;
    }
}
