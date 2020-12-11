module pl.rav {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;
    requires static org.mapstruct.processor; // dopiero po dodaniu dependencies i requires org.mapstruct.processor aplikacja się skomilowała z LOMBOK


    opens pl.rav.control to javafx.fxml;
    opens pl.rav.util to javafx.fxml;
    exports pl.rav;

}