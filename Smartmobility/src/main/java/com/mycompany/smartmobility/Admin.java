
package com.mycompany.smartmobility;

public class Admin extends Pengguna {

    public Admin(int idPengguna, String nama, String email,
                 String password, String role) {
        super(idPengguna, nama, email, password, role);
    }

    public void kelolaLaporan() {
        System.out.println("Admin dapat mengelola laporan.");
    }

    public void verifikasiLaporan() {
        System.out.println("Admin melakukan verifikasi laporan.");
    }

    public void tampilkanProfil() {
        System.out.println("ID Admin: " + getIdPengguna());
        System.out.println("Nama: " + getNama());
        System.out.println("Email: " + getEmail());
        System.out.println("Role: " + getRole());
    }
}
