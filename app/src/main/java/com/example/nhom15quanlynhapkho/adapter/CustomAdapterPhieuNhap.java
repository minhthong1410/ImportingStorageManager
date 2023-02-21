package com.example.nhom15quanlynhapkho.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nhom15quanlynhapkho.KhoActivity;
import com.example.nhom15quanlynhapkho.MainActivity;
import com.example.nhom15quanlynhapkho.PhieuNhapActivity;
import com.example.nhom15quanlynhapkho.R;
import com.example.nhom15quanlynhapkho.VatTuActivity;
import com.example.nhom15quanlynhapkho.model.Kho;
import com.example.nhom15quanlynhapkho.model.PhieuNhap;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterPhieuNhap extends ArrayAdapter<PhieuNhap> {
    private final Context context;
    private final int resource;
    private final List<PhieuNhap> data;
    private final List<PhieuNhap> dataTemp = new ArrayList<>();

    public CustomAdapterPhieuNhap(@NonNull Context context, int resource, @NonNull List<PhieuNhap> data) {
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
        TextView tvSoPhieu = convertView.findViewById(R.id.tvSoPhieu);
        TextView tvNgayLap = convertView.findViewById(R.id.tvNgayLap);
        TextView tvMaKho = convertView.findViewById(R.id.tvMaKhoTrongPN);
        ImageView ivXemChiTietPhieuNhap = convertView.findViewById(R.id.ivXemChiTietPhieuNhap);
        ImageView ivXoa = convertView.findViewById(R.id.ivXoaPhieuNhap);

        PhieuNhap phieuNhap = data.get(position);
        tvSoPhieu.setText(phieuNhap.getSoPhieu() + "");
        tvNgayLap.setText(phieuNhap.getNgayLap());
        tvMaKho.setText(phieuNhap.getMaKho());

        ivXemChiTietPhieuNhap.setOnClickListener(view -> {
            ((PhieuNhapActivity) context).chuyenTrang(phieuNhap.getSoPhieu());
        });

        ivXoa.setOnClickListener(view -> {
            ((PhieuNhapActivity) context).xoaPhieuNhap(phieuNhap);
        });

        return convertView;
    }

    public void filter(String text) {
        // tim kiem theo ma kho
        data.clear();
        text = text.toLowerCase().trim();
        if (text.isEmpty()) {
            data.addAll(dataTemp);
        } else {
            for (PhieuNhap phieuNhap: dataTemp) {
                if (phieuNhap.getMaKho().toLowerCase().contains(text)){
                    data.add(phieuNhap);
                }
            }
            notifyDataSetChanged();
        }
    }
}
