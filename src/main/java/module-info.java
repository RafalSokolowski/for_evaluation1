module pl.rav {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.base;

    requires lombok;
    requires static org.mapstruct.processor; // dopiero po dodaniu dependencies i requires org.mapstruct.processor aplikacja się skomilowała z LOMBOK

    opens pl.rav.control to javafx.fxml, javafx.controls, javafx.media;
    opens pl.rav.util to javafx.fxml, javafx.controls, javafx.media;

    exports pl.rav;

}