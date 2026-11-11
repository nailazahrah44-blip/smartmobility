
package com.mycompany.smartmobility;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import javafx.application.HostServices;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class App extends Application {

    private Stage stage;

    private final ObservableList<Laporan> daftarLaporan =
            FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        tampilkanLogin();
    }

    // LOGIN
    private void tampilkanLogin() {

        Label judul = new Label("SMART MOBILITY");
        Label subjudul = new Label(
                "Sistem Informasi Pelaporan Jalan Rusak"
        );

        TextField emailField = new TextField();
        emailField.setPromptText("Masukkan email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Masukkan password");

        Button loginButton = new Button("Login");
        Label pesan = new Label();

        loginButton.setOnAction(e -> {

            String email = emailField.getText().trim();
            String password = passwordField.getText();

            if (email.equals("admin@gmail.com")
                    && password.equals("admin123")) {

                Admin admin = new Admin(
                        1,
                        "Admin Smart Mobility",
                        email,
                        password,
                        "Admin"
                );

                tampilkanDashboardAdmin(admin);

            } else if (email.equals("pengguna@gmail.com")
                    && password.equals("user123")) {

                Pelapor pelapor = new Pelapor(
                        2,
                        "Pengguna Demo",
                        email,
                        password,
                        "Pelapor"
                );

                tampilkanDashboardPengguna(pelapor);

            } else {
                pesan.setText("Email atau password salah!");
            }
        });

        VBox layout = new VBox(
                12,
                judul,
                subjudul,
                emailField,
                passwordField,
                loginButton,
                pesan
        );

        layout.setPadding(new Insets(30));
        layout.setMaxWidth(350);

        VBox root = new VBox(layout);
        root.setStyle("-fx-alignment: center;");

        stage.setTitle("Login Smart Mobility");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    // DASHBOARD PENGGUNA
    private void tampilkanDashboardPengguna(Pelapor pelapor) {

        Label judul = new Label("Dashboard Pengguna");
        Label identitas = new Label(
                "Selamat datang, " + pelapor.getNama()
        );

        ComboBox<String> jenisField = new ComboBox<>();

        jenisField.getItems().addAll(
                "Jalan Berlubang",
                "Jalan Retak",
                "Aspal Mengelupas",
                "Jalan Amblas",
                "Genangan Air",
                "Lainnya"
        );

        jenisField.setPromptText("Pilih jenis kerusakan");
        jenisField.setMaxWidth(Double.MAX_VALUE);

        TextField lokasiField = new TextField();
        lokasiField.setPromptText("Masukkan lokasi jalan rusak");

        TextArea deskripsiField = new TextArea();
        deskripsiField.setPromptText("Deskripsikan kondisi jalan");
        deskripsiField.setPrefRowCount(3);

        File[] fotoTerpilih = new File[1];

        ImageView previewFoto = new ImageView();
        previewFoto.setFitWidth(200);
        previewFoto.setFitHeight(130);
        previewFoto.setPreserveRatio(true);

        Label namaFoto = new Label("Belum ada foto dipilih");

        Button pilihFotoButton = new Button("Pilih Foto Jalan");

        pilihFotoButton.setOnAction(e -> {

            FileChooser chooser = new FileChooser();

            chooser.setTitle("Pilih Foto Jalan Rusak");

            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "File Gambar",
                            "*.png", "*.jpg", "*.jpeg"
                    )
            );

            File file = chooser.showOpenDialog(stage);

            if (file != null) {
                fotoTerpilih[0] = file;

                previewFoto.setImage(
                        new Image(file.toURI().toString())
                );

                namaFoto.setText(file.getName());
            }
        });

        Button mapsButton = new Button(
                "Lihat Lokasi di Google Maps"
        );

       mapsButton.setOnAction(e -> {

        String lokasi = lokasiField.getText().trim();

        if (lokasi.isEmpty()) {
            tampilkanPesan("Isi lokasi terlebih dahulu!");
            return;
        }

        try {
            String query = URLEncoder.encode(
                    lokasi,
                    StandardCharsets.UTF_8
            );

            String url =
                    "https://www.google.com/maps/search/?api=1&query="
                    + query;

            getHostServices().showDocument(url);

        } catch (Exception ex) {
            tampilkanPesan("Gagal membuka Google Maps!");
        }
    });

        Button kirimButton = new Button("Kirim Laporan");
        Button logoutButton = new Button("Logout");

        ListView<Laporan> listLaporan =
                new ListView<>(daftarLaporan);

        listLaporan.setCellFactory(param ->
                new ListCell<Laporan>() {

                    @Override
                    protected void updateItem(
                            Laporan laporan, boolean empty) {

                        super.updateItem(laporan, empty);

                        if (empty || laporan == null) {
                            setText(null);
                        } else {
                            setText(
                                    laporan.getJenisKerusakan()
                                    + " - " + laporan.getLokasi()
                                    + "\nStatus: " + laporan.getStatus()
                            );
                        }
                    }
                }
        );

        kirimButton.setOnAction(e -> {

            String jenis = jenisField.getValue();
            String lokasi = lokasiField.getText().trim();
            String deskripsi = deskripsiField.getText().trim();

            if (jenis == null
                    || lokasi.isEmpty()
                    || deskripsi.isEmpty()) {

                tampilkanPesan(
                        "Semua data laporan wajib diisi!"
                );
                return;
            }

            if (fotoTerpilih[0] == null) {
                tampilkanPesan("Silakan pilih foto jalan rusak!");
                return;
            }

            Laporan laporan = new Laporan(
                    daftarLaporan.size() + 1,
                    jenis,
                    lokasi,
                    deskripsi
            );

            daftarLaporan.add(laporan);

            jenisField.setValue(null);
            lokasiField.clear();
            deskripsiField.clear();
            previewFoto.setImage(null);
            fotoTerpilih[0] = null;
            namaFoto.setText("Belum ada foto dipilih");

            tampilkanPesan("Laporan berhasil dikirim!");
        });

        logoutButton.setOnAction(e -> tampilkanLogin());

        VBox layout = new VBox(
                10,
                judul,
                identitas,
                new Label("Buat Laporan"),
                new Label("Jenis Kerusakan"),
                jenisField,
                new Label("Lokasi"),
                lokasiField,
                mapsButton,
                new Label("Deskripsi"),
                deskripsiField,
                pilihFotoButton,
                previewFoto,
                namaFoto,
                kirimButton,
                new Label("Daftar Laporan"),
                listLaporan,
                logoutButton
        );

        layout.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);

        stage.setTitle("Dashboard Pengguna");
        stage.setScene(new Scene(scrollPane, 650, 750));
    }

    // DASHBOARD ADMIN
    private void tampilkanDashboardAdmin(Admin admin) {

        Label judul = new Label("Dashboard Admin");

        Label identitas = new Label(
                "Selamat datang, " + admin.getNama()
        );

        ListView<Laporan> listLaporan =
                new ListView<>(daftarLaporan);

        listLaporan.setCellFactory(param ->
                new ListCell<Laporan>() {

                    @Override
                    protected void updateItem(
                            Laporan laporan, boolean empty) {

                        super.updateItem(laporan, empty);

                        if (empty || laporan == null) {
                            setText(null);
                        } else {
                            setText(
                                    "ID: " + laporan.getIdLaporan()
                                    + "\nJenis: " + laporan.getJenisKerusakan()
                                    + "\nLokasi: " + laporan.getLokasi()
                                    + "\nDeskripsi: " + laporan.getDeskripsi()
                                    + "\nStatus: " + laporan.getStatus()
                            );
                        }
                    }
                }
        );

        ImageView previewFotoAdmin = new ImageView();
        previewFotoAdmin.setFitWidth(250);
        previewFotoAdmin.setFitHeight(180);
        previewFotoAdmin.setPreserveRatio(true);

        Button verifikasiButton =
                new Button("Verifikasi Laporan");

        Button tolakButton =
                new Button("Tolak Laporan");

        Button logoutButton =
                new Button("Logout");

        listLaporan.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, lama, laporan) -> {

                    if (laporan != null) {
                        // Foto belum disimpan di class Laporan.
                        previewFotoAdmin.setImage(null);
                    }
                });

        verifikasiButton.setOnAction(e -> {

            Laporan laporan = listLaporan
                    .getSelectionModel()
                    .getSelectedItem();

            if (laporan == null) {
                tampilkanPesan("Pilih laporan terlebih dahulu!");
                return;
            }

            laporan.setStatus("Terverifikasi");
            listLaporan.refresh();

            tampilkanPesan("Laporan berhasil diverifikasi!");
        });

        tolakButton.setOnAction(e -> {

            Laporan laporan = listLaporan
                    .getSelectionModel()
                    .getSelectedItem();

            if (laporan == null) {
                tampilkanPesan("Pilih laporan terlebih dahulu!");
                return;
            }

            laporan.setStatus("Ditolak");
            listLaporan.refresh();

            tampilkanPesan("Laporan berhasil ditolak!");
        });

        logoutButton.setOnAction(e -> tampilkanLogin());

        VBox layout = new VBox(
                12,
                judul,
                identitas,
                new Label("Daftar Semua Laporan"),
                listLaporan,
                new Label("Foto Laporan"),
                previewFotoAdmin,
                verifikasiButton,
                tolakButton,
                logoutButton
        );

        layout.setPadding(new Insets(20));

        stage.setTitle("Dashboard Admin");
        stage.setScene(new Scene(layout, 650, 600));
    }

    // PESAN
    private void tampilkanPesan(String pesan) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Smart Mobility");
        alert.setHeaderText(null);
        alert.setContentText(pesan);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
