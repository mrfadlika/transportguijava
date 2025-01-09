//class untuk inheritance untuk menampilkan data (jenis transport laut)

public class TransportasiLaut extends Transportasi {
    public TransportasiLaut(String nama, double harga, String asal, String tujuan) {
        super(nama, harga, asal, tujuan, "Laut");
    }

    @Override
    public void tampilkanDetail() {
        System.out.println("Transportasi Laut - " + nama);
        System.out.println("Rute: " + asal + " -> " + tujuan);
        System.out.println("Harga: Rp. " + harga);
    }

    @Override
    public double hitungHarga(int jumlah) {
        return harga * jumlah;
    }
} 