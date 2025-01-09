public class Pembelian {
    private String namaPembeli;
    private Transportasi tiket;
    private int jumlah;
    private double totalHarga;

    public Pembelian(String namaPembeli, Transportasi tiket, int jumlah) {
        this.namaPembeli = namaPembeli;
        this.tiket = tiket;
        this.jumlah = jumlah;
        this.totalHarga = tiket.hitungHarga(jumlah);
    }

    // Getter dipake dsni
    public String getNamaPembeli() { return namaPembeli; }
    public Transportasi getTiket() { return tiket; }
    public int getJumlah() { return jumlah; }
    public double getTotalHarga() { return totalHarga; }
} 