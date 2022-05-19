package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<Event>
{
    Context context;

    public arrayAdapter(Context context, int resourceId, List<Event> items){
        super(context,resourceId,items);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Event card_item=getItem(position);
        if(convertView==null)
        convertView= LayoutInflater.from(getContext()).inflate(R.layout.details,parent,false);
        TextView name=(TextView) convertView.findViewById(R.id.ime);
        TextView desc=(TextView) convertView.findViewById(R.id.swpcards1);
        TextView age=(TextView) convertView.findViewById(R.id.godine);
        TextView loc=(TextView) convertView.findViewById(R.id.lokacija);
        TextView dat=(TextView) convertView.findViewById(R.id.vrijeme);
        TextView kat=(TextView) convertView.findViewById(R.id.kategorije);
        ImageView image=(ImageView) convertView.findViewById(R.id.event_pic);

    name.setText(card_item.fullName);
    age.setText(card_item.age);
    desc.setText(card_item.description);
    loc.setText(card_item.location);
    dat.setText(card_item.date);
    kat.setText(card_item.category);
    Glide.with(getContext()).load(card_item.imageUrl).centerCrop().into(image);
    return convertView;
    }
}
