//class untuk inheritance untuk menampilkan data (jenis transport udara)

public class TransportasiDarat extends Transportasi {
    public TransportasiDarat(String nama, double harga, String asal, String tujuan) {
        super(nama, harga, asal, tujuan, "Darat");
    }

    @Override
    public void tampilkanDetail() {
        System.out.println("Transportasi Darat - " + nama);
        System.out.println("Rute: " + asal + " -> " + tujuan);
        System.out.println("Harga: Rp. " + harga);
    }

    @Override
    public double hitungHarga(int jumlah) {
        return harga * jumlah;
    }
} 