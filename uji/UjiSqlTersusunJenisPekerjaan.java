package Uji;

import dao.JenisPekerjaanDao;

public class UjiSqlTersusunJenisPekerjaan {
    public static void main(String[] args) {
        JenisPekerjaanDao dao = new JenisPekerjaanDao();
        System.out.println("INSERT :");
        System.out.println("  " + dao.lihatSqlInsert());
        System.out.println("UPDATE :");
        System.out.println("  " + dao.lihatSqlUpdate());
    }
}