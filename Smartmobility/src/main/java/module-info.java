module com.mycompany.smartmobility {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.smartmobility to javafx.fxml;
    exports com.mycompany.smartmobility;
}
