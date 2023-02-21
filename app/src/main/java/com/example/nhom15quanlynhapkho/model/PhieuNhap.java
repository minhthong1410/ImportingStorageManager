package com.example.nhom15quanlynhapkho.model;

import java.io.Serializable;

public class PhieuNhap implements Serializable {
    private int soPhieu;
    private String ngayLap;
    private String maKho;

    public PhieuNhap() {

    }

    public PhieuNhap(int soPhieu, String ngayLap, String maKho) {
        this.soPhieu = soPhieu;
        this.ngayLap = ngayLap;
        this.maKho = maKho;
    }

    public int getSoPhieu() {
        return soPhieu;
    }

    public void setSoPhieu(int soPhieu) {
        this.soPhieu = soPhieu;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        this.maKho = maKho;
    }
}
