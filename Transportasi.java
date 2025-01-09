public abstract class Transportasi {
    protected String nama;
    protected double harga;
    protected String asal;
    protected String tujuan;
    protected String jenisTransportasi;

    public Transportasi(String nama, double harga, String asal, String tujuan, String jenisTransportasi) {
        this.nama = nama;
        this.harga = harga;
        this.asal = asal;
        this.tujuan = tujuan;
        this.jenisTransportasi = jenisTransportasi;
    }

    public abstract void tampilkanDetail();
    public abstract double hitungHarga(int jumlah);

    // Getter dan Setter
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public String getAsal() { return asal; }
    public String getTujuan() { return tujuan; }
    public String getJenisTransportasi() { return jenisTransportasi; }
} 