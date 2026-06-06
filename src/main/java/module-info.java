module org.example.quiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.quiz to javafx.fxml;
    exports org.example.quiz;
}