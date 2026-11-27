package gui;

import dao.PelangganDao;
import exception.AppException;
import model.Pelanggan;

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

public class FormPelanggan extends BaseForm {

    private final PelangganDao dao = new PelangganDao();

    // Field komponen isian
    private JTextField txtNama;
    private JTextField txtTelepon;
    private JTextField txtAlamat;

    private GenericTableModel<Pelanggan> model;
    private JTable tabel;
    private int idTerpilih;

    public FormPelanggan() {
        super("Sistem Servis Kendaraan - Data Pelanggan", 720, 440);
        muatTabel();
    }

    @Override
    protected void susunKomponen() {
        txtNama = new JTextField();
        txtTelepon = new JTextField();
        txtAlamat = new JTextField();

        String[] label = {"Nama", "Telepon", "Alamat"};
        JComponent[] isian = {txtNama, txtTelepon, txtAlamat};
        String[] kolom = {"Nama", "Telepon", "Alamat"};

        model = new GenericTableModel<Pelanggan>(kolom,
                p -> new Object[]{p.getNama(), p.getTelepon(), p.getAlamat()});

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
        atas.add(panelIsian("Isian Data Pelanggan", label, isian), BorderLayout.CENTER);
        atas.add(panelTombol, BorderLayout.SOUTH);

        setLayout(new BorderLayout(8, 8));
        add(atas, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);
    }

    private void muatTabel() {
        try {
            List<Pelanggan> daftar = dao.ambilSemua();
            model.isiUlang(daftar);
        } catch (AppException e) {
            tampilkanKesalahan(e);
        }
    }

    private void kosongkan() {
        idTerpilih = 0;
        txtNama.setText("");
        txtTelepon.setText("");
        txtAlamat.setText("");
        tabel.clearSelection();
        txtNama.requestFocus();
    }

    private void pilihBaris(Pelanggan p) {
        idTerpilih = p.getId();
        txtNama.setText(p.getNama());
        txtTelepon.setText(p.getTelepon());
        txtAlamat.setText(p.getAlamat());
    }

    private Pelanggan dariIsian() {
        Pelanggan p = new Pelanggan(txtNama.getText(), txtTelepon.getText(), txtAlamat.getText());
        p.setId(idTerpilih);
        return p;
    }

    private void simpan() {
        try {
            Pelanggan p = dariIsian();
            p.setId(0);
            dao.simpan(p);
            pesan("Pelanggan berhasil disimpan dengan id " + p.getId() + ".");
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
                pesan("Perubahan data pelanggan disimpan.");
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
        if (!konfirmasi("Hapus pelanggan dengan id " + idTerpilih + "?")) {
            return;
        }
        try {
            if (dao.hapus(idTerpilih)) {
                pesan("Satu pelanggan berhasil dihapus.");
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
                new FormPelanggan().setVisible(true);
            }
        });
    }
}