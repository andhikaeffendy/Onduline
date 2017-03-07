package com.wiradipa.ondulineApplicator.lib;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.squareup.picasso.Picasso;
import com.wiradipa.ondulineApplicator.R;

import java.util.List;
import java.util.Map;

/**
 * Created by sentanu on 2/21/2017.
 */

//public class MyAdapter extends SimpleAdapter {
//
//    public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to){
//        super(context, data, resource, from, to);
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent){
//        // here you let SimpleAdapter built the view normally.
//        View v = super.getView(position, convertView, parent);
//
//        // Then we get reference for Picasso
//        ImageView img = (ImageView) v.getTag();
//        if(img == null){
//            img = (ImageView) v.findViewById(R.id.img_detil);
//            v.setTag(img); // <<< THIS LINE !!!!
//        }
//        // get the url from the data you passed to the `Map`
//        String url = ((Map)getItem(position)).get(imagepathes(position));
//        // do Picasso
//        Picasso.with(v.getContext()).load(url).into(img);
//
//        // return the view
//        return v;
//    }
//}