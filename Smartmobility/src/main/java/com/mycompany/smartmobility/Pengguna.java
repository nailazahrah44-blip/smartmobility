package com.mycompany.smartmobility;

public class Pengguna {

    private int idPengguna;
    private String nama;
    private String email;
    private String password;
    private String role;

    public Pengguna(int idPengguna, String nama, String email,
                    String password, String role) {

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email wajib diisi!");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Format email tidak valid!");
        }

        this.idPengguna = idPengguna;
        this.nama = nama;
        this.email = email.trim();
        this.password = password;
        this.role = role;
    }

    public int getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()
                || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Email wajib diisi dengan format valid!");
        }

        this.email = email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
