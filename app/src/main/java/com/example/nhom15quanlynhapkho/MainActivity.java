package com.example.nhom15quanlynhapkho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnQlKho, btnQlVatTu, btnLapPhieuNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnQlKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,KhoActivity.class);
                startActivity(intent);
            }
        });
        btnQlVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,VatTuActivity.class);
                startActivity(intent);
            }
        });
        btnLapPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PhieuNhapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btnQlKho = findViewById(R.id.btnQlKho);
        btnQlVatTu = findViewById(R.id.btnQlVatTu);
        btnLapPhieuNhap = findViewById(R.id.btnLapPhieuNhap);
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
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

