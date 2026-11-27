package model;

import exception.ValidationException;

/** MODUL MASTER: JenisPekerjaan. */
public class JenisPekerjaan extends BaseEntity {
    private String nama;
    private int tarif;

    // Constructor kosong (WAJIB ADA untuk DAO)
    public JenisPekerjaan() {
    }

    // Constructor berparameter
    public JenisPekerjaan(String nama, int tarif) {
        this.nama = nama;
        this.tarif = tarif;
    }
    // Getter dan Setter
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public int getTarif() {
        return tarif;
    }
    public void setTarif(int tarif) {
        this.tarif = tarif;
    }
    @Override
    public void validasi() throws ValidationException {
        if (nama == null || nama.trim().isEmpty()) {
            throw new ValidationException("nama", "tidak boleh kosong");
        }
        if (tarif < 0) {
            throw new ValidationException("tarif", "tidak boleh kurang dari nol");
        }
    }
    @Override
    public String label() {
        return nama + " (Rp " + tarif + ")";
    }
}