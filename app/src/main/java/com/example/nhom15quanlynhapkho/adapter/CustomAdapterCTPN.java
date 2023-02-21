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
import com.example.nhom15quanlynhapkho.model.ChiTietPhieuNhap;
import com.example.nhom15quanlynhapkho.model.Kho;
import com.example.nhom15quanlynhapkho.model.PhieuNhap;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterCTPN extends ArrayAdapter<ChiTietPhieuNhap> {
    private final Context context;
    private final int resource;
    private final List<ChiTietPhieuNhap> data;
    private final List<ChiTietPhieuNhap> dataTemp = new ArrayList<>();

    public CustomAdapterCTPN(@NonNull Context context, int resource, @NonNull List<ChiTietPhieuNhap> data) {
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
        TextView tvSoPhieu = convertView.findViewById(R.id.tvSoPhieuChiTiet);
        TextView tvMaVT = convertView.findViewById(R.id.tvMaVTChiTiet);
        TextView tvDVT = convertView.findViewById(R.id.tvDVT);
        TextView tvSoLuong = convertView.findViewById(R.id.tvSoLuong);

        ChiTietPhieuNhap ctpn = data.get(position);
        tvSoPhieu.setText(ctpn.getSoPhieu() + "");
        tvMaVT.setText(ctpn.getMaVT());
        tvDVT.setText(ctpn.getDVT());
        tvSoLuong.setText(ctpn.getSoLuong() + "");

        return convertView;
    }

    public void filter(String text) {
        // tim kiem theo ma vat tu
        data.clear();
        text = text.toLowerCase().trim();
        if (text.isEmpty()) {
            data.addAll(dataTemp);
        } else {
            for (ChiTietPhieuNhap ctpn: dataTemp) {
                if (ctpn.getMaVT().toLowerCase().contains(text)){
                    data.add(ctpn);
                }
            }
            notifyDataSetChanged();
        }
    }
}
