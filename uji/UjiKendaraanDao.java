package uji;

import dao.KendaraanDao;
import exception.AppException;
import model.Kendaraan;

import java.util.List;

public class UjiKendaraanDao {
    public static void main(String[] args) {
        KendaraanDao dao = new KendaraanDao();

        try {
            // Asumsikan pelanggan_id = 1 sudah ada di tabel pelanggan
            Kendaraan baru = new Kendaraan("BM4321BS", "Yamaha NMAX", 2025, 1);
            dao.simpan(baru);
            System.out.println("[simpan    ] id dari AUTO_INCREMENT = " + baru.getId());

            Kendaraan dibaca = dao.cariId(baru.getId());
            if (dibaca != null) {
                System.out.println("[cariId    ] " + dibaca.label());
            }

            Kendaraan lewatPlat = dao.cariPlatNomor("BM1234AA");
            if (lewatPlat != null) {
                System.out.println("[cariPlat  ] " + lewatPlat.label());
            }

            if (dibaca != null) {
                dibaca.setMerek("Yamaha All New NMAX");
                System.out.println("[ubah      ] berhasil? " + dao.ubah(dibaca));
            }

            List<Kendaraan> semua = dao.ambilSemua();
            System.out.println("[ambilSemua] jumlah baris = " + semua.size());
            for (Kendaraan k : semua) {
                System.out.println("   " + k.getId() + ". " + k.label());
            }

            System.out.println("[hapus     ] berhasil? " + dao.hapus(baru.getId()));
            System.out.println("[hapus     ] id tak ada, berhasil? " + dao.hapus(999999));

        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
        }

        // Pengujian data tidak sah
        try {
            dao.simpan(new Kendaraan("", "Tanpa Plat", 2020, 1));
            System.out.println("[validasi  ] TIDAK TERJADI - seharusnya ditolak");
        } catch (AppException e) {
            System.out.println("[validasi  ] " + e.pesanPengguna());
        }
    }
}