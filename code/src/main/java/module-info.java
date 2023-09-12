module com.example.mm_part2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.example.mm_part2 to javafx.fxml;
    exports com.example.mm_part2;
}