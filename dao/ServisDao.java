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
        return new String[]{"kendaraan_id", "tanggal_masuk", "keluhan", "status", "biaya"};
    }

    @Override
    protected void isiParameter(PreparedStatement ps, Servis objek) throws SQLException {
        ps.setString(1, objek.getKendaraanId());
        ps.setString(2, objek.getTanggalMasuk());
        ps.setString(3, objek.getKeluhan());
        ps.setString(4, objek.getStatus());
        ps.setDouble(5, objek.getBiaya());
    }

    @Override
    protected Servis dariBaris(ResultSet r) throws SQLException {
        Servis s = new Servis();
        s.setId(r.getInt("id"));
        s.setKendaraanId(r.getString("kendaraan_id"));
        s.setTanggalMasuk(r.getString("tanggal_masuk"));
        s.setKeluhan(r.getString("keluhan"));
        s.setStatus(r.getString("status"));
        s.setBiaya(r.getDouble("biaya"));
        return s;
    }
}