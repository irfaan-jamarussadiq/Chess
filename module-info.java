module Chess {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter;

    opens src to javafx.fxml;
    exports src;
}