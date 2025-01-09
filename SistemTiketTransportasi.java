import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;

public class SistemTiketTransportasi extends JFrame {
    private ArrayList<Transportasi> daftarTiket = new ArrayList<>();
    private ArrayList<Pembelian> daftarPembelian = new ArrayList<>();
    
    private JTabbedPane tabbedPane;
    private JPanel panelManajemen, panelPembelian, panelPencarian;
    private JTable tabelTiket, tabelPembelian;
    private DefaultTableModel modelTiket, modelPembelian;
    
    public SistemTiketTransportasi() {
        setTitle("Sistem Pengelolaan Tiket Transportasi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Inisialisasi komponen untuk tab nya
        tabbedPane = new JTabbedPane();
        initManajemenPanel();
        initPembelianPanel();
        initPencarianPanel();
        
        // disini atur apa apa saja tab yang dimaui
        tabbedPane.addTab("Manajemen Tiket", panelManajemen);
        tabbedPane.addTab("Pembelian Tiket", panelPembelian);
        tabbedPane.addTab("Pencarian Tiket", panelPencarian);
        
        add(tabbedPane);
    }

    private void initManajemenPanel() {
        panelManajemen = new JPanel(new BorderLayout());
        
        // Form input untuk manajemen tiket ini dsni
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField txtNama = new JTextField();
        JTextField txtHarga = new JTextField();
        JTextField txtAsal = new JTextField();
        JTextField txtTujuan = new JTextField();
        JComboBox<String> cmbJenis = new JComboBox<>(new String[]{"Darat", "Laut", "Udara"});
        
        formPanel.add(new JLabel("Jenis Transportasi:"));
        formPanel.add(cmbJenis);
        formPanel.add(new JLabel("Nama:"));
        formPanel.add(txtNama);
        formPanel.add(new JLabel("Harga:"));
        formPanel.add(txtHarga);
        formPanel.add(new JLabel("Asal:"));
        formPanel.add(txtAsal);
        formPanel.add(new JLabel("Tujuan:"));
        formPanel.add(txtTujuan);
        
        JButton btnTambah = new JButton("Tambahkan Tiket");
        formPanel.add(btnTambah, BorderLayout.NORTH);
        
        // Tabel tiket na dsni utk manajemen tiket
        String[] kolomTiket = {"Jenis", "Nama", "Harga", "Asal", "Tujuan"};
        modelTiket = new DefaultTableModel(kolomTiket, 0);
        tabelTiket = new JTable(modelTiket);
        
        panelManajemen.add(formPanel, BorderLayout.NORTH);
        panelManajemen.add(new JScrollPane(tabelTiket), BorderLayout.CENTER);
        
        // Action listener kalo buttonnya di klik
        btnTambah.addActionListener(e -> {
            try {
                String jenis = cmbJenis.getSelectedItem().toString();
                String nama = txtNama.getText();
                double harga = Double.parseDouble(txtHarga.getText());
                String asal = txtAsal.getText();
                String tujuan = txtTujuan.getText();
                
                if(nama.isEmpty() || asal.isEmpty() || tujuan.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                    return;
                }
                
                Transportasi tiket = null;
                switch(jenis) {
                    case "Darat":
                        tiket = new TransportasiDarat(nama, harga, asal, tujuan);
                        break;
                    case "Laut":
                        tiket = new TransportasiLaut(nama, harga, asal, tujuan);
                        break;
                    case "Udara":
                        tiket = new TransportasiUdara(nama, harga, asal, tujuan);
                        break;
                }
                
                daftarTiket.add(tiket);
                updateTabelTiket();
                
                // Reset form
                txtNama.setText("");
                txtHarga.setText("");
                txtAsal.setText("");
                txtTujuan.setText("");
                
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga harus berupa angka!");
            }
        });
    }

    private void updateTabelTiket() {
        modelTiket.setRowCount(0);
        for(Transportasi t : daftarTiket) {
            modelTiket.addRow(new Object[]{
                t.getJenisTransportasi(),
                t.getNama(),
                formatRupiah(t.getHarga()),
                t.getAsal(),
                t.getTujuan()
            });
        }
    }

    private void initPembelianPanel() {
        panelPembelian = new JPanel(new BorderLayout(10, 10));
        
        // Form pembelian
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField txtNamaPembeli = new JTextField();
        JComboBox<String> cmbTiket = new JComboBox<>();
        JTextField txtJumlah = new JTextField();
        JLabel lblTotal = new JLabel("Total: Rp. 0");
        JButton btnBeli = new JButton("Beli Tiket");
        btnBeli.setBackground(new Color(46, 204, 113));
        btnBeli.setForeground(Color.WHITE);
        btnBeli.setFont(new Font("Arial", Font.BOLD, 14));
        
        formPanel.add(new JLabel("Nama Pembeli:"));
        formPanel.add(txtNamaPembeli);
        formPanel.add(new JLabel("Pilih Tiket:"));
        formPanel.add(cmbTiket);
        formPanel.add(new JLabel("Jumlah:"));
        formPanel.add(txtJumlah);
        formPanel.add(new JLabel("Total:"));
        formPanel.add(lblTotal);
        formPanel.add(new JLabel(""));
        formPanel.add(btnBeli);
        
        // Bungkus form panel dengan panel yang memiliki padding
        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formContainer.add(formPanel, BorderLayout.NORTH);
        
        // Tabel pembelian
        String[] kolomPembelian = {"Nama Pembeli", "Tiket", "Jumlah", "Total"};
        modelPembelian = new DefaultTableModel(kolomPembelian, 0);
        tabelPembelian = new JTable(modelPembelian);
        JScrollPane scrollPane = new JScrollPane(tabelPembelian);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Pembelian"));
        
        panelPembelian.add(formContainer, BorderLayout.NORTH);
        panelPembelian.add(scrollPane, BorderLayout.CENTER);
        
        // Update total saat jumlah diubah
        txtJumlah.getDocument().addDocumentListener(new DocumentListener() {
            private void updateTotal() {
                try {
                    int selectedIndex = cmbTiket.getSelectedIndex();
                    if (selectedIndex != -1 && !txtJumlah.getText().isEmpty()) {
                        int jumlah = Integer.parseInt(txtJumlah.getText());
                        Transportasi tiketDipilih = daftarTiket.get(selectedIndex);
                        double total = tiketDipilih.hitungHarga(jumlah);
                        lblTotal.setText("Total: " + formatRupiah(total));
                    }
                } catch (NumberFormatException ex) {
                    lblTotal.setText("Total: Rp 0");
                }
            }
            
            @Override
            public void insertUpdate(DocumentEvent e) { updateTotal(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updateTotal(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updateTotal(); }
        });

        // Update combo box tiket ketika tab dibuka
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedComponent() == panelPembelian) {
                cmbTiket.removeAllItems();
                for (Transportasi t : daftarTiket) {
                    cmbTiket.addItem(t.getNama() + " (" + t.getJenisTransportasi() + ")");
                }
            }
        });
        
        // Action listener untuk pembelian
        btnBeli.addActionListener(e -> {
            try {
                // Validasi semua input
                String namaPembeli = txtNamaPembeli.getText().trim();
                String jumlahText = txtJumlah.getText().trim();
                int selectedIndex = cmbTiket.getSelectedIndex();
                
                // Validasi field kosong
                if (namaPembeli.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Nama pembeli tidak boleh kosong!", 
                        "Peringatan", 
                        JOptionPane.WARNING_MESSAGE);
                    txtNamaPembeli.requestFocus();
                    return;
                }
                
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(this, 
                        "Silakan pilih tiket terlebih dahulu!", 
                        "Peringatan", 
                        JOptionPane.WARNING_MESSAGE);
                    cmbTiket.requestFocus();
                    return;
                }
                
                if (jumlahText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Jumlah tiket tidak boleh kosong!", 
                        "Peringatan", 
                        JOptionPane.WARNING_MESSAGE);
                    txtJumlah.requestFocus();
                    return;
                }
                
                // Validasi format jumlah
                int jumlah = Integer.parseInt(jumlahText);
                if (jumlah <= 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Jumlah tiket harus lebih dari 0!", 
                        "Peringatan", 
                        JOptionPane.WARNING_MESSAGE);
                    txtJumlah.requestFocus();
                    return;
                }
                
