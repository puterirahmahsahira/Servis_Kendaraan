package Uji;

import exception.ValidationException;
import model.JenisPekerjaan;

public class UjiJenisPekerjaan {
    public static void main(String[] args) {
        // Pengujian untuk Modul JenisPekerjaan
        coba("lengkap dan benar", new JenisPekerjaan("Ganti Oli Mesin", 75000));
        coba("nama jenis pekerjaan kosong", new JenisPekerjaan("", 50000));
        coba("tarif bernilai negatif", new JenisPekerjaan("Tune Up", -10000));
    }

    private static void coba(String keterangan, JenisPekerjaan p) {
        try {
            p.validasi();
            System.out.println("DITERIMA : " + keterangan + " -> " + p.label());
        } catch (ValidationException e) {
            System.out.println("DITOLAK : " + keterangan + " -> " + e.pesanPengguna());
        }
    }
}