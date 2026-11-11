
package com.mycompany.smartmobility;

public class Laporan {

    private int idLaporan;
    private String jenisKerusakan;
    private String lokasi;
    private String deskripsi;
    private String status;

    public Laporan(int idLaporan, String jenisKerusakan,
                   String lokasi, String deskripsi) {

        this.idLaporan = idLaporan;
        this.jenisKerusakan = jenisKerusakan;
        this.lokasi = lokasi;
        this.deskripsi = deskripsi;
        this.status = "Menunggu Verifikasi";
    }

    public int getIdLaporan() {
        return idLaporan;
    }

    public String getJenisKerusakan() {
        return jenisKerusakan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void tampilkanLaporan() {
        System.out.println("ID Laporan: " + idLaporan);
        System.out.println("Jenis Kerusakan: " + jenisKerusakan);
        System.out.println("Lokasi: " + lokasi);
        System.out.println("Deskripsi: " + deskripsi);
        System.out.println("Status: " + status);
    }
}