                // Proses pembelian jika semua validasi berhasil
                Transportasi tiketDipilih = daftarTiket.get(selectedIndex);
                Pembelian pembelian = new Pembelian(namaPembeli, tiketDipilih, jumlah);
                daftarPembelian.add(pembelian);
                
                // Update tabel pembelian
                modelPembelian.addRow(new Object[]{
                    pembelian.getNamaPembeli(),
                    tiketDipilih.getNama(),
                    pembelian.getJumlah(),
                    formatRupiah(pembelian.getTotalHarga())
                });
                
                // Tampilkan detail pemesanan
                StringBuilder detailPesan = new StringBuilder();
                detailPesan.append("Detail Pemesanan:\n\n");
                detailPesan.append("Nama Pembeli: ").append(namaPembeli).append("\n");
                detailPesan.append("Tiket: ").append(tiketDipilih.getNama()).append("\n");
                detailPesan.append("Jenis: ").append(tiketDipilih.getJenisTransportasi()).append("\n");
                detailPesan.append("Rute: ").append(tiketDipilih.getAsal())
                         .append(" → ").append(tiketDipilih.getTujuan()).append("\n");
                detailPesan.append("Jumlah: ").append(jumlah).append(" tiket\n");
                detailPesan.append("Harga per tiket: ").append(formatRupiah(tiketDipilih.getHarga())).append("\n");
                detailPesan.append("Total Pembayaran: ").append(formatRupiah(pembelian.getTotalHarga())).append("\n\n");
                detailPesan.append("Pemesanan berhasil! Terima kasih telah memesan.");
                
