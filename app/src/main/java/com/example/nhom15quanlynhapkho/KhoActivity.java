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
import android.widget.Toast;

import com.example.nhom15quanlynhapkho.adapter.CustomAdapterKho;
import com.example.nhom15quanlynhapkho.model.Kho;

import java.util.ArrayList;
import java.util.List;

public class KhoActivity extends AppCompatActivity {
    private ListView lvDsKho;
    private List<Kho> dataKho = new ArrayList<>();
    private CustomAdapterKho customAdapterKho;
    private ImageButton btnThem, btnLamMoi, btnSua;
    private EditText txtMaKho, txtTenKho;
    private SearchView svKho;
    private QuanLyNhapKhoDB quanLyNhapKhoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho);
        quanLyNhapKhoDB = QuanLyNhapKhoDB.getInstance(this);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dataKho.clear();
        dataKho.addAll(layDuLieu());
        customAdapterKho = new CustomAdapterKho(KhoActivity.this, R.layout.listview_kho, dataKho);
        lvDsKho.setAdapter(customAdapterKho);

        btnThem.setOnClickListener(this::btnThemOnClickEvent);
        btnSua.setOnClickListener(this::btnSuaOnClickEvent);
        btnLamMoi.setOnClickListener(this::btnLamMoiOnClickEvent);

        lvDsKho.setOnItemClickListener(this::lvDsKhoOnItemClickEvent);

        svKho.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                customAdapterKho.filter(s);
                return false;
            }
        });
    }


    private Kho getKhoFromEditText() {
        Kho kho = new Kho();
        String maKho = txtMaKho.getText().toString().trim();
        String tenKho = txtTenKho.getText().toString().trim();
        if (maKho.equals("") || tenKho.equals("")) {
            kho = null;
            return kho;
        }
        kho.setMaKho(maKho);
        kho.setTenKho(tenKho);
        return kho;
    }

    private void btnThemOnClickEvent(View view) {
        Kho kho = getKhoFromEditText();
        if (kho == null) {
            hienThiThongBao("Bạn chưa nhập đủ các trường!");
            return;
        }

        // kiem tra ma kho trung
        for (int i = 0; i < dataKho.size(); i++) {
            if (kho.getMaKho().equals(dataKho.get(i).getMaKho())) {
                Toast.makeText(KhoActivity.this, "Mã kho bị trùng", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        quanLyNhapKhoDB.truyVanDuLieu("INSERT INTO Kho VALUES ('" + kho.getMaKho() + "','" + kho.getTenKho() + "')");
        dataKho.clear();
        dataKho.addAll(layDuLieu());
        customAdapterKho.notifyDataSetChanged();
        hienThiThongBao("Đã thêm!");
        txtMaKho.setText("");
        txtTenKho.setText("");
        txtMaKho.requestFocus();
    }

    private void btnSuaOnClickEvent(View view) {
        Kho kho = getKhoFromEditText();
        if (kho == null) {
            hienThiThongBao("Bạn chưa chọn kho!");
            return;
        }

        quanLyNhapKhoDB.truyVanDuLieu("UPDATE Kho SET TenKho = '" + kho.getTenKho() + "' WHERE MaKho = '" + kho.getMaKho() + "'");
        dataKho.clear();
        dataKho.addAll(layDuLieu());
        customAdapterKho.notifyDataSetChanged();
        hienThiThongBao("Đã cập nhật!");
        txtMaKho.setText("");
        txtTenKho.setText("");
        txtMaKho.requestFocus();
    }

    private void btnLamMoiOnClickEvent(View view) {
        dataKho.clear();
        dataKho.addAll(layDuLieu());
        customAdapterKho.notifyDataSetChanged();
        hienThiThongBao("Làm mới!");
        txtMaKho.setText("");
        txtTenKho.setText("");
        txtMaKho.requestFocus();
    }

    public void xoaKho(Kho kho) {
        quanLyNhapKhoDB.truyVanDuLieu("DELETE FROM Kho WHERE MaKho = '" + kho.getMaKho() + "'");
        dataKho.clear();
        dataKho.addAll(layDuLieu());
        customAdapterKho.notifyDataSetChanged();
        hienThiThongBao("Đã xoá");
        txtMaKho.setText("");
        txtTenKho.setText("");
        txtMaKho.requestFocus();
    }

    private List<Kho> layDuLieu() {
            List<Kho> data = new ArrayList<>();
            String sql = "SELECT * FROM Kho";
            Cursor cursor = quanLyNhapKhoDB.layDuLieu(sql);

            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {
                    Kho kho = new Kho();
                    kho.setMaKho(cursor.getString(0));
                    kho.setTenKho(cursor.getString(1));
                    data.add(kho);
                    cursor.moveToNext();
                }
            }
            return data;
    }

    private void setControl() {
        btnThem = findViewById(R.id.ibThem);
        btnSua = findViewById(R.id.ibSua);
        btnLamMoi = findViewById(R.id.ibLamMoi);
        txtMaKho = findViewById(R.id.etKhoMaKho);
        txtTenKho = findViewById(R.id.etKhoTenKho);
        svKho = findViewById(R.id.svKho);
        lvDsKho = findViewById(R.id.lvDsKho);
    }

    private void lvDsKhoOnItemClickEvent(AdapterView<?> adapterView, View view, int i, long l) {
        Kho kho = dataKho.get(i);
        txtMaKho.setText(kho.getMaKho());
        txtTenKho.setText(kho.getTenKho());
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
            Intent intent = new Intent(KhoActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}