package dao;

import db.Koneksi;
import exception.DataAccessException;
import model.Kendaraan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KendaraanDao extends BaseDao<Kendaraan> {

    @Override
    protected String namaTabel() {
        return "kendaraan";
    }

    @Override
    protected String[] kolomData() {
        // Urutan nama kolom di MySQL (tanpa kolom primary key id)
        return new String[]{"plat_nomor", "merek", "tahun", "pelanggan_id"};
    }

    @Override
    protected void isiParameter(PreparedStatement ps, Kendaraan k) throws SQLException {
        // Urutan parameter di sini WAJIB SAMA PERSIS dengan urutan di kolomData()
        ps.setString(1, k.getPlatNomor());
        ps.setString(2, k.getMerek());
        ps.setInt(3, k.getTahun());
        ps.setInt(4, k.getPelangganId());
    }

    @Override
    protected Kendaraan dariBaris(ResultSet r) throws SQLException {
        Kendaraan k = new Kendaraan();

        // Membaca kolom primary key id (aman jika di database bernama 'id' atau 'id_kendaraan')
        try {
            k.setId(r.getInt("id"));
        } catch (SQLException e) {
            k.setId(r.getInt("id_kendaraan"));
        }

        k.setPlatNomor(r.getString("plat_nomor"));
        k.setMerek(r.getString("merek"));
        k.setTahun(r.getInt("tahun"));
        k.setPelangganId(r.getInt("pelanggan_id"));

        return k;
    }

    /**
     * Method khas modul kendaraan — tidak diangkat ke BaseDao (Keputusan 3 Jobsheet 12).
     * Digunakan untuk mencari kendaraan berdasarkan nomor polisi / plat nomor.
     */
    public Kendaraan cariPlatNomor(String platNomor) throws DataAccessException {
        String sql = "SELECT * FROM kendaraan WHERE plat_nomor = ?";
        try (Connection c = Koneksi.buka();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, platNomor);
            try (ResultSet r = ps.executeQuery()) {
                return r.next() ? dariBaris(r) : null;
            }
        } catch (SQLException e) {
            throw new DataAccessException("gagal mencari kendaraan berdasarkan plat nomor", e);
        }
    }
}