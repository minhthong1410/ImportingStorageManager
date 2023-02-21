package com.example.nhom15quanlynhapkho.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nhom15quanlynhapkho.KhoActivity;
import com.example.nhom15quanlynhapkho.R;
import com.example.nhom15quanlynhapkho.model.Kho;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterKho extends ArrayAdapter<Kho> {
    private final Context context;
    private final int resource;
    private final List<Kho> data;
    private final List<Kho> dataTemp = new ArrayList<>();

    public CustomAdapterKho(@NonNull Context context, int resource, @NonNull List<Kho> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        dataTemp.addAll(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);
        TextView tvMaKho = convertView.findViewById(R.id.tvMaKho);
        TextView tvTenKho = convertView.findViewById(R.id.tvTenKho);
        ImageView ivXoa = convertView.findViewById(R.id.ivXoa);

        Kho kho = data.get(position);
        tvMaKho.setText(kho.getMaKho());
        tvTenKho.setText(kho.getTenKho());

        ivXoa.setOnClickListener(view -> {
            ((KhoActivity) context).xoaKho(kho);
        });

        return convertView;
    }

    public void filter(String text) {
        // dung cho search view khi nhap text
        data.clear();
        text = text.toLowerCase().trim();
        if (text.isEmpty()) {
            data.addAll(dataTemp);
        } else {
            for (Kho kho: dataTemp) {
                if (kho.getTenKho().toLowerCase().contains(text)){
                    data.add(kho);
                }
            }
            notifyDataSetChanged();
        }
    }
}
