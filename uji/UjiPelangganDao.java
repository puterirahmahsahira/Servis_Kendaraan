package Uji;

import dao.PelangganDao;
import exception.AppException;
import model.Pelanggan;
import java.util.List;

public class UjiPelangganDao {
    public static void main(String[] args) {
        PelangganDao dao = new PelangganDao();
        try {
            // Gunakan nomor telepon uji coba yang belum ada di tabel Anda
            Pelanggan baru = new Pelanggan("Andi Saputra", "081299887766", "Jl. Antara, Bengkalis");
            dao.simpan(baru);
            System.out.println("[simpan     ] id dari AUTO_INCREMENT = " + baru.getId());

            Pelanggan dibaca = dao.cariId(baru.getId());
            System.out.println("[cariId     ] " + dibaca.label());

            Pelanggan lewatTelepon = dao.cariTelepon("081299887766");
            System.out.println("[cariTelepon] " + lewatTelepon.label());

            dibaca.setAlamat("Jl. Rumbia No. 10");
            System.out.println("[ubah       ] berhasil? " + dao.ubah(dibaca));

            List<Pelanggan> semua = dao.ambilSemua();
            System.out.println("[ambilSemua ] jumlah baris = " + semua.size());
            for (Pelanggan p : semua) {
                System.out.println("   " + p.getId() + ". " + p.label());
            }

            System.out.println("[hapus      ] berhasil? " + dao.hapus(baru.getId()));
            System.out.println("[hapus      ] id tak ada, berhasil? " + dao.hapus(999999));

        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
        }

        // Uji data yang ditolak sebelum menyentuh basis data
        try {
            // Isian salah: nomor telepon tidak sesuai aturan validasi
            dao.simpan(new Pelanggan("Pelanggan Salah", "123", "Bengkalis"));
            System.out.println("[validasi   ] TIDAK TERJADI - seharusnya ditolak");
        } catch (AppException e) {
            System.out.println("[validasi   ] " + e.pesanPengguna());
        }
    }
}