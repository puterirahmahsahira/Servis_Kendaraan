package gui;

import dao.KendaraanDao;
import exception.AppException;
import model.Kendaraan;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.util.List;

public class FormKendaraan extends BaseForm {

    private final KendaraanDao dao = new KendaraanDao();

    // JANGAN diberi nilai awal di sini (sesuai aturan siklus konstruktor BaseForm)
    private JTextField txtPlatNomor;
    private JTextField txtMerek;
    private JTextField txtTahun;
    private JTextField txtPelangganId;

    private GenericTableModel<Kendaraan> model;
    private JTable tabel;
    private int idTerpilih;

    public FormKendaraan() {
        super("Sistem Servis - Data Kendaraan", 740, 460);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {
        // Komponen diinisialisasi HANYA di dalam method ini
        txtPlatNomor = new JTextField();
        txtMerek = new JTextField();
        txtTahun = new JTextField();
        txtPelangganId = new JTextField();

        String[] label = {"Plat Nomor", "Merek", "Tahun", "ID Pelanggan"};
        JComponent[] isian = {txtPlatNomor, txtMerek, txtTahun, txtPelangganId};
        String[] kolom = {"Plat Nomor", "Merek", "Tahun", "ID Pelanggan"};

        // Model tabel generik dari Pertemuan 10
        model = new GenericTableModel<Kendaraan>(kolom,
                k -> new Object[]{k.getPlatNomor(), k.getMerek(), k.getTahun(), k.getPelangganId()});

        tabel = new JTable(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabel.getSelectedRow() >= 0) {
                pilihBaris(model.baris(tabel.getSelectedRow()));
            }
        });

        JButton btnBaru = new JButton("Baru");
        JButton btnSimpan = new JButton("Simpan");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");

        btnBaru.addActionListener(e -> kosongkan());
        btnSimpan.addActionListener(e -> simpan());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());

        JPanel panelTombol = new JPanel();
        panelTombol.add(btnBaru);
        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);

        JPanel atas = new JPanel(new BorderLayout());
        atas.add(panelIsian("Isian Kendaraan", label, isian), BorderLayout.CENTER);
        atas.add(panelTombol, BorderLayout.SOUTH);

        setLayout(new BorderLayout(8, 8));
        add(atas, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);
    }

    private void muatTabel() {
        try {
            List<Kendaraan> daftar = dao.ambilSemua();
            model.isiUlang(daftar);
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void kosongkan() {
        idTerpilih = 0;
        txtPlatNomor.setText("");
        txtMerek.setText("");
        txtTahun.setText("");
        txtPelangganId.setText("");
        tabel.clearSelection();
        txtPlatNomor.requestFocus();
    }

    private void pilihBaris(Kendaraan k) {
        idTerpilih = k.getId();
        txtPlatNomor.setText(k.getPlatNomor());
        txtMerek.setText(k.getMerek());
        txtTahun.setText(String.valueOf(k.getTahun()));
        txtPelangganId.setText(String.valueOf(k.getPelangganId()));
    }

    /** Mengambil isian layar menjadi objek Kendaraan */
    private Kendaraan dariIsian() {
        int tahun = 0;
        int pelangganId = 0;
        try {
            tahun = Integer.parseInt(txtTahun.getText().trim());
        } catch (NumberFormatException ignored) {}

        try {
            pelangganId = Integer.parseInt(txtPelangganId.getText().trim());
        } catch (NumberFormatException ignored) {}

        Kendaraan k = new Kendaraan(
                txtPlatNomor.getText().trim(),
                txtMerek.getText().trim(),
                tahun,
                pelangganId
        );
        k.setId(idTerpilih);
        return k;
    }

    private void simpan() {
        try {
            Kendaraan k = dariIsian();
            k.setId(0);
            dao.simpan(k);
            pesan("Tersimpan dengan id " + k.getId() + ".");
            kosongkan();
            muatTabel();
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void ubah() {
        if (idTerpilih == 0) {
            pesan("Pilih dulu satu baris di tabel.");
            return;
        }
        try {
            if (dao.ubah(dariIsian())) {
                pesan("Perubahan disimpan.");
                muatTabel();
            } else {
                pesan("Tidak ada baris yang berubah.");
            }
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void hapus() {
        if (idTerpilih == 0) {
            pesan("Pilih dulu satu baris di tabel.");
            return;
        }
        if (!konfirmasi("Hapus kendaraan dengan id " + idTerpilih + "?")) {
            return;
        }
        try {
            if (dao.hapus(idTerpilih)) {
                pesan("Satu baris dihapus.");
                kosongkan();
                muatTabel();
            } else {
                pesan("Tidak ada baris yang terhapus.");
            }
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormKendaraan().setVisible(true));
    }
}