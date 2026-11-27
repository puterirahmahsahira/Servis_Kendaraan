package dao;

import db.Koneksi;
import exception.DataAccessException;
import model.Pelanggan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PelangganDao extends BaseDao<Pelanggan> {

    @Override
    protected String namaTabel() {
        return "pelanggan";
    }

    @Override
    protected String[] kolomData() {
        return new String[]{"nama", "telepon", "alamat"};
    }

    @Override
    protected void isiParameter(PreparedStatement ps, Pelanggan p) throws SQLException {
        ps.setString(1, p.getNama());
        ps.setString(2, p.getTelepon());
        ps.setString(3, p.getAlamat());
    }

    @Override
    protected Pelanggan dariBaris(ResultSet r) throws SQLException {
        Pelanggan p = new Pelanggan();
        p.setId(r.getInt("id"));
        p.setNama(r.getString("nama"));
        p.setTelepon(r.getString("telepon"));
        p.setAlamat(r.getString("alamat"));
        return p;
    }

    /** Pencarian khas modul pelanggan berdasarkan nomor telepon */
    public Pelanggan cariTelepon(String telepon) throws DataAccessException {
        String sql = "SELECT * FROM pelanggan WHERE telepon = ?";
        try (Connection c = Koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, telepon);
            try (ResultSet r = ps.executeQuery()) {
                return r.next() ? dariBaris(r) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal mencari pelanggan", e);
        }
    }
}