//class untuk inheritance untuk menampilkan data (jenis transport udara)

public class TransportasiUdara extends Transportasi {
    public TransportasiUdara(String nama, double harga, String asal, String tujuan) {
        super(nama, harga, asal, tujuan, "Udara");
    }

    @Override
    public void tampilkanDetail() {
        System.out.println("Transportasi Udara - " + nama);
        System.out.println("Rute: " + asal + " -> " + tujuan);
        System.out.println("Harga: Rp. " + harga);
    }

    @Override
    public double hitungHarga(int jumlah) {
        return harga * jumlah;
    }
} 