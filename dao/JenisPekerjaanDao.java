package dao;

import db.Koneksi;
import exception.DataAccessException;
import model.JenisPekerjaan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JenisPekerjaanDao extends BaseDao<JenisPekerjaan> {

    @Override
    protected String namaTabel() {
        return "jenis_pekerjaan";
    }

    @Override
    protected String[] kolomData() {
        return new String[]{"nama", "tarif"};
    }

    @Override
    protected void isiParameter(PreparedStatement ps, JenisPekerjaan jp) throws SQLException {
        // Urutan WAJIB sama dengan urutan di kolomData()
        ps.setString(1, jp.getNama());
        ps.setInt(2, jp.getTarif());
    }

    @Override
    protected JenisPekerjaan dariBaris(ResultSet r) throws SQLException {
        JenisPekerjaan jp = new JenisPekerjaan();
        jp.setId(r.getInt("id"));
        jp.setNama(r.getString("nama"));
        jp.setTarif(r.getInt("tarif"));
        return jp;
    }

    /** Khas modul JenisPekerjaan: pencarian berdasarkan nama */
    public JenisPekerjaan cariNama(String nama) throws DataAccessException {
        String sql = "SELECT * FROM jenis_pekerjaan WHERE nama = ?";
        try (Connection c = Koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nama);
            try (ResultSet r = ps.executeQuery()) {
                return r.next() ? dariBaris(r) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal mencari jenis pekerjaan", e);
        }
    }
}