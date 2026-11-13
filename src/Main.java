import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=========================================");
        System.out.println("    SISTEM MANAJEMEN SERVIS KENDARAAN    ");
        System.out.println("=========================================");
        System.out.println("1. Tambah Data Servis");
        System.out.println("2. Lihat Antrean Servis");
        System.out.println("3. Cetak Rincian Biaya");
        System.out.println("0. Keluar");
        System.out.println("=========================================");
        System.out.print("Pilih menu: ");
        
        int pilihan = scanner.nextInt();
        System.out.println("Menu yang dipilih: " + pilihan);
        System.out.println("Program siap dikembangkan lebih lanjut.");
        
        scanner.close();
    }
}
