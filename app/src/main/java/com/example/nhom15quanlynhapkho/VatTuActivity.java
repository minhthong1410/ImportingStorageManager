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

import com.example.nhom15quanlynhapkho.adapter.CustomAdapterVatTu;
import com.example.nhom15quanlynhapkho.model.VatTu;

import java.util.ArrayList;
import java.util.List;

public class VatTuActivity extends AppCompatActivity {
    private ListView lvDsVatTu;
    private List<VatTu> dataVatTu = new ArrayList<>();
    private CustomAdapterVatTu customAdapterVatTu;
    private ImageButton btnThem, btnLamMoi, btnSua;
    private EditText txtMaVT, txtTenVT, txtXuatXu;
    private SearchView svVatTu;
    private QuanLyNhapKhoDB quanLyNhapKhoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_tu);
        quanLyNhapKhoDB = QuanLyNhapKhoDB.getInstance(this);
        setControl();
        setEvent();
    }

    private void setEvent() {
        dataVatTu.clear();
        dataVatTu.addAll(layDuLieu());
        customAdapterVatTu = new CustomAdapterVatTu(VatTuActivity.this, R.layout.listview_vattu, dataVatTu);
        lvDsVatTu.setAdapter(customAdapterVatTu);

        btnThem.setOnClickListener(this::btnThemOnClickEvent);
        btnSua.setOnClickListener(this::btnSuaOnClickEvent);
        btnLamMoi.setOnClickListener(this::btnLamMoiOnClickEvent);

        lvDsVatTu.setOnItemClickListener(this::lvDsVatTuOnItemClickEvent);

        svVatTu.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                customAdapterVatTu.filter(s);
                return false;
            }
        });
    }


    private VatTu getVatTuFromEditText() {
        VatTu vatTu = new VatTu();
        String maVT = txtMaVT.getText().toString().trim();
        String tenVT = txtTenVT.getText().toString().trim();
        String xuatXu = txtXuatXu.getText().toString().trim();
        if (maVT.equals("") || tenVT.equals("") || xuatXu.equals("")) {
            vatTu = null;
            return vatTu;
        }
        vatTu.setMaVT(maVT);
        vatTu.setTenVT(tenVT);
        vatTu.setXuatXu(xuatXu);
        return vatTu;
    }

    private void btnThemOnClickEvent(View view) {
        VatTu vatTu = getVatTuFromEditText();
        if (vatTu == null) {
            hienThiThongBao("Bạn chưa nhập đủ các trường!");
            return;
        }

        // kiem tra ma vatTu trung
        for (int i = 0; i < dataVatTu.size(); i++) {
            if (vatTu.getMaVT().equals(dataVatTu.get(i).getMaVT())) {
                Toast.makeText(VatTuActivity.this, "Mã vật tư bị trùng", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        quanLyNhapKhoDB.truyVanDuLieu("INSERT INTO VatTu VALUES ('" + vatTu.getMaVT() + "','" + vatTu.getTenVT() + "','" + vatTu.getXuatXu() + "')");
        dataVatTu.clear();
        dataVatTu.addAll(layDuLieu());
        customAdapterVatTu.notifyDataSetChanged();
        hienThiThongBao("Đã thêm vật tư!");
        txtMaVT.setText("");
        txtTenVT.setText("");
        txtXuatXu.setText("");
        txtMaVT.requestFocus();
    }

    private void btnSuaOnClickEvent(View view) {
        VatTu vatTu = getVatTuFromEditText();
        if (vatTu == null) {
            hienThiThongBao("Bạn chưa chọn vật tư!");
            return;
        }

        quanLyNhapKhoDB.truyVanDuLieu("UPDATE VatTu SET TenVT = '" + vatTu.getTenVT() + "', XuatXu = '" + vatTu.getXuatXu() + "' WHERE MaVT = '" + vatTu.getMaVT() + "'");
        dataVatTu.clear();
        dataVatTu.addAll(layDuLieu());
        customAdapterVatTu.notifyDataSetChanged();
        hienThiThongBao("Đã cập nhật vật tư!");
        txtMaVT.setText("");
        txtTenVT.setText("");
        txtXuatXu.setText("");
        txtMaVT.requestFocus();
    }

    private void btnLamMoiOnClickEvent(View view) {
        dataVatTu.clear();
        dataVatTu.addAll(layDuLieu());
        customAdapterVatTu.notifyDataSetChanged();
        hienThiThongBao("Làm mới!");
        txtMaVT.setText("");
        txtTenVT.setText("");
        txtXuatXu.setText("");
        txtMaVT.requestFocus();
    }

    public void xoaVatTu(VatTu vatTu) {
        quanLyNhapKhoDB.truyVanDuLieu("DELETE FROM VatTu WHERE MaVT = '" + vatTu.getMaVT() + "'");
        dataVatTu.clear();
        dataVatTu.addAll(layDuLieu());
        customAdapterVatTu.notifyDataSetChanged();
        hienThiThongBao("Đã xoá");
        txtMaVT.setText("");
        txtTenVT.setText("");
        txtXuatXu.setText("");
        txtMaVT.requestFocus();
    }

    private List<VatTu> layDuLieu() {
        List<VatTu> data = new ArrayList<>();
        String sql = "SELECT * FROM VatTu";
        Cursor cursor = quanLyNhapKhoDB.layDuLieu(sql);

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                VatTu vatTu = new VatTu();
                vatTu.setMaVT(cursor.getString(0));
                vatTu.setTenVT(cursor.getString(1));
                vatTu.setXuatXu(cursor.getString(2));
                data.add(vatTu);
                cursor.moveToNext();
            }
        }
        return data;
    }

    private void setControl() {
        btnThem = findViewById(R.id.ibThemVT);
        btnSua = findViewById(R.id.ibSuaVT);
        btnLamMoi = findViewById(R.id.ibLamMoiVT);
        txtMaVT = findViewById(R.id.etVatTuMaVT);
        txtTenVT = findViewById(R.id.etVatTuTenVT);
        txtXuatXu = findViewById(R.id.etVatTuXuatXu);
        svVatTu = findViewById(R.id.svVatTu);
        lvDsVatTu = findViewById(R.id.lvDsVatTu);
    }

    private void lvDsVatTuOnItemClickEvent(AdapterView<?> adapterView, View view, int i, long l) {
        VatTu vatTu = dataVatTu.get(i);
        txtMaVT.setText(vatTu.getMaVT());
        txtTenVT.setText(vatTu.getTenVT());
        txtXuatXu.setText(vatTu.getXuatXu());
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
            Intent intent = new Intent(VatTuActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}