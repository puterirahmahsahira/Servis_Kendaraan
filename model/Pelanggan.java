package model;

import exception.ValidationException;

/** Entity pelanggan (data master). pemilik: modul Pelanggan. */
public class Pelanggan extends BaseEntity {

    private String nama;
    private String telepon;
    private String alamat;

    public Pelanggan(){
    }
    public Pelanggan(String nama, String telepon, String alamat) {
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
    }
    public String getNama() {return nama;}
    public void setNama(String nama) {this.nama = nama;}

    public String getTelepon() {return telepon;}
    public void setTelepon(String telepon) {this.telepon = telepon;}

    public String getAlamat() {return alamat;}
    public void setAlamat(String alamat) {this.alamat = alamat;}

    @Override
    public void validasi() throws ValidationException{
        // urutan: cek null/kosong dulu, baru cek format
        if (nama == null || nama.trim().isEmpty()){
            throw new ValidationException("nama", "tidak boleh kosong");
        }
        if (telepon == null || telepon.trim().isEmpty()){
            throw new ValidationException("telepon", "tidak boleh kosong");
        }
        if (!telepon.matches("\\d{10,13}")){
            throw new ValidationException("telepon", "harus 10 sampai 13 angka");
        }
        if (alamat == null || alamat.trim().isEmpty()){
            throw new ValidationException("alamat", "tidak boleh kosong");
        }
    }
    @Override
    public String label() {
        return nama + " (" + telepon + ")";
    }


}