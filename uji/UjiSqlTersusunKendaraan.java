package uji;

import dao.KendaraanDao;

public class UjiSqlTersusunKendaraan {
    public static void main(String[] args) {
        KendaraanDao dao = new KendaraanDao();
        System.out.println("INSERT :");
        System.out.println("  " + dao.lihatSqlInsert());
        System.out.println("UPDATE :");
        System.out.println("  " + dao.lihatSqlUpdate());
    }
}