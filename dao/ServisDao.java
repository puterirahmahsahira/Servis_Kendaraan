package dao;

import model.Servis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServisDao extends BaseDao<Servis> {

    @Override
    protected String namaTabel() {
        return "servis";
    }

    @Override
    protected String[] kolomData() {
        return new String[]{
                "kendaraan_id", "jenis_pekerjaan_id",
                "tanggal_masuk", "keluhan", "status", "biaya"
        };
    }

    @Override
    protected void isiParameter(PreparedStatement ps, Servis objek) throws SQLException {
        ps.setInt(1, objek.getKendaraanId());
        ps.setInt(2, objek.getJenisPekerjaanId());
        ps.setString(3, objek.getTanggalMasuk());
        ps.setString(4, objek.getKeluhan());
        ps.setString(5, objek.getStatus());
        ps.setDouble(6, objek.getBiaya());
    }

    @Override
    protected Servis dariBaris(ResultSet r) throws SQLException {
        Servis s = new Servis();
        s.setId(r.getInt("id"));
        s.setKendaraanId(r.getInt("kendaraan_id"));
        s.setJenisPekerjaanId(r.getInt("jenis_pekerjaan_id"));
        s.setTanggalMasuk(r.getString("tanggal_masuk"));
        s.setKeluhan(r.getString("keluhan"));
        s.setStatus(r.getString("status"));
        s.setBiaya(r.getDouble("biaya"));
        return s;
    }
}
