package com.example.nhom15quanlynhapkho.model;

import java.io.Serializable;

public class VatTu implements Serializable {
    private String maVT;
    private String tenVT;
    private String xuatXu;

    public VatTu() {
    }

    public VatTu(String maVT, String tenVT, String xuatXu) {
        this.maVT = maVT;
        this.tenVT = tenVT;
        this.xuatXu = xuatXu;
    }

    public String getMaVT() {
        return maVT;
    }

    public void setMaVT(String maVT) {
        this.maVT = maVT;
    }

    public String getTenVT() {
        return tenVT;
    }

    public void setTenVT(String tenVT) {
        this.tenVT = tenVT;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }
}