                // Tampilkan dialog dengan detail pemesanan
                JOptionPane.showMessageDialog(this,
                    detailPesan.toString(),
                    "Detail Pemesanan",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Reset form
                txtNamaPembeli.setText("");
                txtJumlah.setText("");
                cmbTiket.setSelectedIndex(0);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Format jumlah tidak valid! Masukkan angka yang benar.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                txtJumlah.requestFocus();
            }
        });
    }

    private void initPencarianPanel() {
        panelPencarian = new JPanel(new BorderLayout());
        
        // Panel filter
        JPanel filterPanel = new JPanel(new FlowLayout());
        JComboBox<String> cmbFilter = new JComboBox<>(new String[]{"Semua", "Darat", "Laut", "Udara"});
        JButton btnCari = new JButton("Cari Tiket");
        
        filterPanel.add(new JLabel("Jenis Transportasi:"));
        filterPanel.add(cmbFilter);
        filterPanel.add(btnCari);
        
        // Tabel hasil pencarian
        String[] kolomPencarian = {"Jenis", "Nama", "Harga", "Asal", "Tujuan"};
        DefaultTableModel modelPencarian = new DefaultTableModel(kolomPencarian, 0);
        JTable tabelPencarian = new JTable(modelPencarian);
        
        panelPencarian.add(filterPanel, BorderLayout.NORTH);
        panelPencarian.add(new JScrollPane(tabelPencarian), BorderLayout.CENTER);
        
        // Action listener untuk pencarian
        btnCari.addActionListener(e -> {
            String filter = cmbFilter.getSelectedItem().toString();
            modelPencarian.setRowCount(0);
            
            for (Transportasi t : daftarTiket) {
                if (filter.equals("Semua") || t.getJenisTransportasi().equals(filter)) {
                    modelPencarian.addRow(new Object[]{
                        t.getJenisTransportasi(),
                        t.getNama(),
                        formatRupiah(t.getHarga()),
                        t.getAsal(),
                        t.getTujuan()
                    });
                }
            }
        });
    }

    //disini ki kalau mau atur format rupiah
    private String formatRupiah(double nilai) {
        return String.format("Rp %,.0f", nilai).replace(",", ".");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SistemTiketTransportasi().setVisible(true);
        });
    }
} 