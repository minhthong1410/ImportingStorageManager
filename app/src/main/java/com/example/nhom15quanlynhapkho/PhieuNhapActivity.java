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

import com.example.nhom15quanlynhapkho.adapter.CustomAdapterPhieuNhap;
import com.example.nhom15quanlynhapkho.adapter.CustomSpinner;
import com.example.nhom15quanlynhapkho.model.PhieuNhap;

import java.util.ArrayList;
import java.util.List;

public class PhieuNhapActivity extends AppCompatActivity {
    private ListView lvDsPhieuNhap;
    private List<PhieuNhap> dataPhieuNhap = new ArrayList<>();
    private List<String> dataMaKho= new ArrayList<>();
    private CustomAdapterPhieuNhap customAdapterPhieuNhap;
    private CustomSpinner customSpinner;
    private ImageButton btnThem, btnLamMoi, btnSua;
    private EditText txtSoPhieu, txtNgayLap;
    private SearchView svPhieuNhap;
    private Spinner spMaKho;
    private QuanLyNhapKhoDB quanLyNhapKhoDB;
    private String maKho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_nhap);
        quanLyNhapKhoDB = QuanLyNhapKhoDB.getInstance(this);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dataPhieuNhap.clear();
        dataPhieuNhap.addAll(layDuLieuPhieuNhap());
        customAdapterPhieuNhap = new CustomAdapterPhieuNhap(PhieuNhapActivity.this, R.layout.listview_phieunhap, dataPhieuNhap);
        lvDsPhieuNhap.setAdapter(customAdapterPhieuNhap);

        btnThem.setOnClickListener(this::btnThemOnClickEvent);
        btnSua.setOnClickListener(this::btnSuaOnClickEvent);
        btnLamMoi.setOnClickListener(this::btnLamMoiOnClickEvent);

        lvDsPhieuNhap.setOnItemClickListener(this::lvDsPhieuNhapOnItemClickEvent);

        svPhieuNhap.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                customAdapterPhieuNhap.filter(s);
                return false;
            }
        });

        dataMaKho.clear();
        dataMaKho.addAll(layDuLieuMaKho());
        customSpinner = new CustomSpinner(PhieuNhapActivity.this,R.layout.custom_spinnner,dataMaKho);
        spMaKho.setAdapter(customSpinner);
        spMaKho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maKho = dataMaKho.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private PhieuNhap getPhieuNhapFromEditText() {
        PhieuNhap phieuNhap = new PhieuNhap();
        String soPhieu = txtSoPhieu.getText().toString();
        String ngayLap = txtNgayLap.getText().toString().trim();
        String maKho = spMaKho.getSelectedItem().toString().trim();
        if (soPhieu.equals("") || ngayLap.equals("") || maKho.equals("")) {
            phieuNhap = null;
            return phieuNhap;
        }
        phieuNhap.setSoPhieu(Integer.parseInt(soPhieu));
        phieuNhap.setNgayLap(ngayLap);
        phieuNhap.setMaKho(maKho);
        return phieuNhap;
    }

    private void btnThemOnClickEvent(View view) {
        PhieuNhap phieuNhap = getPhieuNhapFromEditText();
        if (phieuNhap == null) {
            hienThiThongBao("Bạn chưa nhập đủ các trường!");
            return;
        }

        // kiem tra so phieu trung
        for (int i = 0; i < dataPhieuNhap.size(); i++) {
            if (phieuNhap.getSoPhieu() == dataPhieuNhap.get(i).getSoPhieu()) {
                Toast.makeText(PhieuNhapActivity.this, "Số phiếu bị trùng", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        quanLyNhapKhoDB.truyVanDuLieu("INSERT INTO PhieuNhap VALUES ('" + phieuNhap.getSoPhieu() + "','" + phieuNhap.getNgayLap() + "','" + phieuNhap.getMaKho() + "')");
        dataPhieuNhap.clear();
        dataPhieuNhap.addAll(layDuLieuPhieuNhap());
        customAdapterPhieuNhap.notifyDataSetChanged();
        hienThiThongBao("Đã thêm vật tư!");
        txtSoPhieu.setText("");
        txtNgayLap.setText("");
        spMaKho.setSelection(0);
        txtSoPhieu.requestFocus();
    }

    private void btnSuaOnClickEvent(View view) {
        PhieuNhap phieuNhap = getPhieuNhapFromEditText();
        if (phieuNhap == null) {
            hienThiThongBao("Bạn chưa chọn vật tư!");
            return;
        }

        quanLyNhapKhoDB.truyVanDuLieu("UPDATE PhieuNhap SET NgayLap = '" + phieuNhap.getNgayLap() + "', MaKho = '" + phieuNhap.getMaKho() + "' WHERE SoPhieu = '" + phieuNhap.getSoPhieu() + "'");
        dataPhieuNhap.clear();
        dataPhieuNhap.addAll(layDuLieuPhieuNhap());
        customAdapterPhieuNhap.notifyDataSetChanged();
        hienThiThongBao("Đã cập nhật phiếu nhập!");
        txtSoPhieu.setText("");
        txtNgayLap.setText("");
        spMaKho.setSelection(0);
        txtSoPhieu.requestFocus();
    }

    private void btnLamMoiOnClickEvent(View view) {
        dataPhieuNhap.clear();
        dataPhieuNhap.addAll(layDuLieuPhieuNhap());
        customAdapterPhieuNhap.notifyDataSetChanged();
        hienThiThongBao("Làm mới!");
        txtSoPhieu.setText("");
        txtNgayLap.setText("");
        spMaKho.setSelection(0);
        txtSoPhieu.requestFocus();
    }

    public void xoaPhieuNhap(PhieuNhap phieuNhap) {
        quanLyNhapKhoDB.truyVanDuLieu("DELETE FROM PhieuNhap WHERE SoPhieu = '" + phieuNhap.getSoPhieu() + "'");
        dataPhieuNhap.clear();
        dataPhieuNhap.addAll(layDuLieuPhieuNhap());
        customAdapterPhieuNhap.notifyDataSetChanged();
        hienThiThongBao("Đã xoá phiếu nhập!");
        txtSoPhieu.setText("");
        txtNgayLap.setText("");
        spMaKho.setSelection(0);
        txtSoPhieu.requestFocus();
    }

    private List<PhieuNhap> layDuLieuPhieuNhap() {
        List<PhieuNhap> data = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap";
        Cursor cursor = quanLyNhapKhoDB.layDuLieu(sql);

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                PhieuNhap phieuNhap = new PhieuNhap();
                phieuNhap.setSoPhieu(cursor.getInt(0));
                phieuNhap.setNgayLap(cursor.getString(1));
                phieuNhap.setMaKho(cursor.getString(2));
                data.add(phieuNhap);
                cursor.moveToNext();
            }
        }
        return data;
    }

    private List<String> layDuLieuMaKho() {
        List<String> data = new ArrayList<>();
        String sql = "SELECT MaKho FROM Kho";
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
        btnThem = findViewById(R.id.ibThemPN);
        btnSua = findViewById(R.id.ibSuaPN);
        btnLamMoi = findViewById(R.id.ibLamMoiPN);
        txtSoPhieu = findViewById(R.id.etPhieuNhapSoPhieu);
        txtNgayLap = findViewById(R.id.etPhieuNhapNgayLap);
        spMaKho = findViewById(R.id.spPhieuNhapMaKho);
        svPhieuNhap = findViewById(R.id.svPhieuNhap);
        lvDsPhieuNhap = findViewById(R.id.lvDsPhieuNhap);
    }

    private void lvDsPhieuNhapOnItemClickEvent(AdapterView<?> adapterView, View view, int i, long l) {
        PhieuNhap phieuNhap = dataPhieuNhap.get(i);
        txtSoPhieu.setText(phieuNhap.getSoPhieu() + "");
        txtNgayLap.setText(phieuNhap.getNgayLap());
        spMaKho.setSelection(layViTriMaKhoTuListView(phieuNhap.getMaKho()));
    }

    private void hienThiThongBao(String info) {
        Toast.makeText(this,info,Toast.LENGTH_SHORT).show();
    }

    private int layViTriMaKhoTuListView(String text) {
        text = text.toLowerCase().trim();
        int i = 0;
        for (String ma: dataMaKho) {
            if (ma.toLowerCase().contains(text)){
                return i;
            }
            i++;
        }
        return -1;
    }

    public void chuyenTrang(int soPhieu) {
        Intent intent = new Intent(PhieuNhapActivity.this, ChiTietPhieuNhapActivity.class);
        intent.putExtra("SoPhieu", soPhieu);
        startActivity(intent);
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
            Intent intent = new Intent(PhieuNhapActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}