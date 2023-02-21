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
import com.example.nhom15quanlynhapkho.VatTuActivity;
import com.example.nhom15quanlynhapkho.model.VatTu;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterVatTu extends ArrayAdapter<VatTu> {
    private final Context context;
    private final int resource;
    private final List<VatTu> data;
    private final List<VatTu> dataTemp = new ArrayList<>();

    public CustomAdapterVatTu(@NonNull Context context, int resource, @NonNull List<VatTu> data) {
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
        TextView tvMaVT = convertView.findViewById(R.id.tvMaVT);
        TextView tvTenVT = convertView.findViewById(R.id.tvTenVT);
        TextView tvXuatXu = convertView.findViewById(R.id.tvXuatXu);
        ImageView ivXoa = convertView.findViewById(R.id.ivXoaVT);

        VatTu vatTu = data.get(position);
        tvMaVT.setText(vatTu.getMaVT());
        tvTenVT.setText(vatTu.getTenVT());
        tvXuatXu.setText(vatTu.getXuatXu());

        ivXoa.setOnClickListener(view -> {
            ((VatTuActivity) context).xoaVatTu(vatTu);
        });

        return convertView;
    }

    public void filter(String text) {
        // tim kiem theo ten vat tu
        data.clear();
        text = text.toLowerCase().trim();
        if (text.isEmpty()) {
            data.addAll(dataTemp);
        } else {
            for (VatTu vatTu: dataTemp) {
                if (vatTu.getTenVT().toLowerCase().contains(text)){
                    data.add(vatTu);
                }
            }
            notifyDataSetChanged();
        }
    }
}
