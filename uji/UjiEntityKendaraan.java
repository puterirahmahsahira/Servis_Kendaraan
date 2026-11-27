package uji;

import exception.ValidationException;
import model.Kendaraan;

public class UjiEntityKendaraan {
    public static void main(String[] args) {
        System.out.println("=== PENGUJIAN ENTITAS KENDARAAN ===");

        // -------------------------------------------------------------
        // 1. UJI KASUS SUKSES (Data Sah / Valid)
        // -------------------------------------------------------------
        System.out.println("\n[UJI 1] Membuat kendaraan dengan data sah...");
        Kendaraan k1 = new Kendaraan("BM1234AA", "Toyota Avanza", 2022, 1);
        k1.setId(10); // Simulasi ID dari database

        try {
            // Validasi harus lolos tanpa melempar exception
            k1.validasi();
            System.out.println("  ✓ Validasi berhasil: Data sah diterima!");
            System.out.println("  ✓ ID Kendaraan     : " + k1.getId());
            System.out.println("  ✓ Plat Nomor       : " + k1.getPlatNomor());
            System.out.println("  ✓ Merek            : " + k1.getMerek());
            System.out.println("  ✓ Tahun            : " + k1.getTahun());
            System.out.println("  ✓ ID Pelanggan     : " + k1.getPelangganId());
            System.out.println("  ✓ Format Label     : " + k1.label());
        } catch (ValidationException e) {
            System.out.println("  ✗ GAGAL: Seharusnya data ini sah! Pesan: " + e.pesanPengguna());
        }

        // -------------------------------------------------------------
        // 2. UJI KASUS GAGAL: Plat Nomor Kosong
        // -------------------------------------------------------------
        System.out.println("\n[UJI 2] Validasi plat nomor kosong...");
        Kendaraan k2 = new Kendaraan("", "Honda Civic", 2021, 1);
        try {
            k2.validasi();
            System.out.println("  ✗ GAGAL: Seharusnya ditolak karena plat nomor kosong!");
        } catch (ValidationException e) {
            System.out.println("  ✓ Berhasil ditolak: " + e.pesanPengguna());
        }

        // -------------------------------------------------------------
        // 3. UJI KASUS GAGAL: Merek Kosong
        // -------------------------------------------------------------
        System.out.println("\n[UJI 3] Validasi merek kosong...");
        Kendaraan k3 = new Kendaraan("BM5555XX", "   ", 2020, 1);
        try {
            k3.validasi();
            System.out.println("  ✗ GAGAL: Seharusnya ditolak karena merek kosong!");
        } catch (ValidationException e) {
            System.out.println("  ✓ Berhasil ditolak: " + e.pesanPengguna());
        }

        // -------------------------------------------------------------
        // 4. UJI KASUS GAGAL: Tahun Tidak Valid
        // -------------------------------------------------------------
        System.out.println("\n[UJI 4] Validasi tahun perakitan tidak logis (misal: tahun 1850)...");
        Kendaraan k4 = new Kendaraan("BM6789YY", "Yamaha Aerox", 1850, 1);
        try {
            k4.validasi();
            System.out.println("  ✗ GAGAL: Seharusnya ditolak karena tahun tidak valid!");
        } catch (ValidationException e) {
            System.out.println("  ✓ Berhasil ditolak: " + e.pesanPengguna());
        }

        // -------------------------------------------------------------
        // 5. UJI KASUS GAGAL: ID Pelanggan Nol / Negatif
        // -------------------------------------------------------------
        System.out.println("\n[UJI 5] Validasi ID Pelanggan (FK) tidak boleh <= 0...");
        Kendaraan k5 = new Kendaraan("BM9999ZZ", "Suzuki Satria", 2023, 0);
        try {
            k5.validasi();
            System.out.println("  ✗ GAGAL: Seharusnya ditolak karena ID Pelanggan <= 0!");
        } catch (ValidationException e) {
            System.out.println("  ✓ Berhasil ditolak: " + e.pesanPengguna());
        }

        System.out.println("\n=== SELURUH PENGUJIAN SELESAI ===");
    }
}