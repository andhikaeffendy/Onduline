package com.wiradipa.ondulineApplicator.lib;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.Arrays;

/**
 * Created by emrekabir on 10/13/16.
 */

public class AutoCompleteAdapter extends ArrayAdapter<String> {
    private String[] data;
    private long[] ids;

    public AutoCompleteAdapter(Context context, int resource, String[] data, long[] ids) {
        super(context, resource, data);
        this.data = data;
        this.ids = ids;
    }

    @Override
    public long getItemId(int position) {
        String name = getItem(position);
        int index = Arrays.asList(data).indexOf(name);
            /*
             * Atention , if your list has more that one same String , you have to improve here
             */
        // this will be used to get the id provided to the onItemClick callback
        if (index>0)
            return ids[index];
        else return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public long getItemId(String name){
        int index = Arrays.asList(data).indexOf(name);
            /*
             * Atention , if your list has more that one same String , you have to improve here
             */
        // this will be used to get the id provided to the onItemClick callback
            return ids[index];

    }


    public void setIds(long[] ids){
        this.ids = ids;
    }

    public void reloadData(String[] names, long[] ids){
        this.data = names;
        this.ids = ids;
        notifyDataSetChanged();
    }
}
