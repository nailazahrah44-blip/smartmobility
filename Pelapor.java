package com.mycompany.smartmobility;

public class Pelapor extends Pengguna {

    public Pelapor(int idPengguna, String nama, String email,
                   String password, String role) {

        super(idPengguna, nama, email, password, role);
    }

    public void buatLaporan() {
        System.out.println("Pelapor dapat membuat laporan jalan rusak.");
    }

    public void tampilkanProfil() {
        System.out.println("ID Pengguna: " + getIdPengguna());
        System.out.println("Nama: " + getNama());
        System.out.println("Email: " + getEmail());
        System.out.println("Role: " + getRole());
    }
}
