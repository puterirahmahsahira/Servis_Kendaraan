package uji;

import exception.ValidationException;
import model.Servis;

public class UjiEntityServis {

    public static void main(String[] args) {
        coba("lengkap dan benar", new Servis(1, 1, "2026-10-20", "Ganti oli", "Selesai", 75000));
        coba("ID kendaraan tidak sah", new Servis(0, 1, "2026-10-20", "Ganti oli", "Selesai", 75000));
        coba("ID jenis pekerjaan tidak sah", new Servis(1, 0, "2026-10-20", "Ganti oli", "Selesai", 75000));
        coba("tanggal masuk kosong", new Servis(1, 1, "", "Ganti oli", "Selesai", 75000));
        coba("keluhan kosong", new Servis(1, 1, "2026-10-20", " ", "Selesai", 75000));
        coba("status kosong", new Servis(1, 1, "2026-10-20", "Ganti oli", "", 75000));
        coba("biaya negatif", new Servis(1, 1, "2026-10-20", "Ganti oli", "Selesai", -5000));
    }

    private static void coba(String keterangan, Servis s) {
        try {
            s.validasi();
            System.out.println("DITERIMA : " + keterangan + " -> " + s.label());
        } catch (ValidationException e) {
            System.out.println("DITOLAK  : " + keterangan + " -> " + e.pesanPengguna());
        }
    }
}
