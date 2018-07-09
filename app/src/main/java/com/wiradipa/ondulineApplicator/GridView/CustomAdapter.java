package com.wiradipa.ondulineApplicator.GridView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wiradipa.ondulineApplicator.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{

    Context context;
    ArrayList<Scapecraft> scapecrafts;

    public CustomAdapter(Context context, ArrayList<Scapecraft> scapecrafts) {
        this.context = context;
        this.scapecrafts = scapecrafts;
    }

    @Override
    public int getCount() {
        return scapecrafts.size();
    }

    @Override
    public Object getItem(int position) {
        return scapecrafts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.model, viewGroup, false);
        }

        final Scapecraft spacecraft = (Scapecraft) this.getItem(position);

        TextView nameTxt = (TextView) view.findViewById(R.id.nameTxt);
        ImageView img = (ImageView) view.findViewById(R.id.spacescraftImg);

        nameTxt.setText(spacecraft.getName());
        Picasso.with(context).load(spacecraft.getUri()).placeholder(R.drawable.image_placeholder).into(img);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,spacecraft.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
