package com.example.nhom15quanlynhapkho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nhom15quanlynhapkho.adapter.CustomAdapterCTPN;
import com.example.nhom15quanlynhapkho.adapter.CustomAdapterPhieuNhap;
import com.example.nhom15quanlynhapkho.adapter.CustomSpinner;
import com.example.nhom15quanlynhapkho.adapter.CustomSpinnerVatTu;
import com.example.nhom15quanlynhapkho.model.ChiTietPhieuNhap;
import com.example.nhom15quanlynhapkho.model.PhieuNhap;

import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuNhapActivity extends AppCompatActivity {
    private ListView lvDsChiTietPhieuNhap;
    private List<ChiTietPhieuNhap> dataCTPN = new ArrayList<>();
    private List<String> dataMaVT = new ArrayList<>();
    private CustomAdapterCTPN customAdapterCTPN;
    private CustomSpinnerVatTu customSpinnerVatTu;
    private ImageButton btnThem, btnLamMoi;
    private EditText txtSoPhieu, txtDVT, txtSoLuong;
    private SearchView svChiTietPhieuNhap;
    private Spinner spMaVT;
    private QuanLyNhapKhoDB quanLyNhapKhoDB;
    private int soPhieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phieu_nhap);
        quanLyNhapKhoDB = QuanLyNhapKhoDB.getInstance(this);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dataCTPN.clear();
        dataCTPN.addAll(layDuLieuCTPN());
        customAdapterCTPN = new CustomAdapterCTPN(ChiTietPhieuNhapActivity.this, R.layout.listview_chitietphieunhap, dataCTPN);
        lvDsChiTietPhieuNhap.setAdapter(customAdapterCTPN);

        btnThem.setOnClickListener(this::btnThemOnClickEvent);
        btnLamMoi.setOnClickListener(this::btnLamMoiOnClickEvent);

        lvDsChiTietPhieuNhap.setOnItemClickListener(this::lvDsChiTietPhieuNhapOnItemClickEvent);

        svChiTietPhieuNhap.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                customAdapterCTPN.filter(s);
                return false;
            }
        });

        dataMaVT.clear();
        dataMaVT.addAll(layDuLieuMaVT());
        customSpinnerVatTu = new CustomSpinnerVatTu(ChiTietPhieuNhapActivity.this,R.layout.custom_spinner_mavt,dataMaVT);
        spMaVT.setAdapter(customSpinnerVatTu);
        spMaVT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Intent intent = getIntent();
        soPhieu = intent.getIntExtra("SoPhieu", 0);
        txtSoPhieu.setText(soPhieu + "");
    }

    private ChiTietPhieuNhap getChiTietPhieuNhapFromEditText() {
        ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
        String soPhieu = txtSoPhieu.getText().toString();
        String maVT = spMaVT.getSelectedItem().toString().trim();
        String dvt = txtDVT.getText().toString().trim();
        String soLuong = txtSoLuong.getText().toString().trim();

        if (dvt.equals("") || soLuong.equals("")) {
            ctpn = null;
            return ctpn;
        }
        ctpn.setSoPhieu(Integer.parseInt(soPhieu));
        ctpn.setMaVT(maVT);
        ctpn.setDVT(dvt);
        ctpn.setSoLuong(Integer.parseInt(soLuong));

        return ctpn;
    }

    private void btnThemOnClickEvent(View view) {
        ChiTietPhieuNhap ctpn = getChiTietPhieuNhapFromEditText();
        if (ctpn == null) {
            hienThiThongBao("Bạn chưa nhập đủ các trường!");
            return;
        }
        quanLyNhapKhoDB.truyVanDuLieu("INSERT INTO ChiTietPhieuNhap VALUES ('" + ctpn.getSoPhieu() + "','" + ctpn.getMaVT() + "','" + ctpn.getDVT() + "','" + ctpn.getSoLuong() + "')");

        dataCTPN.clear();
        dataCTPN.addAll(layDuLieuCTPN());
        customAdapterCTPN.notifyDataSetChanged();
        hienThiThongBao("Đã thêm chi tiết phiêu nhập!");
        spMaVT.setSelection(0);
        txtDVT.setText("");
        txtSoLuong.setText("");
        txtDVT.requestFocus();
    }

    private void btnLamMoiOnClickEvent(View view) {
        dataCTPN.clear();
        dataCTPN.addAll(layDuLieuCTPN());
        customAdapterCTPN.notifyDataSetChanged();
        hienThiThongBao("Làm mới!");
        spMaVT.setSelection(0);
        txtDVT.setText("");
        txtSoLuong.setText("");
        txtDVT.requestFocus();
    }

    private List<ChiTietPhieuNhap> layDuLieuCTPN() {
        List<ChiTietPhieuNhap> data = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietPhieuNhap";
        Cursor cursor = quanLyNhapKhoDB.layDuLieu(sql);

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
                ctpn.setSoPhieu(cursor.getInt(0));
                ctpn.setMaVT(cursor.getString(1));
                ctpn.setDVT(cursor.getString(2));
                ctpn.setSoLuong(cursor.getInt(3));
                data.add(ctpn);
                cursor.moveToNext();
            }
        }
        return data;
    }

    private List<String> layDuLieuMaVT() {
        List<String> data = new ArrayList<>();
        String sql = "SELECT MaVT FROM VatTu";
        Cursor cursor = quanLyNhapKhoDB.layDuLieu(sql);

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                data.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        return data;
    }

    private void setControl() {
        btnThem = findViewById(R.id.ibThemCTPN);
        btnLamMoi = findViewById(R.id.ibLamMoiCTPN);
        txtSoPhieu = findViewById(R.id.etCTPNSoPhieu);
        txtDVT = findViewById(R.id.etCTPNDonViTinh);
        txtSoLuong = findViewById(R.id.etCTPNSoLuong);
        spMaVT = findViewById(R.id.spCTPNMaVT);
        svChiTietPhieuNhap = findViewById(R.id.svChiTietPhieuNhap);
        lvDsChiTietPhieuNhap = findViewById(R.id.lvDsChiTietPhieuNhap);
    }

    private void lvDsChiTietPhieuNhapOnItemClickEvent(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private void hienThiThongBao(String info) {
        Toast.makeText(this,info,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.mnThoat) {
            finish();
        } else if (itemId == R.id.mnTrangChu) {
            Intent intent = new Intent(ChiTietPhieuNhapActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}