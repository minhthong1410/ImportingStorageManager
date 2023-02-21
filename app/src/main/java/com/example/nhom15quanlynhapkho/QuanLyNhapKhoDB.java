package com.example.nhom15quanlynhapkho;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class QuanLyNhapKhoDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "QuanLyNhapKho";

    private static QuanLyNhapKhoDB sInstance;

    public QuanLyNhapKhoDB(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    public static synchronized QuanLyNhapKhoDB getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new QuanLyNhapKhoDB(context.getApplicationContext());
        }
        return sInstance;
    }

    //Truy van khong tra ket qua
    public void truyVanDuLieu(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    //Truy van tra ket qua
    public Cursor layDuLieu(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "Kho" +
                "(MaKho VARCHAR(5) PRIMARY KEY" +
                ",TenKho VARCHAR)" +
                "");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "VatTu" +
                "(MaVT VARCHAR PRIMARY KEY" +
                ",TenVT VARCHAR" +
                ",XuatXu VARCHAR)" +
                "");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "PhieuNhap" +
                "(SoPhieu INTEGER PRIMARY KEY" +
                ",NgayLap DATE" +
                ",MaKho VARCHAR(5)" +
                ", FOREIGN KEY (MaKho)" +
                " REFERENCES Kho (MaKho)" +
                ")");


        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "ChiTietPhieuNhap" +
                "(SoPhieu INTEGER" +
                ",MaVT VARCHAR" +
                ",DVT VARCHAR" +
                ",SoLuong INTEGER" +
                ",FOREIGN KEY (SoPhieu)" +
                " REFERENCES PhieuNhap(SoPhieu)" +
                ",FOREIGN KEY (MaVT)" +
                " REFERENCES VatTu (MaVT) "+
                ",PRIMARY KEY(SoPhieu, MaVT))" );

        //Them du lieu kho
        db.execSQL("INSERT INTO Kho VALUES ('K1','Bình Chánh')");
        db.execSQL("INSERT INTO Kho VALUES ('K2','Tân Phú')");
        db.execSQL("INSERT INTO Kho VALUES ('K3','Thủ Đức')");

        //Them du lieu VatTu
        db.execSQL("INSERT INTO VatTu VALUES ('GO','Gạch ống', 'Đồng Nai')");
        db.execSQL("INSERT INTO VatTu VALUES ('GT','Gạch thẻ', 'Long An')");
        db.execSQL("INSERT INTO VatTu VALUES ('SA','Sắt tròn', 'Bình Dương')");
        db.execSQL("INSERT INTO VatTu VALUES ('SO','Sơn dầu', 'Tiền Giang')");
        db.execSQL("INSERT INTO VatTu VALUES ('XM','Xi măng', 'Hà Tiên')");

        //Them du lieu phieu nhap
        db.execSQL("INSERT INTO PhieuNhap VALUES (1,'2022-04-30', 'K1')");
        db.execSQL("INSERT INTO PhieuNhap VALUES (2,'2022-04-30', 'K2')");
        db.execSQL("INSERT INTO PhieuNhap VALUES (3,'2022-04-30', 'K1')");
        db.execSQL("INSERT INTO PhieuNhap VALUES (4,'2022-04-30', 'K3')");
        db.execSQL("INSERT INTO PhieuNhap VALUES (5,'2022-04-30', 'K1')");

        //Them du lieu chi tiet phieu nhap
        db.execSQL("INSERT INTO ChiTietPhieuNhap VALUES (1,'GO', 'Viên', '5000')");
        db.execSQL("INSERT INTO ChiTietPhieuNhap VALUES (1,'GT', 'Viên', '2000')");
        db.execSQL("INSERT INTO ChiTietPhieuNhap VALUES (1,'XM', 'Bao', '150')");
        db.execSQL("INSERT INTO ChiTietPhieuNhap VALUES (2,'SO', 'Thùng', '75')");
        db.execSQL("INSERT INTO ChiTietPhieuNhap VALUES (3,'SA', 'Tấn', '25')");
        db.execSQL("INSERT INTO ChiTietPhieuNhap VALUES (3,'XM', 'Bao', '100')");
        db.execSQL("INSERT INTO ChiTietPhieuNhap VALUES (4,'GO', 'Viên', '10000')");
        db.execSQL("INSERT INTO ChiTietPhieuNhap VALUES (4,'SA', 'Tấn', '50')");
        db.execSQL("INSERT INTO ChiTietPhieuNhap VALUES (5,'SO', 'Thùng', '240')");
        db.execSQL("INSERT INTO ChiTietPhieuNhap VALUES (5,'XM', 'Bao', '430')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
