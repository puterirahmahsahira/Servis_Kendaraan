package model;

import exception.ValidationException;

/**
 * Model entitas untuk tabel kendaraan pada sistem Servis Kendaraan.
 * Menurunkan BaseEntity agar kompatibel dengan BaseDao dan BaseForm.
 */
public class Kendaraan extends BaseEntity {

    // Atribut privat sesuai kolom tabel di MySQL
    private String platNomor;
    private String merek;
    private int tahun;
    private int pelangganId;

    // Konstruktor tanpa parameter (dibutuhkan oleh dariBaris pada DAO)
    public Kendaraan() {
    }

    // Konstruktor dengan parameter lengkap
    public Kendaraan(String platNomor, String merek, int tahun, int pelangganId) {
        this.platNomor = platNomor;
        this.merek = merek;
        this.tahun = tahun;
        this.pelangganId = pelangganId;
    }

    // =========================================================================
    // GETTER & SETTER
    // =========================================================================

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public int getPelangganId() {
        return pelangganId;
    }

    public void setPelangganId(int pelangganId) {
        this.pelangganId = pelangganId;
    }

    // =========================================================================
    // IMPLEMENTASI METHOD DARI BaseEntity
    // =========================================================================

    /**
     * Memvalidasi aturan bisnis data kendaraan sebelum query dikirim ke database.
     * Menggunakan .trim().isEmpty() agar tetap kompatibel dengan Java 8.
     */
        @Override
    public void validasi() throws ValidationException {
        if (platNomor == null || platNomor.trim().isEmpty()) {
            throw new ValidationException("plat nomor", "tidak boleh kosong");
        }
        if (merek == null || merek.trim().isEmpty()) {
            throw new ValidationException("merek", "tidak boleh kosong");
        }
        if (tahun < 1900 || tahun > 2100) {
            throw new ValidationException("tahun", "harus antara 1900 - 2100");
        }
        if (pelangganId <= 0) {
            throw new ValidationException("ID pelanggan", "harus lebih dari 0");
        }
    }

    /**
     * Menghasilkan teks ringkas untuk representasi kendaraan di tampilan tabel atau combo box.
     */
    @Override
    public String label() {
        return platNomor + " - " + merek + " (" + tahun + ")";
    }
}
