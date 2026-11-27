package uji;

import exception.ValidationException;
import model.Servis;

public class UjiEntityServis {

    public static void main(String[] args) {
        coba("lengkap dan benar", new Servis("K001", "2026-10-20", "Ganti oli", "Selesai", 75000));
        coba("ID kendaraan kosong", new Servis("", "2026-10-20", "Ganti oli", "Selesai", 75000));
        coba("tanggal masuk kosong", new Servis("K001", "", "Ganti oli", "Selesai", 75000));
        coba("keluhan kosong", new Servis("K001", "2026-10-20", " ", "Selesai", 75000));
        coba("ID kendaraan null", new Servis(null, "2026-10-20", "Ganti oli", "Selesai", 75000));
    }

    private static void coba(String keterangan, Servis s) {
        try {
            s.validasi();
            System.out.println("DITERIMA : " + keterangan + " -> " + s.label());
        } catch (ValidationException e) {
            System.out.println("DITOLAK : " + keterangan + " -> " + e.pesanPengguna());
        }
    }
}