package com.mattlab.gym.fityes_v2.Utilities.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

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
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
}
