package com.example.nhom15quanlynhapkho.model;

public class ChiTietPhieuNhap {
    private int soPhieu;
    private String maVT;
    private String DVT;
    private int soLuong;

    public ChiTietPhieuNhap() {

    }

    public ChiTietPhieuNhap(int soPhieu, String maVT, String DVT, int soLuong) {
        this.soPhieu = soPhieu;
        this.maVT = maVT;
        this.DVT = DVT;
        this.soLuong = soLuong;
    }

    public int getSoPhieu() {
        return soPhieu;
    }

    public void setSoPhieu(int soPhieu) {
        this.soPhieu = soPhieu;
    }

    public String getMaVT() {
        return maVT;
    }

    public void setMaVT(String maVT) {
        this.maVT = maVT;
    }

    public String getDVT() {
        return DVT;
    }

    public void setDVT(String DVT) {
        this.DVT = DVT;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
