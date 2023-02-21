package com.example.nhom15quanlynhapkho.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nhom15quanlynhapkho.R;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinnerVatTu extends ArrayAdapter<String> {
    Context context;
    int resource;
    List<String> data;
    public CustomSpinnerVatTu(@NonNull Context context, int resource, @NonNull List<String> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(resource,null);
        TextView textView = convertView.findViewById(R.id.tvSpinnerMaVT);

        textView.setText(data.get(position));

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);
        TextView textView=convertView.findViewById(R.id.tvSpinnerMaVT);

        textView.setText(data.get(position));

        return convertView;
    }
}
