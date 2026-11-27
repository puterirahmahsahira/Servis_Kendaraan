package Uji;

import dao.PelangganDao;

public class UjiSqlTersusunPelanggan {
    public static void main(String[] args) {
        PelangganDao dao = new PelangganDao();
        System.out.println("INSERT :");
        System.out.println("  " + dao.lihatSqlInsert());
        System.out.println("UPDATE :");
        System.out.println("  " + dao.lihatSqlUpdate());
    }
}