package model;

import exception.ValidationException;

// Entity Servis (data transaksi). Pemilik: modul servis
public class Servis extends BaseEntity {
    private int kendaraanId;
    private int jenisPekerjaanId;
    private String tanggalMasuk;
    private String keluhan;
    private String status;
    private double biaya;

    public Servis() {
    }

    public Servis(int kendaraanId, int jenisPekerjaanId, String tanggalMasuk,
                  String keluhan, String status, double biaya) {
        this.kendaraanId = kendaraanId;
        this.jenisPekerjaanId = jenisPekerjaanId;
        this.tanggalMasuk = tanggalMasuk;
        this.keluhan = keluhan;
        this.status = status;
        this.biaya = biaya;
    }

    public int getKendaraanId() { return kendaraanId; }
    public void setKendaraanId(int kendaraanId) { this.kendaraanId = kendaraanId; }

    public int getJenisPekerjaanId() { return jenisPekerjaanId; }
    public void setJenisPekerjaanId(int jenisPekerjaanId) { this.jenisPekerjaanId = jenisPekerjaanId; }

    public String getTanggalMasuk() { return tanggalMasuk; }
    public void setTanggalMasuk(String tanggalMasuk) { this.tanggalMasuk = tanggalMasuk; }

    public String getKeluhan() { return keluhan; }
    public void setKeluhan(String keluhan) { this.keluhan = keluhan; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getBiaya() { return biaya; }
    public void setBiaya(double biaya) { this.biaya = biaya; }

    @Override
    public void validasi() throws ValidationException {
        if (kendaraanId <= 0) {
            throw new ValidationException("ID Kendaraan", "harus lebih dari 0");
        }
        if (jenisPekerjaanId <= 0) {
            throw new ValidationException("ID Jenis Pekerjaan", "harus lebih dari 0");
        }
        if (tanggalMasuk == null || tanggalMasuk.trim().isEmpty()) {
            throw new ValidationException("Tanggal Masuk", "tidak boleh kosong");
        }
        if (keluhan == null || keluhan.trim().isEmpty()) {
            throw new ValidationException("Keluhan", "tidak boleh kosong");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new ValidationException("Status", "tidak boleh kosong");
        }
        if (biaya < 0) {
            throw new ValidationException("Biaya", "tidak boleh bernilai negatif");
        }
    }

    @Override
    public String label() {
        return keluhan + " (Kendaraan #" + kendaraanId + ")";
    }
}
