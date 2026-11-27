package gui;

import dao.ServisDao;
import exception.AppException;
import model.Servis;

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

public class FormServis extends BaseForm {

    private final ServisDao dao = new ServisDao();

    // JANGAN diberi nilai awal di sini
    private JTextField txtKendaraanId;
    private JTextField txtJenisPekerjaanId;
    private JTextField txtTanggalMasuk;
    private JTextField txtKeluhan;
    private JTextField txtStatus;
    private JTextField txtBiaya;

    private GenericTableModel<Servis> model;
    private JTable tabel;
    private int idTerpilih;

    public FormServis() {
        super("Servis Kendaraan - Data Servis", 800, 500);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {
        txtKendaraanId = new JTextField();
        txtJenisPekerjaanId = new JTextField();
        txtTanggalMasuk = new JTextField();
        txtKeluhan = new JTextField();
        txtStatus = new JTextField();
        txtBiaya = new JTextField();

        String[] label = {
                "ID Kendaraan", "ID Jenis Pekerjaan",
                "Tanggal Masuk", "Keluhan", "Status", "Biaya"
        };
        JComponent[] isian = {
                txtKendaraanId, txtJenisPekerjaanId,
                txtTanggalMasuk, txtKeluhan, txtStatus, txtBiaya
        };
        String[] kolom = {
                "ID Kendaraan", "ID Jenis Pekerjaan",
                "Tanggal Masuk", "Keluhan", "Status", "Biaya"
        };

        model = new GenericTableModel<Servis>(kolom,
                s -> new Object[]{
                        s.getKendaraanId(), s.getJenisPekerjaanId(),
                        s.getTanggalMasuk(), s.getKeluhan(),
                        s.getStatus(), s.getBiaya()
                });

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
        atas.add(panelIsian("Isian Data Servis", label, isian), BorderLayout.CENTER);
        atas.add(panelTombol, BorderLayout.SOUTH);

        setLayout(new BorderLayout(8, 8));
        add(atas, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);
    }

    private void muatTabel() {
        try {
            List<Servis> daftar = dao.ambilSemua();
            model.isiUlang(daftar);
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void kosongkan() {
        idTerpilih = 0;
        txtKendaraanId.setText("");
        txtJenisPekerjaanId.setText("");
        txtTanggalMasuk.setText("");
        txtKeluhan.setText("");
        txtStatus.setText("");
        txtBiaya.setText("");
        tabel.clearSelection();
        txtKendaraanId.requestFocus();
    }

    private void pilihBaris(Servis s) {
        idTerpilih = s.getId();
        txtKendaraanId.setText(String.valueOf(s.getKendaraanId()));
        txtJenisPekerjaanId.setText(String.valueOf(s.getJenisPekerjaanId()));
        txtTanggalMasuk.setText(s.getTanggalMasuk());
        txtKeluhan.setText(s.getKeluhan());
        txtStatus.setText(s.getStatus());
        txtBiaya.setText(String.valueOf(s.getBiaya()));
    }

    /** Mengambil isian layar menjadi objek Servis. */
    private Servis dariIsian() {
        int kendaraanId = 0;
        int jenisPekerjaanId = 0;
        double biayaVal = -1;

        try {
            kendaraanId = Integer.parseInt(txtKendaraanId.getText().trim());
        } catch (NumberFormatException ignored) {}

        try {
            jenisPekerjaanId = Integer.parseInt(txtJenisPekerjaanId.getText().trim());
        } catch (NumberFormatException ignored) {}

        try {
            biayaVal = Double.parseDouble(txtBiaya.getText().trim());
        } catch (NumberFormatException ignored) {}

        Servis s = new Servis(
                kendaraanId,
                jenisPekerjaanId,
                txtTanggalMasuk.getText(),
                txtKeluhan.getText(),
                txtStatus.getText(),
                biayaVal
        );
        s.setId(idTerpilih);
        return s;
    }

    private void simpan() {
        try {
            Servis s = dariIsian();
            s.setId(0);
            dao.simpan(s);
            pesan("Tersimpan dengan id " + s.getId() + ".");
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
        if (!konfirmasi("Hapus data servis dengan id " + idTerpilih + "?")) {
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FormServis().setVisible(true);
            }
        });
    }
}
