package Uji;

import dao.JenisPekerjaanDao;
import exception.AppException;
import model.JenisPekerjaan;

import java.util.List;

public class UjiJenisPekerjaanDao {
    public static void main(String[] args) {
        JenisPekerjaanDao dao = new JenisPekerjaanDao();

        try {
            JenisPekerjaan baru = new JenisPekerjaan("Ganti Oli Mesin", 75000);
            dao.simpan(baru);
            System.out.println("[simpan   ] id dari AUTO_INCREMENT = " + baru.getId());

            JenisPekerjaan dibaca = dao.cariId(baru.getId());
            System.out.println("[cariId   ] " + dibaca.label());

            JenisPekerjaan lewatNama = dao.cariNama("Ganti Oli Mesin");
            System.out.println("[cariNama ] " + (lewatNama != null ? lewatNama.label() : "null"));

            dibaca.setTarif(85000);
            System.out.println("[ubah     ] berhasil? " + dao.ubah(dibaca));

            List<JenisPekerjaan> semua = dao.ambilSemua();
            System.out.println("[ambilSemua] jumlah baris = " + semua.size());
            for (JenisPekerjaan jp : semua) {
                System.out.println("   " + jp.getId() + ". " + jp.label());
            }

            System.out.println("[hapus    ] berhasil? " + dao.hapus(baru.getId()));
            System.out.println("[hapus    ] id tak ada, berhasil? " + dao.hapus(999999));

        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
        }

        // data yang ditolak sebelum menyentuh basis data (uji validasi)
        try {
            dao.simpan(new JenisPekerjaan("", 50000));
            System.out.println("[validasi ] TIDAK TERJADI - seharusnya ditolak");
        } catch (AppException e) {
            System.out.println("[validasi ] " + e.pesanPengguna());
        }
    }
}