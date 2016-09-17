package com.dev.liamgens.quickbin.adapters;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.liamgens.quickbin.R;
import com.dev.liamgens.quickbin.objects.Bin;

import java.util.List;

/**
 * Created by leshan on 9/17/16.
 */
public class BinListAdapter extends RecyclerView.Adapter<BinListAdapter.MyViewHolder>{

    private List<Bin> binList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.bin_list_item_title);
            description = (TextView) view.findViewById(R.id.bin_list_item_description);
        }
    }


    public BinListAdapter(List<Bin> binList) {
        this.binList = binList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bin_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Bin bin = binList.get(position);
        holder.title.setText(bin.get_title());
        holder.description.setText(bin.get_description());
    }

    @Override
    public int getItemCount() {
        return binList.size();
    }


}