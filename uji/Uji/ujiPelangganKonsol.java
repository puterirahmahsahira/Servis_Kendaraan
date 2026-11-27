package Uji;

import exception.AppException;
import konsol.PelangganKonsol;
import model.Pelanggan;

import java.util.List;

public class ujiPelangganKonsol {

    public static void main(String[] args) {
        PelangganKonsol data = new PelangganKonsol();

        try {
            Pelanggan p = new Pelanggan("Budi Santoso", "081234567890", "Bengkalis");
            data.simpan(p);
            System.out.println("Tersimpan dengan id " + p.getId() + ".");
        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
            System.out.println("Rincian teknis: " + (e.getCause() != null ? e.getCause().getMessage() : "-"));
        }

        try {
            List<Pelanggan> semua = data.ambilSemua();
            System.out.println("Jumlah baris: " + semua.size());
            for (Pelanggan p : semua) {
                System.out.println(p.label());
            }
        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
            System.out.println("Rincian teknis: " + (e.getCause() != null ? e.getCause().getMessage() : "-"));
        }

        try {
            Pelanggan ditemukan = data.cariTelepon("081234567890");
            System.out.println("Cari telepon ada: " + (ditemukan != null ? ditemukan.label() : "tidak ditemukan"));
        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
            System.out.println("Rincian teknis: " + (e.getCause() != null ? e.getCause().getMessage() : "-"));
        }

        try {
            Pelanggan tidakAda = data.cariTelepon("000000000000");
            System.out.println("Cari telepon tidak ada: " + (tidakAda != null ? tidakAda.label() : "tidak ditemukan"));
        } catch (AppException e) {
            System.out.println(e.pesanPengguna());
            System.out.println("Rincian teknis: " + (e.getCause() != null ? e.getCause().getMessage() : "-"));
        }
    }
}