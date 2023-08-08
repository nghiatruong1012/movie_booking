package com.example.moviebooking;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Array;
import java.util.ArrayList;

public class ShowAdapter extends BaseAdapter {

    ArrayList<Show> shows;

    Context context;

    public ShowAdapter(Context context, ArrayList<Show> shows){
        this.context = context;
        this.shows = shows;
    }


    @Override
    public int getCount() {
        if(shows!=null){
            return shows.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.item_show, null);
        Button showButton = view.findViewById(R.id.show_btn);
        if(shows!= null){
            Log.d("xxx", shows.get(i).getStart());
            showButton.setText(shows.get(i).getStart());
            showButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SeatActivity.class);
                    intent.putExtra("showID", shows.get(i).getId());
                    context.startActivity(intent);

                }
            });
        }
        return view;

    }
}
