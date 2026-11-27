package Uji;
import exception.ValidationException;
import model.Pelanggan;
public class UjiPelanggan {
    public static void main(String[] args) {
        coba("lengkap dan benar", new Pelanggan("Budi Santoso", "081234567890", "Bengkalis"));
        coba("nama kosong", new Pelanggan(" ", "081234567890", "Bengkalis"));
        coba("nama null", new Pelanggan(null, "081234567890", "Bengkalis"));
        coba("telepon kosong", new Pelanggan("Budi Santoso", "", "Bengkalis"));
        coba("telepon 4 angka", new Pelanggan("Budi Santoso", "0812", "Bengkalis"));
        coba("telepon ada huruf", new Pelanggan("Budi Santoso", "0812ABC4567", "Bengkalis"));
        coba("alamat kosong", new Pelanggan("Budi Santoso", "081234567890", ""));
    }
    private static void coba(String keterangan, Pelanggan p) {
        try {
            p.validasi();
            System.out.println("DITERIMA : " + keterangan + " -> " + p.label());
        } catch (ValidationException e) {
            System.out.println("DITOLAK : " + keterangan + " -> " + e.pesanPengguna());
        }
    }
}