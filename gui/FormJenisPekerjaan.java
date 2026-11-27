package gui;

import dao.JenisPekerjaanDao;
import exception.AppException;
import model.JenisPekerjaan;

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

public class FormJenisPekerjaan extends BaseForm {

    private final JenisPekerjaanDao dao = new JenisPekerjaanDao();

    private JTextField txtNama;
    private JTextField txtTarif;

    private GenericTableModel<JenisPekerjaan> model;
    private JTable tabel;
    private int idTerpilih;

    public FormJenisPekerjaan() {
        super("Servis Kendaraan - Data Jenis Pekerjaan", 680, 420);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {
        txtNama = new JTextField();
        txtTarif = new JTextField();

        String[] label = {"Nama Pekerjaan", "Tarif (Rp)"};
        JComponent[] isian = {txtNama, txtTarif};

        String[] kolom = {"Nama Pekerjaan", "Tarif"};
        model = new GenericTableModel<JenisPekerjaan>(kolom,
                jp -> new Object[]{jp.getNama(), "Rp " + jp.getTarif()});

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
        atas.add(panelIsian("Isian Jenis Pekerjaan", label, isian), BorderLayout.CENTER);
        atas.add(panelTombol, BorderLayout.SOUTH);

        setLayout(new BorderLayout(8, 8));
        add(atas, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);
    }

    private void muatTabel() {
        try {
            List<JenisPekerjaan> daftar = dao.ambilSemua();
            model.isiUlang(daftar);
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void kosongkan() {
        idTerpilih = 0;
        txtNama.setText("");
        txtTarif.setText("");
        tabel.clearSelection();
        txtNama.requestFocus();
    }

    private void pilihBaris(JenisPekerjaan jp) {
        idTerpilih = jp.getId();
        txtNama.setText(jp.getNama());
        txtTarif.setText(String.valueOf(jp.getTarif()));
    }

    private JenisPekerjaan dariIsian() {
        int tarif = 0;
        try {
            tarif = Integer.parseInt(txtTarif.getText().trim());
        } catch (NumberFormatException e) {
            tarif = -1; // jika kosong atau bukan angka, diisi -1 agar ditolak validasi
        }
        JenisPekerjaan jp = new JenisPekerjaan(txtNama.getText(), tarif);
        jp.setId(idTerpilih);
        return jp;
    }

    private void simpan() {
        try {
            JenisPekerjaan jp = dariIsian();
            jp.setId(0);
            dao.simpan(jp);
            pesan("Tersimpan dengan id " + jp.getId() + ".");
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
        if (!konfirmasi("Hapus jenis pekerjaan dengan id " + idTerpilih + "?")) {
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
        SwingUtilities.invokeLater(() -> new FormJenisPekerjaan().setVisible(true));
    }
}